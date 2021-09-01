<g:render template="/common/member/list/header" model="[entity: g.message(code: 'voucher.label')]">
  <g:render template="topButtons"/>
</g:render>

<div class="table vouchers_table">
  <div class="tbl_overlay" data-ng-show="table.editMode"></div>
  <g:render template="/common/list/deleteDialog"/>
  <table infinite-scroll="loadMoreRows()" infinite-scroll-immediate-check="false">
    <thead>
    <tr>
      <g:render template="/common/list/th" model="[code: 'voucher.id.label']"/>
      <g:render template="/common/list/th" model="[code: 'voucher.createdBy.label']"/>
      <g:render template="/common/list/th" model="[code: 'voucher.code.label']"/>
      <g:render template="/common/list/th" model="[code: 'voucher.dateCreated.label']"/>
      <g:render template="/common/list/th" model="[code: 'voucher.site.label']"/>
      <g:render template="/common/list/th" model="[code: 'voucher.status.label']"/>
      <g:render template="/common/list/th" model="[code: 'voucher.dateUsed.label', col: 3]"/>
    </tr>
    </thead>
    <tbody>

    <tr data-ng-repeat="item in list | orderBy:'id':true" data-ng-class="{'editable_row': item.editMode}"
        delete-voucher-dialog="item">
      <g:render template="/common/list/td" model="[className: 'id']">{{item.id}}</g:render>
      <g:render template="/common/list/td" model="[className: 'plainText']">{{item.createdBy}}</g:render>
      <g:render template="/common/list/td" model="[className: 'plainText']">{{item.code}}</g:render>
      <g:render template="/common/list/td" model="[className: 'plainText']">{{item.dateCreated | date:'<g:message
          code="default.date.format"/>'}}</g:render>

      <g:render template="/ng-templates/editableSelectCell" model="[propertyName: 'site.name',
          selectPropertyName: 'site.id', valuesList: sites, optionKey: 'id', optionValue: 'name']"/>

      <g:render template="/common/list/td" model="[className: 'plainText']">{{item.status.name}}</g:render>
      <g:render template="/common/list/td" model="[className: 'plainText']">{{item.dateUsed}}</g:render>

      <g:render template="/ng-templates/voucherRowActions"/>
    </tr>

    </tbody>
  </table>

  <div class="preloader" data-ng-show="busy"></div>
</div>