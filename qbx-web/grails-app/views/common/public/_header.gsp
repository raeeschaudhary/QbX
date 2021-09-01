<div class="header">
  <div class="container">
   <!-- <g:link uri="/" class="logo" title="${g.message(code : 'qbx.home.title')}"></g:link>-->
    <g:link uri="/" style="top: 2px; left: 0px; display: block; position: absolute;
      height: 31px; text-decoration: none; color: #0073a8; font-size: 31px; font-weight: bold" title="${g.message(code: 'qbx.home.title')}">
      <span class="promo_qb">Qb</span><span class="promo_check">Check</span>
    </g:link>
    <div class="slogan">
      <g:message code="header.slogan"/>
    </div>

    <sec:ifLoggedIn>
      <g:link controller="logout" class="logout" title="${g.message(code : 'operation.logout')}"></g:link>

      <div class="user_block">
        <div class="user_name"><sec:username/></div>
      </div>
    </sec:ifLoggedIn>

    <div class="top_nav">
      <a href="${createLink(controller: 'info', action: 'aboutTest')}"><g:message code="header.about"/></a>
      <a href="${createLink(controller: 'info', action: 'moreOnAdhd')}"><g:message code="header.more"/></a>
      <a href="${createLink(controller: 'info', action: 'faq')}"><g:message code="header.faq"/></a>
      <!--<a href="${createLink(controller: 'info', action: 'pricing')}"><g:message code="header.pricing"/></a>-->
      <a href="${createLink(controller: 'info', action: 'dataPrivacy')}" class=""><g:message code="header.data.privacy"/></a>

      <sec:ifNotLoggedIn>
        <g:link controller="login" action="auth" class="sign_up"><g:message code="header.login"/></g:link>
      </sec:ifNotLoggedIn>
    </div>
  </div>
</div>