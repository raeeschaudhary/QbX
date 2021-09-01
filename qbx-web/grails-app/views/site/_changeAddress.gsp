<div>
  <alert ng-repeat="alert in alerts" type="alert.type" close="$remove()">{{alert.msg}}</alert>

  <form method="post" class="form-horizontal" name="changeAddress" data-ng-submit="setAddress(item)">
    <input type="hidden" data-ng-model="item.id">

    <div class="modal-body overflow-visible align-center">
      <div class="row">
        <div class="col-md-12">

          <g:render template="/common/modal/field"
                    model='[name: "address1", model: "${type}.address1", error: "address1", code: "address.address1", isRequired: true]'/>

          <g:render template="/common/modal/field"
                    model='[name: "address2", model: "${type}.address2", error: "address2", code: "address.address2"]'/>

          <g:render template="/common/modal/field"
                    model='[name: "city", model: "${type}.city", error: "city", code: "address.city", isRequired: true]'/>

          <g:render template="/common/modal/field"
                    model='[name: "state", model: "${type}.state", error: "state", code: "address.state"]'/>

          <g:render template="/common/modal/field"
                    model='[name: "zip", model: "${type}.zipCode", error: "zipCode", code: "address.zip"]'/>

          <g:render template="/common/modal/countrySelect"
                    model='[name: "countryCode", model: "${type}.countryCode", isRequired: true,
                        error: "countryCode", code: "address.countryCode"]'/>

        </div>
      </div>
    </div>

    <div class="modal-footer">
      <button type="button" class="btn btn-default" ng-click="cancel()"><g:message code="common.cancel"/></button>
      <button class="btn btn-primary btn-danger" type="submit" data-ng-disabled="changeAddress.$invalid">
        <g:message code="site.address.submit"/>
      </button>
    </div>

  </form>
</div>