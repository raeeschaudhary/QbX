<g:render template="/common/list/td" model="[className: 'edit']">
  <a data-ng-click="edit(item)" data-ng-hide="item.editMode">
    <i title="${g.message(code: 'operation.edit')}" class="icon"></i>
  </a>

  <a data-ng-click="update(item)" data-ng-show="item.editMode">
    <i title="${g.message(code: 'operation.update')}" class="icon"></i>
  </a>
</g:render>

<g:render template="/common/list/td" model="[className: 'delete']">
  <a data-ng-click="setDeletedItem(item)">
    <i title="${g.message(code: 'operation.delete')}" class="icon"></i>
  </a>
</g:render>