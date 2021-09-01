package se.qbtech.qbx.domain.model.site

import se.qbtech.qbx.domain.model.common.Address
import se.qbtech.qbx.domain.model.common.BasePersistentObject
import se.qbtech.qbx.domain.model.qtest.TestSubject
import se.qbtech.qbx.domain.model.qtest.Voucher

/**
 * @author Michael Astreiko
 */
class Site extends BasePersistentObject {
    def braintreePaymentService
    static transients = ['braintreePaymentService']

    static embedded = ['address', 'billingAddress']

    String name
    String notes
    String email
    String phone
    PaymentMethod paymentMethod

    Address address
    Address billingAddress

    Boolean isEnabled = false

    String braintreeCustomerId

    static hasMany = [users: ProfessionalUser, testSubjects: TestSubject, vouchers: Voucher]

    static constraints = {
        email(email: true, nullable: false, blank: false)
        phone(nullable: true, validator: phoneValidator)
        notes(maxSize: 5000, nullable: true)
        address(nullable: true)
        billingAddress(nullable: true)
        vouchers(nullable: true)
        braintreeCustomerId(nullable: true)
    }

    def beforeInsert() {
        super.beforeInsert()
        if (paymentMethod == PaymentMethod.CREDIT_CARD) {
            braintreeCustomerId = braintreePaymentService.registerCustomer(this)
        }
    }

    def beforeUpdate() {
        if (paymentMethod == PaymentMethod.CREDIT_CARD && !braintreeCustomerId) {
            braintreeCustomerId = braintreePaymentService.registerCustomer(this)
        }
    }

    enum PaymentMethod {
        VOUCHER, CREDIT_CARD, INVOICE
    }
}
