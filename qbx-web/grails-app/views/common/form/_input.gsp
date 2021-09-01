<div class="column2">
  <div class="field">
    <div class="error_wrap">
      <g:textField class="inp" required="" data-ng-model="${ngModel}.${fieldName}" name="${fieldName}"
                   data-ng-class="{error: ${ngModel}.errors['${fieldName}']}"/>
      <i data-ng-show="${ngModel}.errors['${fieldName}']" class="close_icon" data-ng-cloak=""></i>
    </div>
  </div>
</div>