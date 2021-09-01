package se.qbtech.qbx.domain.model.authentication

class Role {
    String authority

    static mapping = {
        cache true
    }

    static constraints = {
        authority blank: false, unique: true
    }

    enum AvailableRoles {
        SYSTEM_ADMIN("ROLE_SYSTEM_ADMIN"), PROFESSIONAL_USER("ROLE_PROFESSIONAL_USER")//, TEST_SUBJECT("ROLE_TEST_SUBJECT")

        String roleId

        private AvailableRoles(String id) {
            this.roleId = id
        }

        String value() {
            roleId
        }
    }
}
