<html>
<head>
  <meta name="layout" content="splash"/>
  <title><g:message code="info.splash.title"/></title>
</head>

<body>

<g:if test='${flash.error}'>
  <div class="alert alert-danger">
    <button class="close" data-close="alert"></button>
    <span>${flash.error}</span>
  </div>
</g:if>

<div class="column_layout">
  <div class="column1">
    <g:form role="form" controller="info" action="putVoucherCode" data-role="form" method="post">
      <h3><g:message code="info.voucher.title"/></h3>

      <div class="field">
        <div class="error_wrap">
          <g:textField name="voucherCode" placeholder="${message(code: 'info.voucher.placeholder')}" class="inp"
                       value="${params.voucherCode}" required=""/>
        </div>
      </div>

      <div class="field">
        <button class="btn action_btn"><g:message code="info.voucher.submit"/></button>
      </div>
    </g:form>
  </div>

  <div class="column2">
    <g:form role="form" controller="info" action="putPatientInfo" data-role="form" method="post">
      <h3><g:message code="info.accessCode.title"/></h3>

      <div class="field">
        <div class="error_wrap">
          <g:textField name="patientId" placeholder="${message(code: 'info.patientId.placeholder')}" class="inp"
                       value="${params.patientId}" required=""/>
        </div>
      </div>

      <div class="field">
        <div class="error_wrap">
          <g:textField name="accessCode" placeholder="${message(code: 'info.accessCode.placeholder')}" class="inp"
                       value="${params.accessCode}" required=""/>
        </div>
      </div>

      <div class="field">
        <button class="btn action_btn"><g:message code="info.accessCode.submit"/></button>
      </div>
    </g:form>
  </div>
</div>

</body>
</html>