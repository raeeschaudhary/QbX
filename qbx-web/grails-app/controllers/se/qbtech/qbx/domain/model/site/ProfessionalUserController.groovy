package se.qbtech.qbx.domain.model.site

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import se.qbtech.qbx.domain.model.authentication.PersonRole
import se.qbtech.qbx.domain.model.authentication.Role

import static javax.servlet.http.HttpServletResponse.*

class ProfessionalUserController {
    def springSecurityService

    static final int SC_UNPROCESSABLE_ENTITY = 422

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {}

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.sort = 'id'
        params.order = 'desc'
        response.setIntHeader('X-Pagination-Total', ProfessionalUser.count())
        render ProfessionalUser.list(params) as JSON
    }

    def totalList() {
        render template: '/professionalUser/list', model: [sites: Site.list()]
    }

    def search() {
        String query = params.query ? "%${params.query}%" : ""

        def responseJson = ProfessionalUser.withCriteria {
            or {
                like('firstName', query)
                like('lastName', query)
                like('email', query)
                like('telephone', query)
                site {
                    like('name', query)
                }
            }
        }

        render responseJson as JSON
    }

    def save() {
        def jsonData = request.JSON
        def professionalUser = new ProfessionalUser()
        bindData(professionalUser, jsonData, ["site"])
        professionalUser.site = Site.read(jsonData.site?.id)
        professionalUser.enabled = true

        def responseJson = [:]
        if (professionalUser.save(flush: true)) {
            createProfessionalUserRole(professionalUser)

            response.status = SC_CREATED
            responseJson.id = professionalUser.id
            responseJson.item = professionalUser
            responseJson.message = message(code: 'default.created.message',
                    args: [message(code: 'professionalUser.label', default: 'ProfessionalUser'), professionalUser.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = professionalUser.errors.fieldErrors.collect {
                ['field': it.field, 'error': message(error: it)]
            }
        }
        render responseJson as JSON
    }

    private void createProfessionalUserRole(ProfessionalUser professionalUser) {
        def role = Role.findByAuthority(Role.AvailableRoles.PROFESSIONAL_USER.value())
        def personRole = PersonRole.findByPersonAndRole(professionalUser, role)

        if (!personRole) {
            PersonRole.withNewSession {
                PersonRole.create(professionalUser, role, true)
            }
        }
    }

    def changePassword() {
        render template: '/professionalUser/changePassword'
    }

    def setPassword() {
        def errorMessage = ""
        def message = ""
        def status = "ok"
        def jsonData = request.JSON

        if (!jsonData.newPassword?.equals(jsonData.confirmPassword)) {
            errorMessage = g.message(code: 'password.wrongConfirm')
        } else {
            def user = ProfessionalUser.get(jsonData.id)
            user.password = jsonData.newPassword

            if (!user.hasErrors() && user.save()) {
                message = g.message(code: 'professionalUser.password.updated')
            }
        }

        def result = [:]
        result.content = message
        if (errorMessage) {
            status = ""
            result.content = errorMessage
        }
        result.status = status

        render result as JSON
    }

    def get() {
        def professionalUser = ProfessionalUser.get(params.id)
        if (professionalUser) {
            render professionalUser as JSON
        } else {
            notFound params.id
        }
    }

    def update() {
        def professionalUser = ProfessionalUser.get(params.id)
        if (!professionalUser) {
            notFound params.id
            return
        }

        def responseJson = [:]

        if (request.JSON.version != null) {
            if (professionalUser.version > request.JSON.version) {
                response.status = SC_CONFLICT
                responseJson.message = message(code: 'default.optimistic.locking.failure',
                        args: [message(code: 'professionalUser.label', default: 'ProfessionalUser')],
                        default: 'Another user has updated this ProfessionalUser while you were editing')
                cache false
                render responseJson as JSON
                return
            }
        }

        def jsonData = request.JSON

        bindData(professionalUser, jsonData, ["site", "password"])
        professionalUser.site = Site.read(jsonData.site?.id)

        if (professionalUser.save(flush: true)) {
            response.status = SC_OK
            responseJson.id = professionalUser.id
            responseJson.item = professionalUser
            responseJson.message = message(code: 'default.updated.message',
                    args: [message(code: 'professionalUser.label', default: 'ProfessionalUser'), professionalUser.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = professionalUser.errors.fieldErrors.collect {
                ['field': it.field, 'error': message(error: it)]
            }
        }

        render responseJson as JSON
    }

    def delete() {
        def professionalUser = ProfessionalUser.get(params.id)
        if (!professionalUser) {
            notFound params.id
            return
        }

        def responseJson = [:]
        try {
            PersonRole.findByPerson(professionalUser)?.delete(flush: true)
            professionalUser.delete(flush: true)
            responseJson.message = message(code: 'default.deleted.message',
                    args: [message(code: 'professionalUser.label', default: 'ProfessionalUser'), params.id])
        } catch (DataIntegrityViolationException e) {
            response.status = SC_CONFLICT
            responseJson.message = message(code: 'default.not.deleted.message',
                    args: [message(code: 'professionalUser.label', default: 'ProfessionalUser'), params.id])
        }
        render responseJson as JSON
    }

    private void notFound(id) {
        response.status = SC_NOT_FOUND
        def responseJson = [message: message(code: 'default.not.found.message',
                args: [message(code: 'professionalUser.label', default: 'ProfessionalUser'), id])]
        render responseJson as JSON
    }
}
