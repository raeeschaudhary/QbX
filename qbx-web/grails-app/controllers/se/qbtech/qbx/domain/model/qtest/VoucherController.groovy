package se.qbtech.qbx.domain.model.qtest

import grails.converters.JSON
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.dao.DataIntegrityViolationException
import se.qbtech.qbx.domain.model.site.Site

import static javax.servlet.http.HttpServletResponse.*

class VoucherController {

    static final int SC_UNPROCESSABLE_ENTITY = 422

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {}

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.sort = 'id'
        params.order = 'desc'
        response.setIntHeader('X-Pagination-Total', Voucher.count())
        render Voucher.list(params) as JSON
    }

    def totalList() {
        render template: '/voucher/list', model: [sites: Site.list()]
    }

    def search() {
        String query = params.query ? "%${params.query}%" : ""

        def responseJson = Voucher.withCriteria {
            or {
                like('code', query)
                site {
                    like('name', query)
                }
                sqlRestriction("status like ?", ["${query}".toString()])
            }
        }

        render responseJson as JSON
    }

    def save() {
        def requestParams = request.JSON
        def voucher = new Voucher()

        Site site = Site.get(requestParams.site?.id)

        voucher.site = site
        voucher.status = Voucher.VoucherStatus.NOT_USED
        voucher.code = RandomStringUtils.randomAlphanumeric(8)

        def responseJson = [:]
        if (voucher.save(flush: true)) {
            response.status = SC_CREATED
            responseJson.id = voucher.id
            responseJson.message = message(code: 'default.created.message', args: [message(code: 'voucher.label', default: 'Voucher'), voucher.id])
            responseJson.item = voucher
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = voucher.errors.fieldErrors.collect {
                ['field': it.field, 'error': message(error: it)]
            }
        }
        render responseJson as JSON
    }

    def get() {
        def voucher = Voucher.get(params.id)
        if (voucher) {
            render voucher as JSON
        } else {
            notFound params.id
        }
    }

    def update() {
        def requestParams = request.JSON
        def voucher = Voucher.get(requestParams.id)
        if (!voucher) {
            notFound params.id
            return
        }

        def responseJson = [:]

        if (request.JSON.version != null) {
            if (voucher.version > request.JSON.version) {
                response.status = SC_CONFLICT
                responseJson.message = message(code: 'default.optimistic.locking.failure',
                        args: [message(code: 'voucher.label', default: 'Voucher')],
                        default: 'Another user has updated this Voucher while you were editing')
                render responseJson as JSON
                return
            }
        }

        if (requestParams?.site) {
            voucher.site = Site.get(requestParams?.site?.id)
        }

        if (voucher.save(flush: true)) {
            response.status = SC_OK
            responseJson.id = voucher.id
            responseJson.item = voucher
            responseJson.message = message(code: 'default.updated.message', args: [message(code: 'voucher.label', default: 'Voucher'), voucher.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = voucher.errors.fieldErrors.collect {
                ['field': it.field, 'error': message(error: it)]
            }
        }

        render responseJson as JSON
    }

    def delete() {
        def voucher = Voucher.get(params.id)
        if (!voucher) {
            notFound params.id
            return
        }

        def responseJson = [:]
        try {
            if (voucher.status == Voucher.VoucherStatus.NOT_USED) {
                voucher.delete(flush: true)
            } else if (voucher.status == Voucher.VoucherStatus.USED) {
                throw new DataIntegrityViolationException("Voucher cannot be deleted as it already used")
            }
            responseJson.message = message(code: 'default.deleted.message', args: [message(code: 'voucher.label', default: 'Voucher'), params.id])
        } catch (DataIntegrityViolationException e) {
            response.status = SC_CONFLICT
            responseJson.message = message(code: 'default.not.deleted.message', args: [message(code: 'voucher.label', default: 'Voucher'), params.id])
        }
        render responseJson as JSON
    }

    private void notFound(id) {
        response.status = SC_NOT_FOUND
        def responseJson = [message: message(code: 'default.not.found.message', args: [message(code: 'voucher.label', default: 'Voucher'), params.id])]
        render responseJson as JSON
    }
}
