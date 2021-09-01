package se.qbtech.qbx.service.notification

import org.springframework.context.i18n.LocaleContextHolder as LCH
import se.qbtech.qbx.domain.model.qtest.TestResult
import se.qbtech.qbx.domain.model.site.Site

/**
 * @author Vladimir Havenchyk 
 */
class NotificationService {
    def mailService
    def messageSource
    def grailsApplication
    def grailsLinkGenerator

    static transactional = false

    void sendEmailWithSiteInfoForReview(Site site) {
        def model = getEmailModel(site)
        String emailSubject = messageSource.getMessage('email.review.subject', null, LCH.locale)

        String reviewEmail = grailsApplication.config.qbx.reviewEmail

        mailService.sendMail {
            multipart true
            from "daniel.zakrisson@qbtech.com"
            to reviewEmail
            subject emailSubject
            headers "Message-Id": "<${new Date().format("yyMMdd")}.${new Date().format("HHmmss")}@qbtech.se>"
            html view: "/mail/siteReview", model: model
            text view: "/mail/siteReview_txt", model: model
        }

        if (log.isInfoEnabled()) {
            log.info "Sent Email to qbtech for review site ${site.name}"
        }
    }
    /**
     * |sitename|
     * |email|
     * |telephone|
     * |billing address|
     * |visit address|
     * Preferred payment method: |paymentmethod|
     */
    private Map getEmailModel(Site site) {
        [siteName: site.name, telephone: site.phone, email: site.email,
                billingAddress: site.billingAddress ? [site.billingAddress.address1, site.billingAddress.address2].join(', ') : '',
                visitAddress: site.address ? [site.address.address1, site.address.address2].join(', ') : '',
                paymentMethod: site.paymentMethod, titleCode: 'site.review.title']
    }

    /**
     * Shares test result with email
     */
    void sendReportEmail(String email, String heading, String message, Long testResultId) {
        def testResult = TestResult.get(testResultId)
        def reportLink = grailsLinkGenerator.link(uri: '/report/' + testResult.publicId, absolute: true)

        def model = [message: message, reportLink: reportLink]

        mailService.sendMail {
            multipart true
            from "daniel.zakrisson@qbtech.com"
            to email
            subject heading
            headers "Message-Id": "<${new Date().format("yyMMdd")}.${new Date().format("HHmmss")}@qbcheck.com>"
            html view: "/mail/testResult", model: model
            text view: "/mail/testResult_txt", model: model
        }

        if (log.isInfoEnabled()) {
            log.info "Sent Email to ${email} with report about ${testResult.testSubject}"
        }
    }
}
