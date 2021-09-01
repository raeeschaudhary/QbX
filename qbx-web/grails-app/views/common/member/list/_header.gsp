<div><alert ng-repeat="alert in alerts" type="alert.type" close="removeAlert($index)">{{alert.msg}}</alert></div>

<div class="hd">
  <h1><g:message code="default.list.label" args="[entity]"/></h1>

  <input type="text" class="search_inp" id="search" data-ng-model="query" placeholder="Search..."/>
</div>

<div class="hd">
  ${body()}
</div>