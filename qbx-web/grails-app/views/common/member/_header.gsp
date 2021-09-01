<div class="header">
  <div class="">
    <!--<g:link uri="/" class="logo" title="${g.message(code: 'qbx.home.title')}"></g:link>-->
    <g:link uri="/" style="top: 3px; left: 20px; margin-right: 17px;display: inline-block; position: relative;
      height: 31px; text-decoration: none; color: #0073a8; font-size: 31px; font-weight: bold" title="${g.message(code: 'qbx.home.title')}">
      <span class="promo_qb">Qb</span><span class="promo_check">Check</span>
    </g:link>
    <div class="slogan">
      <g:message code="header.member.slogan"/>
    </div>
    <sec:ifNotLoggedIn>
      <div class="user_block">
        <g:link controller="login" action="auth" class="active sign_up">
          <g:message code="header.login"/>
        </g:link>
      </div>
    </sec:ifNotLoggedIn>

    <sec:ifLoggedIn>
      <g:link controller="logout" class="logout" title="${g.message(code: 'operation.logout')}"></g:link>

      <div class="user_block">
        <div class="user_name"><sec:username/></div>
      </div>
    </sec:ifLoggedIn>

    <div class="top_nav">
      <a href="${createLink(controller: 'info', action: 'aboutTest')}" class=""><g:message code="header.about"/></a>
      <a href="${createLink(controller: 'info', action: 'moreOnAdhd')}"><g:message code="header.more"/></a>
      <a href="${createLink(controller: 'info', action: 'faq')}" class=""><g:message code="header.faq"/></a>
      <!--<a href="${createLink(controller: 'info', action: 'pricing')}" class=""><g:message code="header.pricing"/></a>-->
      <a href="${createLink(controller: 'info', action: 'dataPrivacy')}" class=""><g:message code="header.data.privacy"/></a>
    </div>
  </div>
</div>