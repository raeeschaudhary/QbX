<td>
  <div class="innerWrapper">
    <span data-ng-hide="item.editMode">{{item.${propertyName}}}</span>
    <g:select ui-select2="" name="${selectPropertyName}" from="${valuesList}" optionKey="${optionKey}" optionValue="${optionValue}"
              data-ng-show="item.editMode" data-ng-model="item.${selectPropertyName}" class="select"
              data-ng-init="item.${selectPropertyName} = item.${selectPropertyName} || ''"
              noSelection="${noSelection ? ['': noSelection] : null}"
              data-tooltip="{{errorMessages[item.id]['${errorMessageKey ?: propertyName}']}}"
              data-ng-class="{'error-field' : errorMessages[item.id]['${errorMessageKey ?: propertyName}']}"/>
  </div>
</td>