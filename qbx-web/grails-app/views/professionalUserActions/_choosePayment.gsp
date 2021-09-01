<div class="choice_block">
  <div class="ui-buttonset">
    <input type="radio" data-ng-model="siteInfo.payment" name="credit" class="ui-helper-hidden-accessible"
           ng-disabled="siteInfo.payment == '${g.message(code: 'paymentMethod.INVOICE')}'"
           value="${g.message(code: 'paymentMethod.CREDIT_CARD')}" id="credit"/>
    <label for="credit" data-ng-class="{'ui-state-active' : siteInfo.payment == '${g.message(code: 'paymentMethod.CREDIT_CARD')}'}"
           class="ui-button ui-widget ui-state-default ui-button-text-only ui-corner-left">
      <span class="ui-button-text"><g:message code="professionalUser.siteInfo.payment.credit"/></span>
    </label>
    <input type="radio" data-ng-model="siteInfo.payment" name="payment" class="ui-helper-hidden-accessible"
           ng-disabled="siteInfo.payment == '${g.message(code: 'paymentMethod.CREDIT_CARD')}'"
           value="${g.message(code: 'paymentMethod.INVOICE')}" id="invoice"/>
    <label for="invoice" data-ng-class="{'ui-state-active' : siteInfo.payment == '${g.message(code: 'paymentMethod.INVOICE')}'}"
           class="ui-button ui-widget ui-state-default ui-button-text-only ui-corner-right">
      <span class="ui-button-text"><g:message code="professionalUser.siteInfo.payment.invoice"/></span>
    </label>
  </div>
</div>