package se.qbtech.qbx.domain.model.qtest

/**
 * @author Michael Astreiko
 */
class QbTestParam {
    String key
    String value

    static belongsTo = [testResult: TestResult, qualityTestResult: TestResult]

    static mapping = {
        version false
        key column: 'qb_key'
        value column: 'qb_value'
    }

    static constraints = {
        testResult(nullable: true)
        qualityTestResult(nullable: true)
    }

    String toString() {
        key + ": " + value
    }
}
