<!doctype html>
<html>
<head>
  <meta name="layout" content="test">
  <g:set var="entityName" value="${message(code: 'test.label')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body data-test-subject="${testSubjectId}" data-fps-threshold="${fpsThreshold}">
<section class="container">
  <div data-ng-class="{'canvas_hidden' : !showVideoCanvas}">
    <g:render template="canvas"/>
  </div>
</section>
<section class="container" data-ng-view=""></section>

<g:textField name="fpsValue" data-ng-model="cptTestData.fps" class="hidden"/>
<g:textField name="isFaceTracked" data-ng-model="cptTestData.isFaceTracked" class="hidden"/>
<g:textField name="isFaceDetected" data-ng-model="cptTestData.isFaceDetected" class="hidden"/>

<input type="hidden" id="age" value="${age}"/>
<input type="text" class="hidden" data-ng-model="isDataSent"/>

</body>
</html>
