<g:render template="/common/list/td">
  <span data-ng-hide="item.editMode">{{item.${propertyName}}}</span>
  <input type="${type ?: 'text'}" data-ng-show="item.editMode" data-ng-model="item.${propertyName}"
         data-tooltip="{{errorMessages[item.id]['${propertyName}']}}"
         data-ng-class="{'error-field' : errorMessages[item.id]['${propertyName}']}"/>
</g:render>