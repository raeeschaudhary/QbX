package se.qbtech.qbx.service.qtest

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import org.codehaus.groovy.grails.web.json.JSONObject
import se.qbtech.qbx.domain.model.qtest.TestResult
import se.qbtech.qbx.domain.model.qtest.TestSubject
import se.qbtech.qbx.domain.model.qtest.Transaction
import se.qbtech.qbx.domain.model.qtest.Voucher
import se.qbtech.qbx.domain.model.site.ProfessionalUser
import se.qbtech.qbx.domain.model.site.Site

/**
 * @author Michael Astreiko
 */
class CustomerService {
    static transactional = false

    def grailsApplication
    def springSecurityService

    /**
     * Create Transaction for testResult and testSubject
     *
     * @param testResult
     * @param testSubject
     * @return new Transaction
     */
    def updateTransaction(Long transactionId, String voucherCode){
        def transaction = Transaction.findById(transactionId)
        def voucher = Voucher.findByCode(voucherCode)
        transaction.type = Transaction.TransactionType.VOUCHER
        transaction.voucher = voucher
        transaction.voucher.status = Voucher.VoucherStatus.USED
        transaction.voucher.dateUsed = new Date()
        transaction.status = Transaction.TransactionStatus.COMPLETED
        transaction.save()
    }

    Transaction createTransaction(TestResult testResult, TestSubject testSubject) {
        Transaction transaction = new Transaction(testResult: testResult)
        testResult.transaction = transaction

        def voucher = Voucher.findByTestSubjectAndStatus(testSubject, Voucher.VoucherStatus.NOT_USED)
        if (voucher) {
            transaction.type = Transaction.TransactionType.VOUCHER
            transaction.voucher = voucher
            transaction.voucher.status = Voucher.VoucherStatus.USED
            transaction.voucher.dateUsed = new Date()
        } else {
            def sitePaymentMethod = testSubject.site?.paymentMethod
            if (sitePaymentMethod && sitePaymentMethod == Site.PaymentMethod.INVOICE) {
                transaction.type = Transaction.TransactionType.INVOICE
            } else {
                transaction.type = Transaction.TransactionType.CREDIT_CARD
            }
        }
        transaction.beforeInsert()
        transaction
    }

    /*
     * {
     *    ‘test_version’: ‘2.0’,
     *    ‘data_version’: ‘2.0’,
     *    ‘test_date’: ‘2014-01-15 15:55’,
     *    ‘test_duration’: 901,
     *    ‘gender’: ‘F’,
     *    ‘dob’: ‘2003-04-15’,
     *    ‘fov’: 61,
     *    ‘fps’: 28,
     *    ‘scale_factor’: 54.28,
     *    ‘camera_data’: {
     *      ‘x’: [23, 15, 20, 180],
     *      ‘y’: [44, 35, 15, 45],
     *      ‘t’: [4000, 4080, 5060, 5110],
     *      ‘q’:[1,1,0,0]
     *    },
     *    ‘stimuli_data’: {
     *      ‘tg’: [0, 0, 0, 128],
     *      ‘t’: [4000, 6000, 8020, 10004]
     *    },
     *    ‘button_data’: [4010, 10100]
     *    }
     *
     * @param dataFromUI
     * @param testSubject
     * @return Map that can be transferred to JSON object
     */

    Map buildJSONDataForExternalAPICall(dataFromUI, TestSubject testSubject) {
        def jsonToSend = [:]
        //Field ‘test_version’ should always changed each time the qbX application is changed.
        //The following format should be used: Release_Number.Upgrade_Number  The value should always increase
        //2.0 is initial value
        jsonToSend["test_version"] = "2.0"
        //Field ‘data_version’ should be changed each time the data object is changed. The following format should
        //be used: Release_Number.Upgrade_Number   The value should always increase.
        //2.0 is initial version
        jsonToSend["data_version"] = "2.0"
        //In seconds
        jsonToSend["test_duration"] = dataFromUI.testDuration
        jsonToSend["fov"] = dataFromUI.fov
        jsonToSend["fps"] = dataFromUI.fps
        jsonToSend["scale_factor"] = dataFromUI.scaleFactor
        jsonToSend["camera_data"] = dataFromUI.camera_data
        jsonToSend["stimuli_data"] = dataFromUI.stimuli_data
        jsonToSend["button_data"] = dataFromUI.button_data
        jsonToSend["gender"] = testSubject.gender == 'male' ? 'M' : 'F'
        jsonToSend["dob"] = testSubject.dateOfBirth.format("yyyy-MM-dd")
        jsonToSend["age"] = calculateUserAge(testSubject.dateOfBirth)
        jsonToSend["test_date"] = new Date().format("yyyy-MM-dd HH:mm")
        jsonToSend
    }

