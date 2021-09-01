<div class="btn_block">
  <span class="tooltip-wrapper" tooltip="{{voucher.tooltip}}">
    <button data-ng-disabled="!selectedSite.id" data-ng-click="createVoucher(1)" class="btn type1">
      <g:message code="voucher.create" args="[1]"/>
    </button>
  </span>

  <span class="tooltip-wrapper" tooltip="{{voucher.tooltip}}">
    <button data-ng-disabled="!selectedSite.id" data-ng-click="createVoucher(5)"
            class="btn type2"><g:message code="voucher.create" args="[5]"/></button>
  </span>

  <div style="width: 200px; float: left; margin-right: 15px;">
    <g:select ui-select2="select2Options" class="select_type2" name="site" data-ng-model="selectedSite.id"
              from="${sites}" noSelection="['': '-Select site-']" optionKey="id" optionValue="name"/>
  </div>

  <a href="" onclick="window.print();" class="btn print" title="${g.message(code: 'operation.print')}"></a>
</div>