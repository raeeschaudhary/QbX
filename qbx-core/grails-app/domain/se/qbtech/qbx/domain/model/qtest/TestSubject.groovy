package se.qbtech.qbx.domain.model.qtest

import se.qbtech.qbx.domain.model.common.BasePersistentObject
import se.qbtech.qbx.domain.model.questionnaire.QuestionAnswer
import se.qbtech.qbx.domain.model.site.Site

/**
 * @author Michael Astreiko
 */
class TestSubject extends BasePersistentObject {
    String gender
    Date dateOfBirth
    //Specific name/id given in site
    String sitePatientId
    //Reference number in QbTest
    String qbTestPatientId

    static belongsTo = [site: Site]
    static hasMany = [vouchers: Voucher, testResults: TestResult,
            qualityParameter: QualityParameter, questionAnswers: QuestionAnswer]

    static constraints = {
        site(nullable: true)
        sitePatientId(nullable: true)
        qbTestPatientId(nullable: true)
        vouchers(nullable: true)
    }
}
