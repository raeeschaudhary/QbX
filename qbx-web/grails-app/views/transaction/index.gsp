<%@ page import="se.qbtech.qbx.domain.model.qtest.Transaction" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="ng-app">
  <g:set var="entityName" value="${message(code: 'transaction.label')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body data-ng-app="scaffolding" data-base-url="${createLink(uri: '/transaction/')}">
<div data-ng-view></div>
</body>
</html>
