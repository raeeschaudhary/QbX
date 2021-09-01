<div class="form-group" data-ng-class="{error: errors.${error}}">
  <g:if test="${isRequired}"><span class='required'>*</span></g:if>
  <label for="${name}" class="col-md-3 control-label no-padding-right">
    <g:message code="${code}"/>
  </label>

  <div class="col-md-8">
    <input type="text" class="form-control" id="${name}" name="${name}" data-ng-model="item.${model}"
           <g:if test="${isRequired}">required=""</g:if>>

    <span class="help-inline" data-ng-show="errors.${error}">{{errors.${error}}</span>
  </div>
</div>