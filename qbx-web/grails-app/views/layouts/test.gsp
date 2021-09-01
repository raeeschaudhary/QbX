<g:applyLayout name="main">
  <html>
  <head>
    <title><g:layoutTitle/></title>
    %{--<asset:stylesheet href="manifest.css"/>--}%
    <g:layoutHead/>
  </head>

  <body class="inner_page test_page" data-ng-app="test" data-base-url="${createLink(uri: '/customer/')}"
        data-test-subject="${pageProperty(name: 'body.data-test-subject')}"
        data-fps-threshold="${pageProperty(name: 'body.data-fps-threshold')}">

  <g:render template="/common/public/header"/>

  <section class="bg_wrapper" data-ng-controller="TestCtrl">
      <g:layoutBody/>
  </section>

  <g:render template="/common/public/footer"/>
  </body>
  </html>
</g:applyLayout>