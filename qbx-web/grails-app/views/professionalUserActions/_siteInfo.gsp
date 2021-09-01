<div id="tabs-2" class="tab_content">
  <div class="users_tab_content">
    <div class="site_info_form login_form">
      <div class="alert alert-danger" data-ng-show="siteInfo.errors" data-ng-cloak="">
        <p data-ng-repeat="error in siteInfo.errors">
          {{error}}
        </p>
      </div>

      <form action="">
        <h1>
          <g:message code="professionalUser.siteIno.title"/>
          <span>(Id: {{siteInfo.id}})</span>
        </h1>

        <div class="column_layout">
          <div class="column1 l_side">

            <g:render template="/common/form/row"
                      model="[fieldName: 'name', code: 'professionalUser.siteInfo.name', ngModel: 'siteInfo']"/>

            <g:render template="/common/form/rowCountry"
                      model="[fieldName: 'address.countryCode', code: 'professionalUser.siteInfo.countryCode', ngModel: 'siteInfo']"/>

            <g:render template="/common/form/row"
                      model="[fieldName: 'email', code: 'professionalUser.siteInfo.email', ngModel: 'siteInfo']"/>

            <g:render template="/common/form/row"
                      model="[fieldName: 'phone', code: 'professionalUser.siteInfo.phone', ngModel: 'siteInfo']"/>


            <g:render template="/common/form/row"
                      model="[fieldName: 'address.address1', code: 'professionalUser.siteInfo.address.address1', ngModel: 'siteInfo']"/>

            <g:render template="/common/form/row"
                      model="[fieldName: 'address.address2', code: 'professionalUser.siteInfo.address.address2', ngModel: 'siteInfo']"/>

            <g:render template="/common/form/row"
                      model="[fieldName: 'address.zipCode', code: 'professionalUser.siteInfo.address.zip', ngModel: 'siteInfo']"/>

            <g:render template="/common/form/row"
                      model="[fieldName: 'address.city', code: 'professionalUser.siteInfo.address.city', ngModel: 'siteInfo']"/>

            <g:render template="/common/form/row"
                      model="[fieldName: 'address.state', code: 'professionalUser.siteInfo.address.state', ngModel: 'siteInfo']"/>

            <div class="column_layout">
              <div class="column1">&nbsp;</div>

              <div class="column2">
                <div class="field">
                  <a href="" data-ng-click="save(siteInfo)" class="btn action_btn">
                    <g:message code="professionalUser.siteInfo.submit"/>
                  </a>
                </div>
              </div>
            </div>
          </div>

          <div class="column2 r_side">

            <g:render template="/professionalUserActions/choosePayment"/>

            <div data-ng-show="siteInfo.payment == '${g.message(code: 'paymentMethod.INVOICE')}'" data-ng-cloak="">

              <h3><g:message code="professionalUser.siteInfo.billingAddress.title"/></h3>
              <g:render template="/common/form/row"
                        model="[fieldName: 'address1', code: 'professionalUser.siteInfo.billingAddress.address1', ngModel: 'siteInfo.billingAddress']"/>

              <g:render template="/common/form/row"
                        model="[fieldName: 'address2', code: 'professionalUser.siteInfo.billingAddress.address2', ngModel: 'siteInfo.billingAddress']"/>

              <g:render template="/common/form/row"
                        model="[fieldName: 'zipCode', code: 'professionalUser.siteInfo.address.zip', ngModel: 'siteInfo.billingAddress']"/>

              <g:render template="/common/form/row"
                        model="[fieldName: 'state', code: 'professionalUser.siteInfo.address.state', ngModel: 'siteInfo.billingAddress']"/>

              <g:render template="/common/form/row"
                        model="[fieldName: 'city', code: 'professionalUser.siteInfo.address.city', ngModel: 'siteInfo.billingAddress']"/>

              <g:render template="/common/form/rowCountry"
                        model="[fieldName: 'countryCode', code: 'professionalUser.siteInfo.countryCode', ngModel: 'siteInfo.billingAddress']"/>
            </div>

            <div data-ng-show="siteInfo.payment == '${g.message(code: 'paymentMethod.CREDIT_CARD')}'">
              <div class="credit_card_block" data-ng-show="siteInfo.last4">
                <h3><g:message code="professionalUser.siteInfo.credit.ok"/></h3>

                <div class="field">
                  <span><g:message code="professionalUser.siteInfo.credit.number"/>:</span>
                </div>

                <div class="field">
                  <span class="credit_card_number">**** **** **** {{siteInfo.last4}}</span>
                </div>

                <div class="field">
                </div>
              </div>
              <div data-ng-show="!siteInfo.last4"><g:message code="professionalUser.siteInfo.credit.noCard"/></div>

              <div class="link_field">
                <a data-ng-click="registerNewCard(siteInfo)" class="link"><g:message code="professionalUser.siteInfo.credit.register"/></a>
              </div>
            </div>
          </div>

        </div>
      </form>
    </div>
  </div>
</div>