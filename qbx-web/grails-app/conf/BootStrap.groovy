import grails.converters.JSON
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import org.springframework.context.i18n.LocaleContextHolder as LCH
import se.qbtech.qbx.domain.model.authentication.*
import se.qbtech.qbx.domain.model.common.Address
import se.qbtech.qbx.domain.model.qtest.TestResult
import se.qbtech.qbx.domain.model.qtest.TestSubject
import se.qbtech.qbx.domain.model.qtest.Transaction
import se.qbtech.qbx.domain.model.qtest.Voucher
import se.qbtech.qbx.domain.model.questionnaire.AnswerOption
import se.qbtech.qbx.domain.model.questionnaire.Question
import se.qbtech.qbx.domain.model.site.ProfessionalUser
import se.qbtech.qbx.domain.model.site.Site

class BootStrap {

    def messageSource
    def customerService

    def init = { servletContext ->


        [ProfessionalUser, TestSubject, Voucher].each { clazz ->
            JSON.registerObjectMarshaller(clazz) {
                Map result = [:]
                def domain = new DefaultGrailsDomainClass(clazz)
                domain.persistentProperties.each { GrailsDomainClassProperty property ->
                    result[property.name] = it[property.name]
                }
                result.id = it.id
                if (it.site) {
                    result.site = [id: it.site.id, name: it.site.name]
                }
                return result
            }
        }

        JSON.registerObjectMarshaller(Site) {
            Map result = [:]
            def domain = new DefaultGrailsDomainClass(Site)
            domain.persistentProperties.each { GrailsDomainClassProperty property, String propertyName = property.name ->
                if (propertyName != 'users' && propertyName != 'vouchers' && propertyName != 'paymentMethod') {
                    result[propertyName] = it[propertyName]
                } else if (propertyName == 'paymentMethod') {
                    result.paymentMethod = [name : it.paymentMethod.name(),
                                            value: messageSource.getMessage('paymentMethod.' + it.paymentMethod.name(), null, LCH.locale)]
                }
            }
            result.id = it.id
            return result
        }

        JSON.registerObjectMarshaller(Transaction) {
            Map result = [:]
            def domain = new DefaultGrailsDomainClass(Transaction)
            domain.persistentProperties.each { GrailsDomainClassProperty property, String propertyName = property.name ->
                if (propertyName != 'voucher') {
                    result[propertyName] = it[propertyName]
                } else if (it.voucher) {
                    result.voucher = it.voucher.code
                }
            }
            result.id = it.id
            return result
        }

        if (!Role.count()) {
            Role.AvailableRoles.values().each {
                new Role(authority: it.value()).save()
            }
        }

        def roleSystemAdmin = Role.findByAuthority(Role.AvailableRoles.SYSTEM_ADMIN.value())

        Person systemAdmin = Person.findByEmail('systemadmin@gmail.com') ?: new Person(firstName: 'System',
                lastName: 'Admin', email: 'systemadmin@gmail.com',
                password: 'sAdmin1', enabled: true, accountLocked: false, accountExpired: false).save(failOnError: true, flush: true)

        if (!PersonRole.findByPersonAndRole(systemAdmin, roleSystemAdmin)) {
            PersonRole.create(systemAdmin, roleSystemAdmin, true)
        }
        createDefaultQuestionsAnswers()

        createIfNotExist(AccessCode, [publicId: "34JDED55", qbTestPatientId: "123573"])
        if (!Site.count()) {
            new Site(name: "Test Site", email: "testsite@testsite.com", paymentMethod: Site.PaymentMethod.INVOICE,
                    address: new Address(countryCode: "SE", zipCode: "254 DS", city: "Stockholm",
                            address1: "International park")).save(flush: true, failOnError: true)
        }

        createRequestMapIfNotExists('/testSubject/**', Role.AvailableRoles.SYSTEM_ADMIN.value())
        createRequestMapIfNotExists('/person/**', Role.AvailableRoles.SYSTEM_ADMIN.value())
        createRequestMapIfNotExists('/professionalUser/**', Role.AvailableRoles.SYSTEM_ADMIN.value())
        createRequestMapIfNotExists('/professionalUserActions/**', Role.AvailableRoles.PROFESSIONAL_USER.value())
        createRequestMapIfNotExists('/voucher/**', Role.AvailableRoles.SYSTEM_ADMIN.value())
        createRequestMapIfNotExists('/site/**', Role.AvailableRoles.SYSTEM_ADMIN.value())
        createRequestMapIfNotExists('/site/signUp', 'IS_AUTHENTICATED_ANONYMOUSLY')
        createRequestMapIfNotExists('/site/changeAddress', 'IS_AUTHENTICATED_ANONYMOUSLY')
        createRequestMapIfNotExists('/site/registration', 'IS_AUTHENTICATED_ANONYMOUSLY')
        createRequestMapIfNotExists('/transaction/**', Role.AvailableRoles.SYSTEM_ADMIN.value())

        environments {
            test {
                def siteName = "Test Site"
                def siteEmail = "testsite@testsite.com"

                new Voucher(code: 'F123878H', site: Site.findByNameAndEmail(siteName, siteEmail), status: Voucher.VoucherStatus.NOT_USED).save(flush: true, failOnError: true)
                new Voucher(code: '33333333', site: Site.findByNameAndEmail(siteName, siteEmail), status: Voucher.VoucherStatus.USED).save(flush: true, failOnError: true)

                def professionalUser = new ProfessionalUser(firstName: 'professionalUser', lastName: 'professionalUser',
                        email: 'professionalUser1@gmail.com', password: 'professionalUser1', enabled: true, accountLocked: false,
                        accountExpired: false, site: Site.findByEmail("testsite@testsite.com"))
                professionalUser.save(flush: true, failOnError: true)

                PersonRole.create professionalUser, Role.findByAuthority(Role.AvailableRoles.PROFESSIONAL_USER.value()), true

                def testSubject = new TestSubject(gender: "male", dateOfBirth: new Date() - 364 * 26, publicId: 'TestSubject1').save(failOnError: true)
                def testResult = new TestResult(qbxPath: "sss", age: 50, testSubject: testSubject, publicId: 'TestResult1')

                Transaction transaction = customerService.createTransaction(testResult, testSubject)
                transaction.publicId = "TransactionPublicId"
                testResult.transaction = transaction
                testResult.save(flush: true, failOnError: true)
            }
        }
    }

