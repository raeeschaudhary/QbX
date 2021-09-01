<g:applyLayout name="main">
  <html>
  <head>
    <title><g:layoutTitle/></title>
    <g:layoutHead/>
  </head>

  <body class="promo_page landing_page">
  <div class="bg_wrapper">
    <div class="header container">
      <a href="${createLink(uri: '/')}" class="logo" title="Qbx"></a>

      <div class="slogan">
        ADHD symptom measurement tool
      </div>

      <a id="simple-menu" class="menu_btn" href="#sidr"></a>

      <div id="sidr" class="mobile_menu">
        <ul>
          <li>
            <a href="Home_page.html">Home</a>
          </li>
          <li>
            <a href="More_on_ADHD.html">More on ADHD</a>
          </li>
          <li>
            <a href="About_the_test.html">About the test</a>
          </li>
          <li>
            <a href="faq.html">FAQ</a>
          </li>
          <li>
            <a href="Pricing.html">Pricing</a>
          </li>
          <li>
            <a href="#">Profile</a>
          </li>
          <li>
            <a href="Test.html">TEst</a>
          </li>
          <li>
            <a href="Report.html">REport</a>
          </li>
        </ul>
      </div>
    </div>
    %{--Header end--}%
    <div class="container">
      <div class="form_box landing_form">
        <g:layoutBody/>
      </div>
    </div>
  </div>
  </body>
  </html>
</g:applyLayout>