    /**
     * Store resulted json String in file for statistics reason
     *
     * @param jsonToSend
     * @param publicId
     */
    synchronized String storeJSON(String jsonToSend, String publicId) {
        def now = new Date()
        def filePath = ""
        try {
            def filePathPrefix = grailsApplication.config.qbx.qbTest.pathToSaveTestJSON
            filePath = "${filePathPrefix}/${now.format("yyyy")}/${now.format("MM")}"
            if (!new File(filePath).exists()) {
                new File(filePath).mkdirs()
            }
            filePath = filePath + "/${publicId}.json"
            new File(filePath).append(jsonToSend)
        } catch (ex) {
            log.error "Cannot store JSON file for test ${publicId}: ${ex.message}", ex
        }
        return filePath
    }

    /**
     * Calculate age based on date of birth
     *
     * @param birthday
     * @return
     */
    int calculateUserAge(Date birthday) {
        def now = new Date()
        def birthdayThisYear = new Date()
        birthdayThisYear.month = birthday.month
        birthdayThisYear.date = birthday.date

        return now.year - birthday.year - (birthdayThisYear > now ? 1 : 0)
    }

    JSONObject sendJSONDataToReportServer(Map jsonToSend) {
        def jsonResult = null
        def serverUrl = grailsApplication.config.qbx.qbTest.pg.server.url
        def http = new HTTPBuilder(serverUrl)
        http.request(Method.POST, ContentType.JSON) {
            uri.path = '/main/qbcheck'
            body = jsonToSend

            response.success = { resp, json ->
                jsonResult = json
            }
        }
        jsonResult
    }

    List getTransactions(Map params) {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        params.sort = 'id'
        params.order = 'desc'

        def currentUser = springSecurityService.currentUser
        def isCurrentUserProfessionalUser = currentUser instanceof ProfessionalUser

        def totalTransactionsCount = Transaction.createCriteria().count {
            if (isCurrentUserProfessionalUser) {
                testResult {
                    submittingUser {
                        eq('site', currentUser.site)
                    }
                }
            }
        }

        def transactions = Transaction.createCriteria().list(params) {
            if (isCurrentUserProfessionalUser) {
                testResult {
                    submittingUser {
                        eq('site', currentUser.site)
                    }

                }
            }
        }

        [transactions, totalTransactionsCount]
    }

    List searchTestSubjects(params) {
        String query = params.query ? "%${params.query}%" : ""

        def currentUser = springSecurityService.currentUser
        def isCurrentUserProfessionalUser = currentUser instanceof ProfessionalUser

        def testSubjects = TestSubject.withCriteria {
            if (isCurrentUserProfessionalUser) {
                eq('site', currentUser.site)
            }
            or {
                like('gender', query)
                like('qbTestPatientId', query)
                like('sitePatientId', query)
                like('publicId', query)
            }
        }

        testSubjects
    }

    List getTestSubjects(Map params) {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        params.sort = 'id'
        params.order = 'desc'

        def currentUser = springSecurityService.currentUser
        def isCurrentUserProfessionalUser = currentUser instanceof ProfessionalUser

        def totalTestSubjectsCount = TestSubject.createCriteria().count {
            if (isCurrentUserProfessionalUser) {
                eq('site', currentUser.site)
            }
        }

        def testSubjects = TestSubject.createCriteria().list(params) {
            if (isCurrentUserProfessionalUser) {
                eq('site', currentUser.site)
            }
        }
        [testSubjects, totalTestSubjectsCount]
    }

    List searchTransactions(params) {
        String query = params.query

        def currentUser = springSecurityService.currentUser
        def isCurrentUserProfessionalUser = currentUser instanceof ProfessionalUser

        def searchById
        try {
            searchById = Long.parseLong(query)
        } catch (NumberFormatException e) {
        }

        def transactions = Transaction.withCriteria {
            if (isCurrentUserProfessionalUser) {
                testResult {
                    submittingUser {
                        eq('site', currentUser.site)
                    }
                }
            }
            or {
                if (searchById) {
                    testResult {
                        like 'id', searchById
                    }
                }

                sqlRestriction("status like ?", ["%${query}%".toString()])
                sqlRestriction("type like ?", ["%${query}%".toString()])
            }
        }

        transactions
    }

    boolean isChild(Long id){
        def testSubject = TestSubject.get(id)

        def age = calculateUserAge(testSubject.dateOfBirth)

        return age < 12 ? true : false
    }

    String getVideoLink(Long id) {
        def testSubject = TestSubject.get(id)

        def age = calculateUserAge(testSubject.dateOfBirth)

        def linkForChild = grailsApplication.config.qbx.videoInstrationsLink.child
        def linkForAdult = grailsApplication.config.qbx.videoInstrationsLink.adult

        //less than 12 is a child, other - adults
        return age < 12 ? linkForChild : linkForAdult
    }
}
