package se.qbtech.qbx.domain.model.qtest

import se.qbtech.qbx.domain.model.common.BasePersistentObject

class QualityParameter extends BasePersistentObject {

    static belongsTo = [testSubject : TestSubject]
    int numberOfComputerChecks
    int numberOfPractiseTests
    TestStatus testStatus
    static constraints = {
        numberOfComputerChecks(null: false)
        numberOfPractiseTests(null: false)
        testStatus(nullable: false)
    }

    enum TestStatus{
        REGISTERED, STARTED, COMPLETED
    }
}
