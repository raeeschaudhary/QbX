<g:render template="/common/member/list/header" model="[entity: g.message(code: 'transaction.label')]"/>

<div class="table trans_table">
  <table infinite-scroll="loadMoreRows()" infinite-scroll-immediate-check="false">
    <thead>
    <tr>
      <g:render template="/common/list/th" model="[code: 'transaction.testResult.label']"/>
      <g:render template="/common/list/th" model="[code: 'transaction.dateCreated.label']"/>
      <g:render template="/common/list/th" model="[code: 'transaction.type.label']"/>
      <g:render template="/common/list/th" model="[code: 'transaction.voucher.label']"/>
      <g:render template="/common/list/th" model="[code: 'transaction.status.label']"/>
    </tr>
    </thead>
    <tbody>
    <tr data-ng-repeat="item in list | orderBy:'id':true">
      <g:render template="/common/list/td" model="[className: 'id']">{{item.testResult.id}}</g:render>
      <g:render template="/common/list/td">
        {{item.dateCreated | date:'<g:message code="default.date.format"/>'}}
      </g:render>
      <g:render template="/common/list/td">{{item.type.name}}</g:render>
      <g:render template="/common/list/td">{{item.voucher}}</g:render>
      <g:render template="/common/list/td">{{item.status.name}}</g:render>
    </tr>

    </tbody>
  </table>

  <div class="preloader" data-ng-show="busy"></div>
</div>