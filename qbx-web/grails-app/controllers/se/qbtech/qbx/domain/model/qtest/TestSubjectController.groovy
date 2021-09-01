package se.qbtech.qbx.domain.model.qtest

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import se.qbtech.qbx.domain.model.site.ProfessionalUser
import se.qbtech.qbx.domain.model.site.Site

import static javax.servlet.http.HttpServletResponse.*

class TestSubjectController {
    def customerService
    def springSecurityService

    static final int SC_UNPROCESSABLE_ENTITY = 422

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {}

    def list() {
        def (testSubjects, int total) = customerService.getTestSubjects(params)
        response.setIntHeader('X-Pagination-Total', total)
        render testSubjects as JSON
    }

    def totalList() {
        def currentUser = springSecurityService.currentUser
        def sites = currentUser instanceof ProfessionalUser ? [] << currentUser.site : Site.list()

        render template: '/testSubject/list', model: [sites: sites]
    }

    def save() {
        def testSubject = new TestSubject()

        bindRequestParams(testSubject)

        def responseJson = [:]
        if (testSubject.save(flush: true)) {
            response.status = SC_CREATED
            responseJson.id = testSubject.id
            responseJson.message = message(code: 'default.created.message', args: [message(code: 'testSubject.label', default: 'TestSubject'), testSubject.id])
            responseJson.item = testSubject
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = testSubject.errors.fieldErrors.collect {
                ['field': it.field, 'error': message(error: it)]
            }
        }
        render responseJson as JSON
    }

    def search() {
        def responseJson = customerService.searchTestSubjects(params)

        render responseJson as JSON
    }

    private void bindRequestParams(TestSubject testSubject) {
        def requestParams = request.JSON
        bindData(testSubject, requestParams, ['dateOfBirth', 'site', 'testResults', 'vouchers'])

        //@todo: make with validator in domain
        def currentUser = springSecurityService.currentUser
        if (currentUser instanceof ProfessionalUser) {
            testSubject.site = currentUser.site
        } else if (requestParams?.site) {
            Site site = Site.get(requestParams?.site?.id)
            testSubject.site = site
        }

        if (requestParams.dateOfBirth) {
            def format = g.message(code: 'default.date.format').toString()
            testSubject.dateOfBirth = Date.parse(format, requestParams.dateOfBirth as String)
        }

    }

    def get() {
        def testSubject = TestSubject.get(params.id)
        if (testSubject) {
            render testSubject as JSON
        } else {
            notFound params.id
        }
    }

    def update() {
        def testSubject = TestSubject.get(params.id)
        if (!testSubject) {
            notFound params.id
            return
        }

        def responseJson = [:]
        def requestParams = request.JSON

        if (requestParams.version != null) {
            if (testSubject.version > requestParams.version) {
                response.status = SC_CONFLICT
                responseJson.message = message(code: 'default.optimistic.locking.failure',
                        args: [message(code: 'testSubject.label', default: 'TestSubject')],
                        default: 'Another user has updated this TestSubject while you were editing')
                render responseJson as JSON
                return
            }
        }

        //@todo: make binding explicitly
        bindRequestParams(testSubject)


        if (testSubject.save(flush: true)) {
            response.status = SC_OK
            responseJson.id = testSubject.id
            responseJson.message = message(code: 'default.updated.message', args: [message(code: 'testSubject.label', default: 'TestSubject'), testSubject.id])
            responseJson.item = testSubject
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = testSubject.errors.fieldErrors.collect {
                ['field': it.field, 'error': message(error: it)]
            }
        }

        render responseJson as JSON
    }

    def delete() {
        def testSubject = TestSubject.get(params.id)
        if (!testSubject) {
            notFound params.id
            return
        }

        def responseJson = [:]
        try {
            testSubject.delete(flush: true)
            responseJson.message = message(code: 'default.deleted.message', args: [message(code: 'testSubject.label', default: 'TestSubject'), params.id])
        } catch (DataIntegrityViolationException e) {
            response.status = SC_CONFLICT
            responseJson.message = message(code: 'default.not.deleted.message', args: [message(code: 'testSubject.label', default: 'TestSubject'), params.id])
        }
        render responseJson as JSON
    }

    private void notFound(id) {
        response.status = SC_NOT_FOUND
        def responseJson = [message: message(code: 'default.not.found.message', args: [message(code: 'testSubject.label', default: 'TestSubject'), params.id])]
        render responseJson as JSON
    }
}