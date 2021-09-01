package se.qbtech.qbx.domain.model.qtest

import se.qbtech.qbx.domain.model.common.BasePersistentObject
import se.qbtech.qbx.domain.model.site.Site

/**
 * @author Michael Astreiko
 */
class Voucher extends BasePersistentObject {
    String code
    VoucherStatus status
    Date dateUsed

    //Test subject who used it
    static belongsTo = [site: Site, testSubject: TestSubject, transaction: Transaction]

    static constraints = {
        status(inList: VoucherStatus.values().toList())
        testSubject(nullable: true)
        dateUsed(nullable: true)
        transaction(nullable: true)
    }

    enum VoucherStatus {
        NOT_USED, USED
    }
}
