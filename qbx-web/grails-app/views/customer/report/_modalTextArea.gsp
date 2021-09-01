<div class="form-group" data-ng-class="{error: ${ngModel}.errors['${fieldName}']}">
  <label for="${fieldName}" class="col-md-3 control-label no-padding-right">
    <g:message code="${code}"/>
    <g:if test="${isRequired}"><span class='required'>*</span></g:if>
  </label>

  <div class="col-md-8">
    <textarea class="form-control" name="${fieldName}" id="${fieldName}"
              data-ng-model="${ngModel}.${fieldName}" rows="10"
              data-ng-class="{error: ${ngModel}.errors['${fieldName}']}"
              <g:if test="${isRequired}">required=""</g:if>>
    </textarea>
  </div>
</div>