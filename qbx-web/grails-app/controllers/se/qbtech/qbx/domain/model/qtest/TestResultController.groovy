package se.qbtech.qbx.domain.model.qtest

import grails.converters.JSON

import static javax.servlet.http.HttpServletResponse.SC_OK

/**
 * @author Vladimir Havenchyk 
 */
class TestResultController {
    def notificationService

    static final int SC_UNPROCESSABLE_ENTITY = 422


    def showReport(String token) {
        def testResult = TestResult.findByPublicId(token)

        if (testResult) {
            render view: '/customer/report/report', model: [testResult: testResult]
        } else {
            flash.infoMessage = g.message(code: 'report.notFound')
            redirect controller: 'info', action: 'landing'
        }
    }

    def renderReportPdf(String token) {
        def testResult = TestResult.findByPublicId(token)

        renderPdf([template: '/customer/report/report', model: [testResult: testResult]])
    }

    def sendReportEmail(Long id) {
        def requestParams = request.JSON
        String email = requestParams.email
        String subject = requestParams.subject
        String message = requestParams.message

        def responseJson = [:]
        try {
            notificationService.sendReportEmail(email, subject, message, id)

            response.status = SC_OK
            responseJson.message = g.message(code: 'report.email.sent', args: [email])
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error e.message
            }

            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = g.message(code: 'report.email.error')
        }

        render responseJson as JSON
    }

    def renderEmailTpl() {
        render template: '/customer/report/emailModal'
    }
}
