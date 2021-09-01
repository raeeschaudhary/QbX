package se.qbtech.qbx.domain.model.qtest

import grails.converters.JSON
import static javax.servlet.http.HttpServletResponse.*

class TransactionController {

    def customerService

    static final int SC_UNPROCESSABLE_ENTITY = 422

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {}

    def list() {
        def (transactions, int total) = customerService.getTransactions(params)
        response.setIntHeader('X-Pagination-Total', total)
        render transactions as JSON
    }

    def totalList() {
        render template: '/transaction/list'
    }

    def search() {
        def responseJson = customerService.searchTransactions(params)

        render responseJson as JSON
    }

    def get() {
        def transaction = Transaction.get(params.id)
        if (transaction) {
            render transaction as JSON
        } else {
            notFound params.id
        }
    }

    private void notFound(id) {
        response.status = SC_NOT_FOUND
        def responseJson = [message: message(code: 'default.not.found.message', args: [message(code: 'transaction.label', default: 'Transaction'), params.id])]
        render responseJson as JSON
    }
}
