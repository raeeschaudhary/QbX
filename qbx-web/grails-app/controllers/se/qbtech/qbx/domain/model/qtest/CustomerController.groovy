package se.qbtech.qbx.domain.model.qtest

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONObject
import se.qbtech.qbx.domain.model.questionnaire.AnswerOption
import se.qbtech.qbx.domain.model.questionnaire.Question
import se.qbtech.qbx.domain.model.questionnaire.QuestionAnswer
import se.qbtech.qbx.domain.model.site.ProfessionalUser
import se.qbtech.qbx.domain.model.site.Site
import se.qbtech.qbx.domain.payment.BillingCommand

/**
 * @author Vladimir Havenchyk
 */
class CustomerController {
    def grailsApplication
    def springSecurityService
    def braintreePaymentService
    def publicIdGenerationService
    def customerService

    def index() {
        redirect(action: 'payment')
    }

    def beforeTest() {
        def userAgent = request.getHeader("user-agent")
        def isFirefox = userAgent.contains("Firefox")
        def isChrome = userAgent.contains("Chrome")
        //Alternative or pluggin can be used.
        render view: '/customer/beforeTest', model : [chrome: isChrome, firefox: isFirefox,
                transactionId: params.transactionId, testResultId: params.testResultId, testSubjectId: params.testSubjectId]
    }

    def registration() {
        def userAgent = request.getHeader("user-agent")
        def isFirefox = userAgent.contains("Firefox")
        def isChrome = userAgent.contains("Chrome")


        def testSubjectId = params.long('testSubjectId')
        def transactionId = params.long('transactionId')
        def voucherCode = params.voucher
        def voucher
        if (transactionId && testSubjectId){
            def testResult = TestResult.findById(params.testResultId)
            def testSubject = TestSubject.findById(testSubjectId)
            if (voucherCode) {
                voucher = Voucher.findByCode(voucherCode)
                if (voucher) {
                    if (voucher.status == Voucher.VoucherStatus.USED) {
                        flash.error = g.message(code: 'error.voucher.expired')
                    }
                } else {
                    flash.error = g.message(code: 'error.voucher.notExist')
                }
                if (flash.error) {
                    render view: "/customer/beforeTest", model: [chrome: isChrome, firefox: isFirefox,
                            transactionId: params.transactionId, testResultId: params.testResultId, testSubjectId: params.testSubjectId]
                    return
                }
                else {
                    testSubject.site = voucher.site
                    testSubject.addToVouchers(voucher)
                    //log.info("6 " + voucher + ", " + transactionId + ", " + testSubject.id + ", " + testResult.id)
                    customerService.updateTransaction(transactionId, voucher.code)
                    //log.info("updated transaction")
                    render(view: 'report/report', model: [testResult: testResult])
                    return
                }
            }
        }
        else{
            if (params.voucher) {
                voucher = Voucher.findByCode(params.voucher)
                if (voucher) {
                    if (voucher.status == Voucher.VoucherStatus.USED) {
                        flash.error = g.message(code: 'error.voucher.expired')
                    }
                } else {
                    flash.error = g.message(code: 'error.voucher.notExist')
                }
                if (flash.error) {
                    render view: "/customer/beforeTest"
                    return
                }
            }
            render view: '/customer/registration', model: [voucher: params.voucher, isNormal: isNormal()]
        }
    }

