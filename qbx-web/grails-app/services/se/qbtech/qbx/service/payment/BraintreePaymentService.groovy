package se.qbtech.qbx.service.payment

import com.braintreegateway.Customer
import com.braintreegateway.CustomerRequest
import com.braintreegateway.Result
import com.braintreegateway.Transaction
import com.braintreegateway.TransactionRequest
import se.qbtech.qbx.domain.model.site.Site
import se.qbtech.qbx.domain.payment.BillingCommand

/**
 * @author Michael Astreiko
 */
class BraintreePaymentService {
    static transactional = false

    def braintreeGateway

    /**
     * Executes sale (authorize only) transaction using Braintree account.
     * Throws exception in case when transaction was not successful.
     *
     * @param billingCommand
     * @param transactionAmount
     *
     * @return Braintree transaction Id
     */
    String executeSaleTransaction(BillingCommand billingCmd, BigDecimal transactionAmount) {
        def transactionRequest = new TransactionRequest()
                .amount(transactionAmount)
                .creditCard()
                .cardholderName(billingCmd.cardHolderName)
                .number(billingCmd.cardNumber)
                .cvv(billingCmd.cardSecurityCode)
                .expirationMonth(billingCmd.cardExpireMonth)
                .expirationYear(billingCmd.cardExpireYear)
                .done()
                .options()
                .submitForSettlement(true)
                .done()

        Result<Transaction> result = braintreeGateway.transaction().sale(transactionRequest)
        if (result.success) {
            return result.target.id
        } else {
            throw new Exception(result.message)
        }
    }

    /**
     * Executes sale (authorize only) transaction using Braintree account.
     * Throws exception in case when transaction was not successful.
     *
     * @param site
     * @param transactionAmount
     *
     * @return Braintree transaction Id
     */
    String executeSaleTransactionForSite(Site site, BigDecimal transactionAmount) {
        def transactionRequest = new TransactionRequest()
                .amount(transactionAmount)
                .customerId(site.braintreeCustomerId)

        Result<Transaction> result = braintreeGateway.transaction().sale(transactionRequest)
        if (result.success) {
            return result.target.id
        } else {
            throw new Exception(result.message)
        }
    }

    /**
     * Register Site as Customer in Braintree
     *
     * @param site
     * @return customer Id which is given by Braintree to this customer - should be used in future transactions
     */
    String registerCustomer(Site site) {
        CustomerRequest customerRequest = new CustomerRequest()
                .company(site.name)
                .email(site.email)
                .phone(site.phone)

        Result<Customer> result = braintreeGateway.customer().create(customerRequest)
        if (result.isSuccess()) {
            return result.getTarget().getId()
        } else {
            log.warn "Cannot register site ${site.name} in Braintree due to ${result.message}: ${result.errors.allValidationErrors}"
            return null
        }
    }

    boolean registerCreditCardForSite(Site site, BillingCommand billingCmd) {
        CustomerRequest customerRequest = new CustomerRequest()
                .creditCard()
                .cardholderName(billingCmd.cardHolderName)
                .number(billingCmd.cardNumber)
                .cvv(billingCmd.cardSecurityCode)
                .expirationMonth(billingCmd.cardExpireMonth)
                .expirationYear(billingCmd.cardExpireYear)
                .done()

        Result<Customer> updateResult = braintreeGateway.customer().update(site.braintreeCustomerId, customerRequest)
        if (!updateResult.isSuccess()) {
            log.warn "Cannot register site ${site.name} in Braintree due to ${updateResult.message}: ${updateResult.errors.allValidationErrors}"
            return false
        }
        return true
    }

    String getCreditCardDetailsForSite(Site site) {
        Customer customer = braintreeGateway.customer().find(site.braintreeCustomerId)
        return customer.creditCards ? customer.creditCards?.get(0)?.last4 : null
    }
}
