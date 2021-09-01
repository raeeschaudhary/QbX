package se.qbtech.qbx.domain.model.common

/**
 * @author Michael Astreiko
 */
class Address {
    String address1
    String address2
    String city
    String state
    String zipCode
    String countryCode

    static constraints = {
        address2(nullable: true)
        state(nullable: true)
        zipCode(nullable: true)
    }
}
