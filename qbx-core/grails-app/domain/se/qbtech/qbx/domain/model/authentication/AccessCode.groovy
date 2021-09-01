package se.qbtech.qbx.domain.model.authentication

import se.qbtech.qbx.domain.model.common.BasePersistentObject
import se.qbtech.qbx.domain.model.site.Site

/**
 * @author Michael Astreiko
 */
class AccessCode extends BasePersistentObject {
    static List EXISTING_CODES = ["4922", "7442", "4851", "1298", "3556"]
    Site site
    String qbTestPatientId

    static mapping = {
        version false
    }

    static constraints = {
        site(nullable: true)
        publicId(unique: "site")
    }
}
