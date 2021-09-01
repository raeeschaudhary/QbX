package se.qbtech.qbx.domain.model.common

import grails.converters.JSON
import se.qbtech.qbx.common.AppConstants
import se.qbtech.qbx.domain.model.authentication.AccessCode
import se.qbtech.qbx.domain.model.qtest.Voucher
import se.qbtech.qbx.domain.model.site.ProfessionalUser
import se.qbtech.qbx.domain.model.site.Site

import javax.servlet.http.Cookie

/**
 * @author Michael Astreiko
 */
class InfoController {
    def springSecurityService
    def braintreePaymentService

    def index() {
    }

    def splash() {
    }

    def landing() {

    }

    def moreOnAdhd() {

    }

    def aboutTest() {

    }

    def pricing() {

    }

    def faq() {

    }

    def dataPrivacy() {

    }

    def home() {
        if (springSecurityService.isLoggedIn()) {
            def currentUser = springSecurityService.currentUser

            if (currentUser instanceof ProfessionalUser) {
                Site site = currentUser.site
                if (site.paymentMethod == Site.PaymentMethod.CREDIT_CARD &&
                        !braintreePaymentService.getCreditCardDetailsForSite(site)) {
                    redirect(uri: '/professionalUserActions/#/siteInfo')
                } else {
                    render view: '/info/home'
                }
            } else {
                render view: '/index'
            }
        } else {
            forward action: 'landing'
        }
    }

    def afterSiteSubmitting() {
        flash.infoMessage = g.message(code: 'professionalUser.site.thanks')
        redirect controller: 'info', action: 'landing'
    }

    def putVoucherCode(String voucherCode) {
        def voucher = Voucher.findByCode(voucherCode)
        if (voucher) {
            if (voucher.status == Voucher.VoucherStatus.NOT_USED) {
                def cookie = new Cookie(AppConstants.VOUCHER_COOKIE_NAME, voucherCode)
                cookie.path = "/"
                //one year
                cookie.maxAge = 365 * 60 * 60 * 24
                response.addCookie(cookie)
                redirect(controller: "info", action: "landing")
            } else {
                flash.error = message(code: 'error.voucher.expired')
                render(view: "splash")
            }
        } else {
            flash.error = message(code: 'error.voucher.notExist')
            render(view: "splash")
        }

    }

    def putPatientInfo(String patientId, String accessCode) {
        if (AccessCode.countByPublicId(patientId) && AccessCode.EXISTING_CODES.contains(accessCode)) {
            def cookie = new Cookie(AppConstants.ACCESS_CODE_COOKIE_NAME, "${accessCode}:${patientId}".toString())
            cookie.path = "/"
            //one year
            cookie.maxAge = 365 * 60 * 60 * 24
            response.addCookie(cookie)
            redirect(controller: "info", action: "landing")
        } else {
            flash.error = message(code: 'error.accessCode.notExist')
            render(view: "splash")
        }
    }

    def loadMessage(String code) {
        def responseJson = [:]
        responseJson.message = code ? message(code: code) : ''
        render responseJson as JSON
    }
}
