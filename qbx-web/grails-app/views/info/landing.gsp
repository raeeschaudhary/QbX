<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="landing"/>
  <title><g:message code="landing.title"/></title>
</head>

<body class="promo_page">

<div class="header">
  <div class="container">
    <div class="wrap_logo">
    <!--<a href="${createLink(uri: '/')}" class="logo" title="${message(code: 'qbx.home.title')}">
    </a>-->
      <a href="${createLink(uri: '/')}" title="${message(code: 'qbx.home.title')}"
         style="top: 30px; margin-right: 17px;display: inline-block; left: auto; position: relative;
      height: 41px; text-decoration: none; color: #ffffff; font-size: 41px; font-weight: bold">
        <span class="promo_qb">Qb</span><span class="promo_check">Check</span>
      </a>
    <div class="slogan">
      <g:message code="info.landing.slogan"/>
    </div>
</div>
    <div class="top_nav">
      <a href="${createLink(controller: 'info', action: 'aboutTest')}" class="about_item">
        <g:message code="info.landing.about"/>
      </a>
      <a href="${createLink(controller: 'info', action: 'moreOnAdhd')}" class="more_item">
        <g:message code="info.landing.more"/>
      </a>
      <a href="${createLink(controller: 'info', action: 'faq')}" class="faq_item">
        <g:message code="info.landing.faq"/>
      </a>
      <!--<a href="${createLink(controller: 'info', action: 'pricing')}" class="pricing_item">
        <g:message code="info.landing.pricing"/>
      </a>-->
      <a href="#" class="pricing_item">
        <g:message code="info.landing.pricing"/>
      </a>
      <a href="${createLink(controller: 'info', action: 'dataPrivacy')}" class="privacy_item">
        <g:message code="info.landing.data.privacy"/>
      </a>
      <a href="${createLink(controller: 'login', action: 'auth')}" class="login_item">
        <g:message code="info.landing.login"/>
      </a>
    </div>
  </div>
</div>

<div class="bg_wrapper">

  <div class="video_holder">
    <div class="video_block">

      <!--<asset:image src="images/computer_first_page.jpg" width="617" height="400"/>-->
      <div class="backImage"></div>

      <!--<a href="">
        <object>
          <param name="movie" value="//www.youtube.com/v/RMaCE5RT54c?version=3&amp;rel=0"></param>
          <param name="allowFullScreen" value="true"></param>
          <param name="allowscriptaccess" value="always"></param>
          <embed src="//www.youtube.com/v/RMaCE5RT54c?hl=ru_RU&amp;version=3&amp;rel=0" class="landing__video"
                 type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true"></embed>
        </object>
      </a>-->
    </div>
  </div>
</div>

<div class="footer">
  <div class="container">
    <div class="btn_block">
      <g:if test="${flash.infoMessage}">
        <div class="attention_tooltip">
          ${flash.infoMessage}
          <i class="close_icon"></i>
        </div>
      </g:if>
      <g:else>
        <span class="wrap_btn">
          <span class="lbl">For consumers:</span>
          <g:link controller="customer" action="beforeTest" class="btn action_btn" style="width: 140px">
            <g:message code="landing.test"/>
          </g:link>
        </span>
        <span class="wrap_btn">
          <span class="lbl">Professional users only:</span>
        <g:link controller="site" action="signUp" class="btn action_btn" style="width:140px"><g:message code="landing.signup"/></g:link>
      </g:else>
        </span>


    </div>
  </div>
</div>



</body>

</html>
