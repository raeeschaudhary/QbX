<div>
  <div class="form_box billing_form login_form test_form">
    <alert ng-repeat="alert in alerts" type="alert.type" close="$remove()">{{alert.msg}}</alert>
    <g:textField name="isValidCard" data-ng-model="card.isValidCard" class="hidden" />
    <g:textField name="cardNumberHolder" data-ng-model="item.cardNumber" class="hidden" />
    <g:textField name="cardSecurityCodeHolder" data-ng-model="card.cardSecurityCode" class="hidden" />

    <form data-ng-submit="setCard(item)" name="addNewCard" method="post" class="form-horizontal">
      <input type="hidden" data-ng-model="item.id">

      <div class="column_layout">
        <div class="column1">
          <div class="field">
            <div class="lbl"><g:message code="customer.payment.card.number"/>:</div>
          </div>
        </div>

        <div class="column2">
          <div class="field">
            <div class="error_wrap">
              <g:textField name="cardNumber" autocomplete="off" class="inp" required=""/>
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
                      data-ng-model="item.cardExpireMonth"
                      class="form_select"
                      required=""
                      from="${se.qbtech.qbx.common.AppConstants.CREDIT_CARD_MONTHS}"
                      noSelection="['': 'Month']"/>
            <g:select name="cardExpireYear" data-encrypted-name="cardExpireYear"
                      data-ng-model="item.cardExpireYear"
                      class="form_select "
                      required=""
                      from="${se.qbtech.qbx.common.AppConstants.CREDIT_CARD_YEARS}"
                      noSelection="['': 'Year']"/>
            <div class="cvv_field">
              <div class="lbl"><g:message code="customer.payment.card.cvv"/></div>

              <div class="error_wrap">
                <g:textField name="cardSecurityCode" autocomplete="off" class="inp cvv_inp" required=""/>
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
              <g:textField name="cardHolderName" data-ng-model="item.cardHolderName"
                           class="inp" autocomplete="off" required=""/>
            </div>
          </div>
        </div>
      </div>


      <div class="modal-footer">
        <button type="button" class="btn btn-default" ng-click="cancel()"><g:message
            code="common.cancel"/></button>
        <button class="btn btn-primary btn-danger" type="submit" data-ng-disabled="changeAddress.$invalid">
          <g:message code="customer.payment.card.use"/>
        </button>
      </div>

    </form>
  </div>

</div>
<script>
  $(function () {
    var creditly = Creditly.initialize('', '#cardNumber', '#cardSecurityCode', '');

    $("form[name=addNewCard]").submit(function (e) {
      if (!creditly.validate()) {
        e.preventDefault();
      } else {
        setValue('cardNumberHolder', $('#cardNumber').val());
        setValue('cardSecurityCodeHolder', $('#cardSecurityCode').val());
        setValue('isValidCard', true);
      }
    });

    function setValue(elemId, val){
      var element = document.getElementById(elemId);
        element.value = val;
        jQuery(element).trigger('input');
    }
  });
</script>

