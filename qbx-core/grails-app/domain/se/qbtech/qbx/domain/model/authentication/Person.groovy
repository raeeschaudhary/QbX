package se.qbtech.qbx.domain.model.authentication

import se.qbtech.qbx.domain.model.common.BasePersistentObject

/**
 * @author Michael Astreiko
 */
class Person extends BasePersistentObject {

    transient springSecurityService

    String password
    String firstName
    String lastName
    //Email for username
    String email

    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    Date lastLoginDate

    static constraints = {
        password blank: false
        email email: true, unique: true, blank: false
        firstName blank: false
        lastName blank: false
        lastLoginDate nullable: true
    }

    static mapping = {
        password column: '`password`'
    }

    Set<Role> getAuthorities() {
        //Will look into only for existed User
        if (id) {
            Person.withNewSession {
                PersonRole.findAllByPerson(this).collect { it.role } as Set
            }
        } else {
            return [] as Set
        }
    }

    def beforeInsert() {
        super.beforeInsert()
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
}