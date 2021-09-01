package se.qbtech.qbx.domain.model.common

import se.qbtech.qbx.domain.model.authentication.Person

/**
 * @author Michael Astreiko
 */
abstract class BasePersistentObject implements Serializable {
    def springSecurityService
    def publicIdGenerationService

    static transients = ['publicIdGenerationService', 'springSecurityService']

    static mapping = {
        tablePerHierarchy false
    }
    String publicId

    Date dateCreated // autoupdated by GORM
    Date lastUpdated // autoupdated by GORM
    String createdBy

    static constraints = {
        createdBy(nullable: true)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
    }

    def beforeInsert() {
        if (!createdBy) {
            //Need to determine user who created object
            Person person = springSecurityService?.getCurrentUser()
            createdBy = person?.email ?: 'system'
        }
        insertPublicIdIfNeeded()
    }

    def beforeValidate() {
        insertPublicIdIfNeeded()
    }

    private void insertPublicIdIfNeeded() {
        //Need to determine publicId if not set (new instance)
        if (!publicId) {
            publicId = publicIdGenerationService?.generatePublicId(getClass())
        }
    }

    protected static phoneValidator = { val, obj ->
        def phoneNumberToMatch = val?.replaceAll(/\s|\(|\)/, '')
        String pattern = "^[+][0-9]{0,14}\$"
        if (phoneNumberToMatch && !(phoneNumberToMatch ==~ pattern)) {
            return "${obj.class.simpleName}.error.phone"
        }
    }
}
