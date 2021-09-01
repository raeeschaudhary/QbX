<header class="landing__header" role="navigation">
  <div class="navbar-header">

    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
      <span class="sr-only"><g:message code="header.toggle"/></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>

    <g:link uri="/" class="navbar-brand landing__logo"><g:message code="brand.logo"/></g:link>
  </div>

  <div class="collapse navbar-collapse">
    <ul class="nav navbar-nav">
      <li class="navbar__subtitle"><a class="navbar-item"><g:message code="landing.slog"/></a></li>
    </ul>

    <ul class="nav navbar-nav navbar-right">
      <li>
        <a href="#" class="navbar-item">
          <i class="glyphicon glyphicon-plus-sign navbar__icon"></i>
          <g:message code="header.more"/>
        </a>
      </li>
      <li>
        <a href="#" class="navbar-item">
          <i class="glyphicon glyphicon-info-sign navbar__icon"></i>
          <g:message code="header.about"/>
        </a>
      </li>
      <li>
        <a href="#" class="navbar-item">
          <i class="glyphicon glyphicon-question-sign navbar__icon"></i>
          <g:message code="header.faq"/>
        </a>
      </li>
      <li>
        <a href="#" class="navbar-item">
          <i class="glyphicon glyphicon-tag navbar__icon"></i>
          <g:message code="header.pricing"/>
        </a>
      </li>

      <li>
        <sec:ifNotLoggedIn>
          <g:link controller="login" action="auth" class="navbar-item">
            <i class="glyphicon glyphicon-lock navbar__icon"></i>
            <g:message code="header.login"/>
          </g:link>
        </sec:ifNotLoggedIn>

        <sec:ifLoggedIn>
          <g:link controller="logout" class="navbar-item">
            <i class="glyphicon glyphicon-off navbar__icon"></i>
            <g:message code="header.logout"/>
          </g:link>
        </sec:ifLoggedIn>
      </li>
    </ul>
  </div>
</header>