<html>
<head>
  <meta name="layout" content="customer"/>
  <title><g:message code="customer.beforeTest.title"/></title>
</head>


<body class="inner_page">

<g:if test="${!firefox && !chrome}">
  <div class="attention_tooltip" style="width: 817px">
    <g:message code="customer.warning"/>
    <i class="close_icon"></i>
  </div>
</g:if>
<div class="form_box landing_form payment_form">
  <g:if test='${flash.error}'>
    <div class="attention_tooltip">
      <i class="close_icon"></i>
      <span>${flash.error}</span>
    </div>
  </g:if>
  <g:form role="form" controller="customer" action="registration" method="post">
    <div class="column_layout">

      <div class="column1">
        <h3><g:message code="customer.payment.test"/></h3>

        <div class="field coast_field">
            <g:message code="customer.payment.test.description"/>
                <a href="#" class="link"><g:message code="customer.payment.options"/></a></div>

        <div class="field btn_block">

          <div class="field">
            <!--<g:link controller="customer" action="registration" class="btn action_btn"> disable for now-->
            <a href="#" class="btn action_btn">
              <g:message code="customer.payment.test.submit"/></a>
            <!--</g:link>-->
          </div>

        </div>

      </div>

      <div class="column2">
        <h3><g:message code="customer.payment.voucher"/><i class="info_icon" title="${g.message(code:'payment.voucher.info')}"></i></h3>

        <div class="field">
          <div class="error_wrap">
            <g:textField name="voucher" placeholder="${message(code: 'customer.payment.voucher.placeholder')}"
                         class="inp" required="" value="${params.voucher}"/>
            <i class="close_icon" style="display: none"></i>
          </div>
        </div>
        <div><input type="hidden" value="${transactionId}" name="transactionId"/><input type="hidden" value="${testSubjectId}" name="testSubjectId" /><input type="hidden" value="${testResultId}" name="testResultId"/></div>
        <div class="field btn_block">
          <button class="btn action_btn">
            <g:message code="customer.payment.voucher.submit"/>
          </button>
        </div>

      </div>

    </div>
  </g:form>
</div>

</body>
</html>