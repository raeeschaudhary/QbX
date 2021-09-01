<html>
<head>
  <meta name="layout" content="customer"/>
  <title><g:message code="customer.payment.title"/></title>
</head>

<body>

<div class="attention_tooltip payment_attention_tooltip">
  <b><g:message code="common.wellDone"/></b>
  <g:message code="customer.payment.attention"/>
</div>
<g:if test="${flash.error}">
  <div class="alert-error attention_tooltip payment_attention_tooltip">
    ${flash.error}
  </div>
</g:if>

<div class="form_box billing_form login_form test_form">
  <g:form controller="customer" action="processPayment" name="paymentForm" method="post">
    <g:hiddenField name="transactionId" value="${transactionId}"/>
    <h1><g:message code="customer.payment.topLabel"/></h1>

    <div class="column_layout">
      <div class="column1">
        <div class="field">
          <div class="lbl"><g:message code="customer.payment.card.number"/>:</div>
        </div>
      </div>

      <div class="column2">
        <div class="field">
          <div class="error_wrap">
            <input type="text" id="cardNumber" data-encrypted-name="cardNumber" autocomplete="off"
                   class="inp ${hasErrors(bean: billingCmd, field: 'cardNumber', 'error')}" required=""/>
          </div>
        </div>
      </div>
    </div>

    <div class="column_layout">
      <div class="column1">
        <div class="field">
          <div class="lbl"><g:message code="customer.payment.card.expirationDate"/>:</div>
        </div>
      </div>

      <div class="column2">
        <div class="field">
          <g:select name="cardExpireMonth" data-encrypted-name="cardExpireMonth"
                    valueMessagePrefix="customer.payment.card.expirationMonth"
                    class="form_select select2-offscreen ${hasErrors(bean: billingCmd, field: 'cardExpireMonth', 'error')}"
                    required=""
                    from="${se.qbtech.qbx.common.AppConstants.CREDIT_CARD_MONTHS}"
                    noSelection="['': 'Month']"/>
          <g:select name="cardExpireYear" data-encrypted-name="cardExpireYear"
                    class="form_select select2-offscreen ${hasErrors(bean: billingCmd, field: 'cardExpireYear', 'error')}"
                    required=""
                    from="${se.qbtech.qbx.common.AppConstants.CREDIT_CARD_YEARS}"
                    noSelection="['': 'Year']"/>
          <div class="cvv_field">
            <div class="lbl"><g:message code="customer.payment.card.cvv"/></div>

            <div class="error_wrap">
              <input type="text" id="cardSecurityCode" data-encrypted-name="cardSecurityCode" autocomplete="off"
                     class="inp cvv_inp ${hasErrors(bean: billingCmd, field: 'cardSecurityCode', 'error')}"
                     required=""/>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="column_layout">
      <div class="column1">
        <div class="field">
          <div class="lbl"><g:message code="customer.payment.card.holderName"/>:</div>
        </div>
      </div>

      <div class="column2">
        <div class="field">
          <div class="error_wrap">
            <g:textField name="cardHolderName" value="${billingCmd.cardHolderName}"
                         class="inp ${hasErrors(bean: billingCmd, field: 'cardHolderName', 'error')}"/>
          </div>
        </div>
      </div>
    </div>
    <div class="column_layout">
      <div class="column1">&nbsp;</div>

      <div class="column2">
        <div class="field">
          <div class="protect_note"><g:message code="professionalUser.billingInfo.protectNote" />
            <a href="https://www.braintreepayments.com/about/" target="_blank" class="link"><g:message code="professionalUser.billingInfo.protectNote.link" /></a>
          </div>
        </div>
      </div>
    </div>
    <div class="column_layout">

      <div class="column1">&nbsp;</div>

      <div class="column2">
        <div class="field">
          <g:submitButton name="submitPayment" value="${message(code: 'customer.payment.continue')}"
                          class="btn action_btn"></g:submitButton>
        </div>
      </div>
    </div>

    <div class="column_layout">
      <div class="column1">&nbsp;</div>

      <div class="column2">
        <div class="field">
          <div class="info_box">
									<p><g:message code="payment.voucher.option.text" /><i class="info_icon" title="${g.message(code:'payment.voucher.info')}"></i></p>

								</div>
        </div>
      </div>
      </div>
    </g:form>
  <div class="column_layout">
    <div class="column1">&nbsp;</div>

    <div class="column2">
  <g:form controller="customer" action="beforeTest"><input type="hidden" name="transactionId" value="${transactionId}" />
    <input type="hidden" name="testSubjectId" value="${testSubjectId}" /><input type="hidden" name="testResultId" value="${testResultId}" />
    <button class="btn action_btn" type="submit"><g:message code="payment.enter.code" /></button>
    </div>
  </div>
</g:form>
</div>

<script src="https://js.braintreegateway.com/v1/braintree.js"></script>
<script>
  var braintree = Braintree.create("${grailsApplication.config.braintree.encryptionKey}");
  braintree.onSubmitEncryptForm("paymentForm");

  $(function () {
    var creditly = Creditly.initialize('', '#cardNumber', '#cardSecurityCode', '');

    $("form[name=paymentForm]").submit(function (e) {
      if (!creditly.validate()) {
        e.preventDefault();
      } else {
        return;
      }
    });
  });
</script>

<script>
  $(function () {
    $(".form_select").select2({
      dropdownCssClass: "form_select_dropdown"
    });
  });
</script>

</body>
</html>