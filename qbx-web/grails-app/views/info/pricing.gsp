<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="customer"/>
  <title><g:message code="info.pricing.title"/></title>
</head>

<body>
<section class="container">
  <div class="form_box price_block">
    <h1><g:message code="info.pricing.header"/></h1>

    <div class="hd">
      <g:message code="info.pricing.cost"/>
    </div>

    <div class="text">
      <p><g:message code="info.pricing.cards"/></p>
      <ul class="price_list">
        <li>
          <asset:image src="images/price/price_pic1.gif" width="64" height="40" alt=""/>
        </li>
        <li>
          <asset:image src="images/price/price_pic2.gif" width="65" height="40" alt=""/>
        </li>
        <li>
          <asset:image src="images/price/price_pic3.gif" width="64" height="40" alt=""/>
        </li>
      </ul>

      <div class="link_field">
        <a href="" class="link"><g:message code="info.pricing.read"/></a>
      </div>

        <g:link class="btn action_btn" controller="customer" action="beforeTest"><g:message code="start.test.now" /></g:link>
    </div>
  </div>
</section>
</body>
</html>