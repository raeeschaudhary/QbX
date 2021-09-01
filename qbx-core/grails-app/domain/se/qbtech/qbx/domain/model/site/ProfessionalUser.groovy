package se.qbtech.qbx.domain.model.site

import se.qbtech.qbx.domain.model.authentication.Person

/**
 * @author Michael Astreiko
 */
class ProfessionalUser extends Person {
    static List VALID_DATE_FORMATS = ["MM/dd/yyyy", "dd/MM/yyyy", "yyyy-MM-dd", "dd-MM-yyyy", "dd.MM.yyyy"]
    String telephone
    String language
    String preferredDateFormat

    static belongsTo = [site: Site]

    static constraints = {
        telephone(nullable: true, validator: phoneValidator)
        language(nullable: true)
        preferredDateFormat(nullable: true, inList: VALID_DATE_FORMATS)
    }
}
