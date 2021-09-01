import com.braintreegateway.BraintreeGateway

// Place your Spring DSL code here
beans = {
    braintreeGateway(BraintreeGateway,
            grailsApplication.config.braintree.environment,
            grailsApplication.config.braintree.merchantId,
            grailsApplication.config.braintree.publicKey,
            grailsApplication.config.braintree.privateKey)
}
