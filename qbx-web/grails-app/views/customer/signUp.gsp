<html>
<head>
  <meta name="layout" content="customer"/>
  <title><g:message code="customer.signup.title"/></title>
</head>

<body data-ng-app="siteSignUp">

<div class="form_box login_form" data-ng-controller="siteSignUpCtrl">
  <div class="alert alert-danger" data-ng-show="item.errors" data-ng-cloak="">
    <p data-ng-repeat="error in item.errors">
      {{error}}
    </p>
  </div>

  <g:form role="form" controller="info" action="afterSiteSubmitting" name="afterSiteSubmittingForm" method="post">
    <h1><g:message code="customer.signup.header"/></h1>

    <g:render template="/common/form/row"
              model="[fieldName: 'name', code: 'customer.singup.clinic', ngModel: 'item']"/>
    <g:render template="/common/form/row"
              model="[fieldName: 'email', code: 'customer.singup.email', ngModel: 'item']"/>

    <g:render template="/common/form/address" model="[type: 'address']"/>
    <g:render template="/common/form/address" model="[type: 'billingAddress']"/>

    <div class="column_layout choice_field">
      <g:render template="/common/form/label" model="[code: 'customer.singup.payment']"/>

      <div class="column2">
        <div class="field">
          <span class="inp_field">
            <input type="radio" data-ng-model="item.payment" name="credit"
                   value="${g.message(code: 'paymentMethod.CREDIT_CARD')}" id="credit"/>
            <label for="credit"><g:message code="customer.singup.credit"/></label>
          </span>
          <span class="inp_field">
            <input type="radio" data-ng-model="item.payment" name="payment"
                   value="${g.message(code: 'paymentMethod.INVOICE')}" id="invoice"/>
            <label for="invoice"><g:message code="customer.singup.invoice"/></label>
          </span>
        </div>
      </div>
    </div>

    <div class="column_layout">
      <div class="column1">&nbsp;</div>

      <div class="column2">
        <div class="field">
          <button type="button" class="btn action_btn" data-ng-click="saveSite()">
            <g:message code="customer.singup.submit"/>
          </button>
        </div>
      </div>
    </div>
  </g:form>
</div>

</body>
</html>