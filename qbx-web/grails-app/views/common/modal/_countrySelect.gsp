<div class="form-group" data-ng-class="{error: errors.${error}}">
  <g:if test="${isRequired}"><span class='required'>*</span></g:if>
  <label for="${name}" class="col-md-3 control-label no-padding-right">
    <g:message code="${code}"/>
  </label>

  <div class="col-md-8">
    <g:countrySelect name="${name}" id="${name}" data-ng-model="item.${model}" class="form-control"
                     noSelection="['': '-Choose your country-']" required=""/>
    <span class="help-inline" data-ng-show="errors.${error}">{{errors.${error}}</span>
  </div>
</div>