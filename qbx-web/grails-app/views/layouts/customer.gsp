<g:applyLayout name="main">
  <html>
  <head>
    <title><g:layoutTitle/></title>
    <g:layoutHead/>
  </head>

  <body class="inner_page" data-ng-app="${pageProperty(name: 'body.data-ng-app')}">

  <g:render template="/common/public/header"/>

  <section class="bg_wrapper">
    <section class="container">
      <g:layoutBody/>
    </section>
  </section>

  <g:render template="/common/public/footer"/>

  </body>
  </html>
</g:applyLayout>