    def welcomeQbX(){
        redirectToLandingPageIfFromMobileDevice();
        def child = params.child
        def permission = params.permission
        def voucher = params.voucher
        def gender = params.gender
        def testSubjectId = params.long('testSubjectId')
        def dob = params.dateOfBirth

        def age
        if (params.dateOfBirth){
            def parseDOB = Date.parse(g.message(code: 'default.date.format'), params.dateOfBirth)
            age = customerService.calculateUserAge(parseDOB)
        }
        else {
            def testSubject= TestSubject.findAllById(testSubjectId)
            age = customerService.calculateUserAge(testSubject.dateOfBirth)
        }

        if (age >= 6 && age < 61){
            if (age < 18 && !permission){
                child = true
                if (testSubjectId){
                    //goto page welcome
                    render view: '/customer/welcomeQbX', model: [testSubjectId: testSubjectId, child : child]
                    return
                }
                else{
                    if (dob && gender && voucher){
                        def dateOfBirth = Date.parse(g.message(code: 'default.date.format'), dob)
                        testSubjectId = createTestSubjectWithVoucher(dateOfBirth, gender, voucher)
                        createQualityParameter(testSubjectId)
                        render view: "welcomeQbX", model: [testSubjectId: testSubjectId, child: child]
                        return
                    }
                    else if(dob && gender && !voucher){
                        def dateOfBirth = Date.parse(g.message(code: 'default.date.format'), dob)
                        testSubjectId = createTestSubjectWithVoucher(dateOfBirth, gender, voucher)
                        createQualityParameter(testSubjectId)
                        render view: "welcomeQbX", model: [testSubjectId: testSubjectId, child: child]
                        return
                    }
                }
            }

            else if (age < 18 && permission){
                child = false
                //goto page welcome
                render view: '/customer/welcomeQbX', model: [testSubjectId: testSubjectId, child : child]
                return
            }
            else{
                if (testSubjectId){
                    //goto page welcome
                    render view: '/customer/welcomeQbX', model: [testSubjectId: testSubjectId]
                    return
                }else{
                    if (dob && gender && voucher){
                        def dateOfBirth = Date.parse(g.message(code: 'default.date.format'), dob)
                        testSubjectId = createTestSubjectWithVoucher(dateOfBirth, gender, voucher)
                        createQualityParameter(testSubjectId)
                        render view: "welcomeQbX", model: [testSubjectId: testSubjectId]
                        return
                    }
                    else if(dob && gender && !voucher){
                        def dateOfBirth = Date.parse(g.message(code: 'default.date.format'), dob)
                        testSubjectId = createTestSubjectWithVoucher(dateOfBirth, gender, voucher)
                        createQualityParameter(testSubjectId)
                        render view: "welcomeQbX", model: [testSubjectId: testSubjectId]
                        return
                    }
                }
            }
        }
        else{
            if (age < 6)
            {
                flash.error = g.message(code: 'invalid.age.error')
                render view:'/customer/registration', model: [voucher: voucher, isNormal: isNormal()]
                return
            }
            else{
                flash.error = g.message(code:'invalid.age.error')
                render view: '/customer/registration', model: [voucher: voucher, isNormal: isNormal()]
                return
            }
        }
    }

    def createTestSubjectWithVoucher(def dateOfBirth, def gender, def voucher){

        def testSubject = new TestSubject()
        testSubject.dateOfBirth = dateOfBirth
        testSubject.gender = params.gender

        def currentUser = springSecurityService.currentUser
        if (currentUser instanceof ProfessionalUser) {
            testSubject.site = currentUser.site
        }

        if (voucher) {
            def findVoucher = Voucher.findByCode(voucher)

            if (findVoucher) {
                if (findVoucher.status == Voucher.VoucherStatus.NOT_USED) {
                    testSubject.site = findVoucher.site
                    testSubject.addToVouchers(findVoucher)
                } else {
                    flash.error = g.message(code: 'error.voucher.expired')
                }
            } else {
                flash.error = g.message(code: 'error.voucher.notExist')
            }
            if (flash.error) {
                render view: "/customer/registration", model: [voucher: params.voucher]

                return
            }
        }
        if (testSubject.save(flush: true) && !testSubject.hasErrors()) {
            return testSubject.id
        }
    }

    def createQualityParameter(Long testSubjectId){
        def testSubject = TestSubject.findById(testSubjectId)

        def qualityParameter = new QualityParameter()
        qualityParameter.testSubject = testSubject
        qualityParameter.numberOfComputerChecks = 0
        qualityParameter.numberOfPractiseTests = 0
        qualityParameter.testStatus = QualityParameter.TestStatus.REGISTERED

        qualityParameter.save()
    }

