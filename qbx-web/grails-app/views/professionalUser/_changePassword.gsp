<div>
  <alert ng-repeat="alert in alerts" type="alert.type" close="$remove()">{{alert.msg}}</alert>

  <form method="post" class="form-horizontal" name="changePassword" data-ng-submit="setPassword(item)">
    <input type="hidden" data-ng-model="item.id">

    <div class="modal-body overflow-visible align-center">
      <div class="row">
        <div class="col-xs-12">

          <div class="form-group" data-ng-class="{error: errors.newPassword}">
            <label for="newPassword" class="col-sm-3 control-label no-padding-right">
              <g:message code="professionalUser.newPassword" default="New password"/>
            </label>

            <div class="col-md-8 col-sm-8 col-lg-8">
              <g:passwordField name="newPassword" class="form-control" required="required"
                               data-ng-model="item.newPassword"/>
              <span class="help-inline" data-ng-show="errors.newPassword">{{errors.newPassword}}</span>
            </div>
          </div>

          <div class="form-group" data-ng-class="{error: errors.confirmPassword}">
            <label for="confirmPassword" class="col-sm-3 control-label no-padding-right">
              <g:message code="professionalUser.confirmPassword"/>
            </label>

            <div class="col-md-8 col-sm-8 col-lg-8">
              <g:passwordField name="confirmPassword" class="form-control" required="required"
                               data-ng-model="item.confirmPassword"/>
              <span class="help-inline" data-ng-show="errors.confirmPassword">{{errors.confirmPassword}}</span>
            </div>
          </div>

        </div>
      </div>
    </div>

    <div class="modal-footer">
      <button type="button" class="btn btn-default" ng-click="cancel()"><g:message code="common.cancel"/></button>
      <button class="btn btn-primary btn-danger" type="submit" data-ng-disabled="form.$invalid">
        <g:message code="professionalUser.updatePassword.submit"/>
      </button>
    </div>

  </form>
</div>