    Object createIfNotExist(Class clazz, Map propertyMap) {
        def obj = clazz.findWhere(propertyMap)
        if (!obj) {
            obj = clazz.newInstance(propertyMap)
            obj.save(flush: true, failOnError: true)
        }
        return obj
    }

    void createRequestMapIfNotExists(String url, String... configAttributes) {
        String summaryConfigAttribute = configAttributes.join(', ')
        createIfNotExist(Requestmap, [url: url, configAttribute: summaryConfigAttribute])
    }

    def destroy = {
    }

    def createDefaultQuestionsAnswers(){
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Has difficulty paying attention to details or makes careless mistakes unless interested'])//0 1
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Has difficulty remaining focused on tasks or play activities unless interested'])//0 2
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Does not seem to listen when spoken to directly'])//0 3
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Has difficulty to follow through on tasks'])//0 4
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Has difficulty organizing and managing tasks'])//0 5
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Avoids sustained mental effort unless interested'])//0 6
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Loses things'])//0 7
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Seems easily distracted by the environment'])//0 8
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Forgetful'])//0 9
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Fidgets or squirms'])//0 10
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Leaves seat when remaining seated is expected'])//0 11
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Runs about or climbs when inappropriate or appears restless'])//0 12
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Has difficulty to engage in play or leisure activities quietly'])//0 13
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Seems uncomfortable being still for extended time periods'])//0 14
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Talks excessively'])//0 15
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Blurts out answers or thoughts'])//0 16
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Has difficulty waiting turn'])//0 17
        createIfNotExist(Question, [ageGroup: 0, questionDescription: 'Interrupts or intrudes on others'])//0 18


        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Difficulty paying attention to details or makes careless mistakes unless interested'])//1 1
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Difficulty staying focused unless  interested'])//1 2
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Difficulty listening when spoken to directly'])//1 3
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Difficulty following through on tasks'])//1 4
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Difficulty organizing and managing tasks'])//1 5
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Avoiding sustained mental effort unless interested'])//1 6
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Losing things'])//1 7
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Easily distracted by the environment or by unrelated thoughts'])//1 8
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Forgetful'])//1 9
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Fidgeting, or squirming in my chair'])//1 10
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Finding reasons to leave my seat when expected to remain seated'])//1 11
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Moving about excessively or feeling restless'])//1 12
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Difficulty engaging in leisure activities quietly'])//1 13
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Feeling uncomfortable being still for extended time periods'])//1 14
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Talking excessively'])//1 15
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Blurting out answers or thoughts'])//1 16
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Difficulty awaiting my turn'])//1 17
        createIfNotExist(Question, [ageGroup: 1, questionDescription: 'Interrupting or imposing on others'])//1 18


        createIfNotExist(AnswerOption, [scaleValue: 0, answer: 'Never or Rarely'])
        createIfNotExist(AnswerOption, [scaleValue: 1, answer: 'Sometimes'])
        createIfNotExist(AnswerOption, [scaleValue: 2, answer: 'Often'])
        createIfNotExist(AnswerOption, [scaleValue: 3, answer: 'Very Often'])

    }
}
