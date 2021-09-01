package se.qbtech.qbx.domain.model.qtest

import se.qbtech.qbx.domain.model.common.BasePersistentObject

/**
 * @author Michael Astreiko
 */
class Transaction extends BasePersistentObject {
    //The transaction status. -1 is aborted, 0 is under way, 1 completed.
    //Set to 1 when credit card transaction is verified or voucher.Status is set to 2 (used)
    TransactionStatus status = TransactionStatus.UNDER_WAY
    //1 for credit card payment, 2 for voucher, 3 is for logged in User (will be billed by Qbtech)
    TransactionType type

    //Whether voucher ...
    Voucher voucher
    //.. or braintreeTransactionId should be defined
    String braintreeTransactionId

    static belongsTo = [testResult: TestResult]

    static constraints = {
        voucher(nullable: true)
        braintreeTransactionId(nullable: true)
    }

    enum TransactionStatus {
        ABORTED, UNDER_WAY, NOT_COMPLETED, COMPLETED
    }
    enum TransactionType {
        CREDIT_CARD, VOUCHER, OTHER, INVOICE
    }
}
