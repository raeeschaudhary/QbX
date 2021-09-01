<html>
<head>
  <meta name="layout" content="form"/>
  <title><g:message code="login.auth.title"/></title>
</head>

<body>
<div class="form_box login_form">
  <g:if test='${flash.message}'>
    <div class="alert alert-danger">
      <i class="fa fa-remove-sign"></i>
      ${flash.message}
    </div>
  </g:if>
  <g:form role="form" url="${postUrl}" method="post">
    <h1><g:message code="login.title"/></h1>

    <div class="column_layout">
      <g:render template="/common/form/label" model="[code: 'login.email']"/>

      <div class="column2">
        <div class="field">
          <div class="error_wrap">
            <input type="text" id="email" class="inp" required="" autofocus="" autocomplete="off" name='j_username'
                   value="${session['SPRING_SECURITY_LAST_USERNAME']?.decodeHTML()}">
          </div>
        </div>
      </div>
    </div>

    <div class="column_layout">
      <g:render template="/common/form/label" model="[code: 'login.password']"/>

      <div class="column2">
        <div class="field">
          <div class="error_wrap">
            <input type="password" id="password" class="inp" name='j_password' required="" autocomplete="off">
            <i class="close_icon" style="display: none;"></i>
          </div>

          <div class="forgot_link">
            <a href=""><g:message code="login.password.forgot"/></a>
          </div>
        </div>
      </div>
    </div>

    <div class="column_layout">
      <div class="column1">&nbsp;</div>

      <div class="column2">
        <div class="field">
          <button type="submit" class="btn action_btn"><g:message code="login.button.label"/></button>
        </div>
      </div>
    </div>
  </g:form>
</div>

</body>
</html>