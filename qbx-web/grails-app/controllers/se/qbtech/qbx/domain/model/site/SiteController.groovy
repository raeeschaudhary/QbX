package se.qbtech.qbx.domain.model.site

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import se.qbtech.qbx.domain.model.authentication.PersonRole
import se.qbtech.qbx.domain.model.common.Address

import static javax.servlet.http.HttpServletResponse.*

class SiteController {

    def notificationService

    static final int SC_UNPROCESSABLE_ENTITY = 422

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {}

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.sort = 'id'
        params.order = 'desc'
        response.setIntHeader('X-Pagination-Total', Site.count())
        render Site.list(params) as JSON
    }

    def totalList() {
        //todo: remove Voucher from payment methods at all
        render template: '/site/list', model: [sites: Site.list(), paymentMethods: (Site.PaymentMethod.values() - Site.PaymentMethod.VOUCHER)*.name()]
    }

    def search() {
        String query = params.query ? "%${params.query}%" : ""

        def responseJson = Site.withCriteria {
            or {
                like('name', query)
                like('notes', query)
                like('email', query)
                like('phone', query)

                sqlRestriction("payment_method like ?", ["${query}".toString()])
            }
        }

        render responseJson as JSON
    }

    def save() {
        def jsonData = request.JSON
        def site = new Site(jsonData)

        //bind payment method if needed
        if (jsonData?.paymentMethod && !(jsonData?.paymentMethod instanceof String)) {
            try {
                String paymentMethodName = jsonData.paymentMethod.name
                site.paymentMethod = Site.PaymentMethod.valueOf(paymentMethodName)
            }
            catch (IllegalArgumentException e) {
                if (log.isErrorEnabled()) {
                    log.error e.message
                }
            }
        }


        addAddressesIfNeeded(jsonData, site)
        def responseJson = [:]
        if (site.save(flush: true)) {
            response.status = SC_CREATED
            responseJson.item = site
            responseJson.id = site.id
            responseJson.message = message(code: 'default.created.message', args: [message(code: 'site.label', default: 'Site'), site.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = site.errors.fieldErrors.collect {
                ['field': it.field, 'error': message(error: it)]
            }
        }
        render responseJson as JSON
    }

    def changeAddress(String type) {
        render template: '/site/changeAddress', model: [type: type]
    }

    def editAddress() {
        def jsonData = request.JSON

        Site site = Site.get(jsonData.id)

        if (!site) {
            notFound params.id
            return
        }

        addAddressesIfNeeded(jsonData, site)

        def responseJson = [:]

        if (site.save(flush: true)) {
            response.status = SC_OK
            responseJson.id = site.id
            responseJson.message = message(code: 'default.updated.message', args: [message(code: 'site.label', default: 'Site'), site.id])
            responseJson.item = site
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = site.errors.fieldErrors.collect {
                ['field': it.field, 'error': message(error: it)]
            }
        }

        render responseJson as JSON
    }

    private void addAddressesIfNeeded(jsonData, Site site) {
        if (jsonData.address) {
            Address address = new Address(jsonData.address)
            site.address = address
        }
        if (jsonData.billingAddress) {
            Address billingAddress = new Address(jsonData.billingAddress)
            site.billingAddress = billingAddress
        }
    }


    def get() {
        def site = Site.get(params.id)
        if (site) {
            render site as JSON
        } else {
            notFound params.id
        }
    }

    def update() {

        def requestJson = request.JSON
        def site = Site.get(params.id)
        if (!site) {
            notFound params.id
            return
        }

        def responseJson = [:]

        if (requestJson.version != null) {
            if (site.version > requestJson.version) {
                response.status = SC_CONFLICT
                responseJson.message = message(code: 'default.optimistic.locking.failure',
                        args: [message(code: 'site.label', default: 'Site')],
                        default: 'Another user has updated this Site while you were editing')
                render responseJson as JSON
                return
            }
        }

        bindData(site, requestJson, ['users', 'vouchers', 'testSubjects', 'address', 'billingAddress'])
        site.paymentMethod = Site.PaymentMethod.values().find { it.name() == requestJson.paymentMethod?.name }
        addAddressesIfNeeded(requestJson, site)

        if (site.save(flush: true)) {
            response.status = SC_OK
            responseJson.id = site.id
            responseJson.item = site
            responseJson.message = message(code: 'default.updated.message', args: [message(code: 'site.label', default: 'Site'), site.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = site.errors.fieldErrors.collect {
                ['field': it.field, 'error': message(error: it)]
            }
        }

        render responseJson as JSON
    }

    def delete() {
        def site = Site.get(params.id)
        if (!site) {
            notFound params.id
            return
        }

        def responseJson = [:]
        try {
            //@todo: make in beforeDelete()
            site.users.each { ProfessionalUser professionalUser ->
                PersonRole.findByPerson(professionalUser)?.delete(flush: true)
                site.removeFromUsers(professionalUser)
                professionalUser.delete(flush: true)
            }
            site.delete(flush: true)
            responseJson.message = message(code: 'default.deleted.message', args: [message(code: 'site.label', default: 'Site'), params.id])
        } catch (DataIntegrityViolationException e) {
            response.status = SC_CONFLICT
            responseJson.message = message(code: 'default.not.deleted.message', args: [message(code: 'site.label', default: 'Site'), params.id])
        }
        render responseJson as JSON
    }

    private void notFound(id) {
        response.status = SC_NOT_FOUND
        def responseJson = [message: message(code: 'default.not.found.message', args: [message(code: 'site.label', default: 'Site'), params.id])]
        render responseJson as JSON
    }

    /**
     * Register new site
     * @todo: Create site disabled, send email to qbtech for review.
     */
    def registration() {
        def jsonData = request.JSON
        def site = new Site()
        site.name = jsonData?.name
        site.email = jsonData?.email

        addAddressesIfNeeded(jsonData, site)
        bindPayment(jsonData, site)

        def responseJson = [:]

        if (site.save(flush: true) && !site.hasErrors()) {
            try {
                notificationService.sendEmailWithSiteInfoForReview(site)
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error e.message
                }
            }

            response.status = SC_OK
            responseJson.status = "ok"
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = site.errors.fieldErrors.collect {
                ['field': it.field, 'error': message(error: it)]
            }
        }
        render responseJson as JSON
    }

    private void bindPayment(jsonData, Site site) {
        def payment = jsonData.payment
        if (payment == g.message(code: 'paymentMethod.INVOICE')) {
            site.paymentMethod = Site.PaymentMethod.INVOICE
        } else if (payment == g.message(code: 'paymentMethod.CREDIT_CARD')) {
            site.paymentMethod = Site.PaymentMethod.CREDIT_CARD
        }
    }

    /**
     * Sign up new site
     * @return
     */
    def signUp() {
        render view: '/customer/signUp', model: [site: new Site()]
    }
}
