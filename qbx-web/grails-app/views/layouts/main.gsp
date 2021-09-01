<!DOCTYPE html>
<html class="${pageProperty(name: 'body.data-html')}">
<head>
  <title><g:layoutTitle/></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <asset:stylesheet href="application.css"/>
  <asset:javascript src="applicationHead.js"/>
  <asset:stylesheet href="manifest.css"/>
  <g:layoutHead/>
</head>

<body class="${pageProperty(name: 'body.class')}"
      data-ng-app="${pageProperty(name: 'body.data-ng-app')}"
      data-base-url="${pageProperty(name: 'body.data-base-url', default: createLink(action: 'index').replaceAll(/index$/, ''))}"
      data-fps-threshold="${pageProperty(name: 'body.data-fps-threshold')}"
      data-test-subject="${pageProperty(name: 'body.data-test-subject')}">
<g:layoutBody/>
<asset:javascript src="application.js"/>
</body>
</html>
