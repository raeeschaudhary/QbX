<g:applyLayout name="main">
  <html>
  <head>
    <title><g:layoutTitle/></title>
    <g:layoutHead/>
  </head>

  <body class="inner_page member_area">
  <g:render template="/common/member/header"/>

  <section class="bg_wrapper">
    <div class="list__row">
      <g:render template="/common/member/sidebar"/>

      <div class="col-md-10 list__column list__content">
        <g:layoutBody/>
      </div>
    </div>
  </section>
  </body>
  </html>
</g:applyLayout>