    def questionnaire(){

        def testSubjectId = params.long('testSubjectId')
        def testSubject = TestSubject.findById(testSubjectId)

        def age = customerService.calculateUserAge(testSubject.dateOfBirth)
        def questions
        def ageGroup
        if (age < 18){
            ageGroup = 0
            questions = Question.findAllByAgeGroup(ageGroup)
        }
        else{
            ageGroup = 1
            questions = Question.findAllByAgeGroup(ageGroup)
        }

        def options = AnswerOption.getAll()
        render view: "questionnaire", model: [testSubjectId: testSubjectId, questions: questions, options: options, ageGroup: ageGroup]
    }

    def createTest() {

        def ageGroup = params["ageGroup"]
        def questions = Question.findAllByAgeGroup(ageGroup)
        def options = AnswerOption.getAll()

        //have to create test_subject after dob
        def testSubjectId = params.long('testSubjectId')
        def testSubject = TestSubject.get(testSubjectId)

        def alreadyAnswered = QuestionAnswer.findByTestSubject(testSubject)

        def answers = [:]
        for (question in questions){
            answers[question.id] = params[question.id.toString()]

            if (!answers[question.id]){
                flash.message = "Please answer all questions. They contain certain value in test"
                render view: "questionnaire", model: [testSubjectId: testSubjectId, questions: questions, options: options, ageGroup: ageGroup]
                return
            }
        }
        if (!alreadyAnswered){
            for (question in questions){
                answers[question.id] = params[question.id.toString()]
                def newAnswer = new QuestionAnswer()
                newAnswer.question = question
                newAnswer.answer = AnswerOption.findByScaleValue(answers[question.id])
                newAnswer.testSubject = testSubject
                if (newAnswer.save(flush: true)){

                }
                else{
                    log.info('error in saving answer')
                }
            }
        }

        def fpsThreshold = grailsApplication.config.qbx.fps.threshold
        def age = customerService.calculateUserAge(testSubject.dateOfBirth)
        render view: '/customer/test', model: [testSubjectId: testSubjectId, fpsThreshold: fpsThreshold, age: age]
    }

    private void redirectToLandingPageIfFromMobileDevice() {
        if (!isNormal()) {
            redirect controller: 'info', action: 'landing'
            return
        }
    }

    def introduction(Long id) {
        def testSubject = TestSubject.findById(id)
        def qualityParameter = QualityParameter.findByTestSubject(testSubject)
        qualityParameter.testStatus = QualityParameter.TestStatus.STARTED
        qualityParameter.save(flush: true)
        def child = customerService.isChild(id)
        render template: '/customer/steps/introduction', model: [child: child]
    }

    def computerCheck() {
        render template: '/customer/steps/computerCheck'
    }

    def updateComputerChecksCounter(){
        def testSubject = TestSubject.findById(params.subject)
        def qualityParameter = QualityParameter.findByTestSubject(testSubject)
        qualityParameter.numberOfComputerChecks = qualityParameter.numberOfComputerChecks + 1
        qualityParameter.save(flush: true)
    }

    def performPracticeTest(Long id) {
        def linkToVideo = customerService.getVideoLink(id)
        def child = customerService.isChild(id)
        render template: '/customer/steps/performPracticeTest', model: [linkToVideo: linkToVideo, child: child]
    }

    def updatePracticeTestsCounter(){
        def testSubject = TestSubject.findById(params.subject)
        def qualityParameter = QualityParameter.findByTestSubject(testSubject)
        qualityParameter.numberOfPractiseTests = qualityParameter.numberOfPractiseTests + 1
        qualityParameter.save(flush: true)
    }

    def startTest() {
        render template: '/customer/steps/startTest'
    }

    def testScreen() {
        render template: '/customer/testScreen'
    }

    def failPracticeTest() {
        render template: '/customer/steps/failPracticeTest'
    }

    def generateReport() {

        //todo: make this page not static later
        render view: '/customer/report/report'
    }

    def payment() {
        [billingCmd: new BillingCommand(), transactionId: params.transactionId]
    }

