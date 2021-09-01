import org.apache.commons.lang.BooleanUtils
import se.qbtech.qbx.common.AppConstants
import se.qbtech.qbx.domain.model.authentication.AccessCode
import se.qbtech.qbx.domain.model.qtest.Voucher

class SecurityFilters {

    def filters = {
        invitationAcceptedFilter(uri: "/**") {
            before = {
                //Show Blocking page - only for limited period
                if (!BooleanUtils.toBoolean(grailsApplication.config.qbx.publicAccess.enabled)) {
                    def voucherCookie = request.cookies.find { it.name == AppConstants.VOUCHER_COOKIE_NAME }
                    def accessCodeCookie = request.cookies.find { it.name == AppConstants.ACCESS_CODE_COOKIE_NAME }
                    if (accessCodeCookie?.value) {
                        def accessCode = accessCodeCookie.value.split(":")?.getAt(0)
                        def patientId = accessCodeCookie.value.split(":")?.getAt(1)
                        if (AccessCode.countByPublicId(patientId) && AccessCode.EXISTING_CODES.contains(accessCode)) {
                            return true
                        }
                    }
                    if (voucherCookie && Voucher.countByCodeAndStatus(voucherCookie.value, Voucher.VoucherStatus.NOT_USED)) {
                        return true
                    } else if (!(controllerName == "assets") &&
                            !(controllerName == "info" && actionName == "putPatientInfo") &&
                            !(controllerName == "info" && actionName == "putVoucherCode") &&
                            !(controllerName == "info" && actionName == "splash")) {
                        redirect(controller: "info", action: "splash")
                        return false
                    }
                    return true
                }
                return true
            }
        }

    }
}