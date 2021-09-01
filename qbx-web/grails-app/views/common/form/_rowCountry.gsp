<div class="column_layout">
  <g:render template="/common/form/label" model="[code: code]"/>

  <div class="column2">
    <div class="field">
      <div class="error_wrap">
        <g:countrySelect class="form_select" ui-select2="select2Options" name="${fieldName}" id="${name}"
                         data-ng-model="${ngModel}.${fieldName}" data-ng-class="{error: ${ngModel}.errors.${fieldName}}"
                         data-ng-init="${"${ngModel}.${fieldName}"}"
                         noSelection="['': '-Choose your country-']" required=""/>
        <i data-ng-show="${ngModel}.errors['${fieldName}']" class="close_icon" data-ng-cloak=""></i>
      </div>
    </div>
  </div>
</div>