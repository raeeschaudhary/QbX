package se.qbtech.qbx.domain.payment

import grails.validation.Validateable

/**
 * @author Michael Astreiko
 */
@Validateable
class BillingCommand {
    // Billing
    String cardHolderName
    String cardNumber
    String cardSecurityCode
    String cardExpireMonth
    String cardExpireYear

    static constraints = {
        cardHolderName nullable: true, maxSize: 35, validator: billingValidator
        cardNumber nullable: true, validator: billingValidator
        cardSecurityCode nullable: true, validator: billingValidator
        cardExpireMonth nullable: true, validator: billingValidator
        cardExpireYear nullable: true, validator: billingValidator
    }

    static billingValidator = { val, obj ->
        if (!val) {
            return "nullable.error"
        }
    }
}
