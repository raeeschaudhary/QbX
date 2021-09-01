<g:applyLayout name="main">
  <html>
  <head>
    <title><g:layoutTitle/></title>
    <g:layoutHead/>
  </head>

  <body class="inner_page">

  <g:render template="/common/public/header"/>

  <section class="bg_wrapper">
    <g:layoutBody/>
  </section>

  <g:render template="/common/public/footer"/>

  </body>
  </html>
</g:applyLayout>