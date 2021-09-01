package se.qbtech.qbx.domain.model.qtest

import se.qbtech.qbx.domain.model.common.BasePersistentObject
import se.qbtech.qbx.domain.model.site.ProfessionalUser

/**
 * @author Michael Astreiko
 */
class TestResult extends BasePersistentObject {

    Boolean deleted = false
    //The location of json file
    String qbxPath
    //1 for the child test, 6 for the adult
    Integer productNumber = 6

    Integer age
    String countryCode
    Integer numberOfButtons
    Integer numberOfOutliers

    static hasMany = [testParams: QbTestParam, qualityPrams: QbTestParam]
    static mappedBy = [testParams: "testResult", qualityPrams: "qualityTestResult"]

    static belongsTo = [testSubject: TestSubject, submittingUser: ProfessionalUser]
    static hasOne = [transaction: Transaction]

    static mapping = {
        testParams sort: 'key'
        qualityPrams sort: 'key'
    }

    static constraints = {
        submittingUser(nullable: true)
        countryCode(nullable: true)
        numberOfButtons(nullable: true)
        numberOfOutliers(nullable: true)
    }

    def beforeInsert() {
        super.beforeInsert()
        if (age < 12) {
            productNumber = 1
        }
    }
}