    def thankYou(){

        def data = JSON.parse(params.testResults)

        def testSubject = TestSubject.get(data.testSubjectId)
        def testResult = new TestResult(testSubject: testSubject)
        testResult.publicId = publicIdGenerationService.generatePublicId(TestSubject)

        Transaction transaction = customerService.createTransaction(testResult, testSubject)

        Map jsonToSend = customerService.buildJSONDataForExternalAPICall(data, testSubject)

        jsonToSend.each { elem ->
            //Store in db all params except huge massives of data
            if (!['camera_data', 'stimuli_data', 'button_data'].contains(elem.key)) {
                testResult.addToTestParams(key: elem.key, value: elem.value)
            }
        }

        testResult.qbxPath = customerService.storeJSON((jsonToSend as JSON).toString(), testResult.publicId)
        testResult.age = customerService.calculateUserAge(testSubject.dateOfBirth)

        JSONObject jsonResult = customerService.sendJSONDataToReportServer(jsonToSend)

        if (jsonResult.status == "OK") {
            jsonResult.each { elem ->
                if (elem.key != "status") {
                    testResult.addToQualityPrams(key: elem.key, value: "${elem.value}".toString())
                }
            }
            transaction.status = Transaction.TransactionStatus.UNDER_WAY
        } else {
            //handle error
            transaction.status = Transaction.TransactionStatus.ABORTED
            log.error "Error during publishing data to PG server: ${jsonResult.error}"
        }

        def voucher = Voucher.findByTestSubject(testSubject)
        if (voucher || transaction.type == Transaction.TransactionType.INVOICE){
            transaction.status = Transaction.TransactionStatus.COMPLETED
        }
        else{
            transaction.status = Transaction.TransactionStatus.NOT_COMPLETED
        }

        testResult.save(flush: true, failOnError: true)

        if (springSecurityService.currentUser instanceof ProfessionalUser) {
            def paymentMethod = testSubject.site?.paymentMethod
            if (paymentMethod == Site.PaymentMethod.CREDIT_CARD) {
                try {
                    def braintreeTransactionId = braintreePaymentService.executeSaleTransactionForSite(testSubject.site,
                            grailsApplication.config.qbx.qbTest.costInUsd as BigDecimal)
                    transaction.braintreeTransactionId = braintreeTransactionId
                    transaction.status = Transaction.TransactionStatus.COMPLETED
                    transaction.save()
                } catch (Exception e) {
                    if (log.isErrorEnabled()) {
                        log.error e.message
                    }
                }
            }
        }

        def qualityParameter = QualityParameter.findByTestSubject(testSubject)
        qualityParameter.testStatus = QualityParameter.TestStatus.COMPLETED
        qualityParameter.save(flush: true)

        render view: "thankYou", model: [transactionId: transaction.id, testResultId: testResult.id, testSubjectId: testSubject.id]
    }

    def finishTest() {
        def transactionId = params.long('transactionId')
        def transaction = Transaction.findById(transactionId)

        def testResultId = params.long('testResultId')
        def testResult = TestResult.findById(testResultId)

        def testSubjectId = params.long('testSubjectId')
        if (transaction.voucher || transaction.braintreeTransactionId || transaction.type == Transaction.TransactionType.INVOICE) {
            render(view: 'report/report', model: [testResult: testResult])
        } else {
            render(view: 'payment', model: [billingCmd: new BillingCommand(), transactionId: transactionId, testSubjectId: testSubjectId, testResultId: testResultId])
        }
    }

    def processPayment(BillingCommand billingCmd) {
        def transactionId = params.transactionId
        if (billingCmd.hasErrors()) {
            render(view: "payment", model: [billingCmd: billingCmd, transactionId: transactionId])
            return
        }
        def transaction = null
        try {
            def braintreeTransactionId = braintreePaymentService.executeSaleTransaction(billingCmd,
                    grailsApplication.config.qbx.qbTest.costInUsd as BigDecimal)
            if (transactionId) {
                transaction = Transaction.get(transactionId)
                transaction.braintreeTransactionId = braintreeTransactionId
                transaction.status = Transaction.TransactionStatus.COMPLETED
                transaction.save()
            }
        } catch (ex) {
            flash.error = ex.message
            render(view: "payment", model: [billingCmd: billingCmd, transactionId: transactionId])
            return
        }
        render(view: 'report/report', model: [testResult: transaction?.testResult])
    }

    def report() {

    }
}
