<div class="column_layout">
  <g:render template="/common/form/label" model="[code: 'site.' + type + '.label']"/>
  <div class="column2">
    <div class="field">
      <a data-ng-click="editAddress('${type}')" class="sign-up__link">
        <span data-ng-show="item.${type}.address1" data-ng-cloak="">
          {{item.${type}.address1}}, {{item.${type}.city}}
        </span>
        <span data-ng-show="!item.${type}.address1"><g:message code="site.${type}.change"/></span>
      </a>
    </div>
  </div>
</div>