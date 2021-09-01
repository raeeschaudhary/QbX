<div>
  <alert ng-repeat="alert in alerts" type="alert.type" close="$remove()">{{alert.msg}}</alert>

  <form method="post" class="form-horizontal" name="setEmail">
    <div class="modal-header">
      <h3><g:message code="report.email.header"/></h3>
    </div>

    <div class="modal-body overflow-visible align-center">
      <div class="row">
        <div class="col-md-12">
          <g:render template="/customer/report/modalInput"
                    model="[ngModel: 'mail', fieldName: 'email', isRequired: true, code: 'report.email.label']"/>

          <g:render template="/customer/report/modalInput"
                    model="[ngModel: 'mail', fieldName: 'subject', isRequired: true, code: 'report.email.subject']"/>

          <g:render template="/customer/report/modalTextArea"
                    model="[ngModel: 'mail', fieldName: 'message', code: 'report.email.message']"/>

        </div>
      </div>
    </div>

    <div class="modal-footer">
      <button type="button" class="btn btn-default" ng-click="cancel()"><g:message code="common.cancel"/></button>
      <button class="btn btn-primary btn-danger" data-ng-click="setEmailInstance(mail)"
              data-ng-disabled="setEmail.$invalid">
        <g:message code="report.email.send"/>
      </button>
    </div>

  </form>
</div>