package se.qbtech.qbx.domain.model.site

import grails.converters.JSON
import se.qbtech.qbx.domain.payment.BillingCommand

import javax.servlet.http.HttpServletResponse

class ProfessionalUserActionsController {
    def springSecurityService
    def braintreePaymentService

    static final int SC_UNPROCESSABLE_ENTITY = 422

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {

    }

    def transactions() {
        forward controller: 'transaction', action: 'list'
    }

    def searchTransactions() {
        forward controller: 'transaction', action: 'search', params: request.JSON
    }

    def testSubjects() {
        forward controller: 'testSubject', action: 'list'
    }

    def searchTestSubjects() {
        forward controller: 'testSubject', action: 'search'
    }

    def transactionsTpl() {
        forward controller: 'transaction', action: 'totalList'
    }

    def testSubjectsTpl() {
        forward controller: 'testSubject', action: 'totalList'
    }

    def siteInfoTpl() {
        render template: '/professionalUserActions/siteInfo'
    }

    def billingInfoTpl() {
        render template: '/professionalUserActions/billingInfo'
    }

    def updateSite() {

    }

    def updateTestSubject() {
        forward controller: 'testSubject', action: 'update'
    }

    def deleteTestSubject() {
        forward controller: 'testSubject', action: 'delete'
    }

    def saveTestSubject() {
        forward controller: 'testSubject', action: 'save'
    }

    def siteInfo() {
        def currentUser = springSecurityService.currentUser as ProfessionalUser

        render currentUser.site as JSON
    }

    def last4() {
        def currentUser = springSecurityService.currentUser as ProfessionalUser
        def site = currentUser.site
        if (site) {
            def last4 = [last4 : braintreePaymentService.getCreditCardDetailsForSite(site)]
            render last4 as JSON
        } else {
            render ""
        }
    }

    def saveSiteInfo() {
        forward controller: 'site', action: 'update'
    }

    def changeCard() {
        render template: '/professionalUserActions/addNewCard'
    }

    def updateCard() {
        def siteInfo = request.JSON
        def responseJson = [:]
        try {
            def site = Site.get(siteInfo.id)
            def command = new BillingCommand(
                    cardExpireMonth: siteInfo.cardExpireMonth,
                    cardExpireYear: siteInfo.cardExpireYear,
                    cardNumber: siteInfo.cardNumber,
                    cardSecurityCode: siteInfo.cardSecurityCode,
                    cardHolderName: siteInfo.cardHolderName
            )

            if (braintreePaymentService.registerCreditCardForSite(site, command)) {
                response.status = HttpServletResponse.SC_OK
                responseJson.id = siteInfo.id
                responseJson.message = message(code: 'default.updated.message', args: [message(code: 'site.label', default: 'Site'), site.id])
                responseJson.item = [last4: braintreePaymentService.getCreditCardDetailsForSite(site)]
            } else {
                flash.error = "Cannot register card for ${site.name}"
            }
        } catch (ex) {
            flash.error = ex.message
        }
        if (!responseJson.item) {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = flash.error
        }
        render responseJson as JSON

    }
}
