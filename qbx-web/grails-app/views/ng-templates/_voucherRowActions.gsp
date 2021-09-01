<g:render template="/common/list/tdCustomTooltip"
          model="[className: 'edit', tooltip: '{USED:voucher.usedStatusTooltip}[item.status.name]']">
  <a data-ng-click="update(item)" data-ng-show="item.editMode">
    <i class="icon" title="${g.message(code: 'operation.update')}"></i>
  </a>

  <a data-ng-click="editVoucher(item)" data-ng-hide="item.editMode">
    <i class="icon" title="${g.message(code: 'operation.edit')}"></i>
  </a>
</g:render>

<g:render template="/common/list/tdCustomTooltip"
          model="[className: 'delete', tooltip: '{USED:voucher.usedStatusTooltip}[item.status.name]']">
  <a data-ng-click="deleteVoucher(item)">
    <i class="icon" title="${g.message(code: 'operation.delete')}"></i>
  </a>
</g:render>