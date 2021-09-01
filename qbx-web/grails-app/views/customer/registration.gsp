<html>
<head>
  <meta name="layout" content="customer"/>
  <title><g:message code="customer.registration.title"/></title>
</head>

<body>
<div class="form_box login_form">
        <g:if test="${isNormal}">
        <g:if test='${flash.error}'>
    <div class="alert-box">
      <button class="close" data-close="alert"></button>
      <span class="error_text">${flash.error}</span>
    </div>
        </g:if>

  <g:hasErrors bean="${testSubject}" field="dateOfBirth">
    <div class="alert-box"><g:message code="customer.registration.dateOfBirth.wrong"/></div>
  </g:hasErrors>

  <g:form role="form" controller="customer" action="welcomeQbX">
    <g:hiddenField name="voucher" value="${voucher}"/>

    <h1><g:message code="customer.registration.title"/><i class="info_icon" title="${g.message(code:'data.privacy.policy')}"></i></h1>

    <div class="column_layout">

      <div class="column1">
        <div class="field">
          <div class="lbl"><g:message code="customer.registration.gender"/>:</div>
        </div>
      </div>

      <div class="column2">
        <div class="field">
          <span class="inp_field">
            <input type="radio" name="gender" id="male" checked="" value="male">
            <label for="male"><g:message code="customer.registration.gender.male"/></label>
          </span>
          <span class="inp_field">
            <input type="radio" name="gender" id="female" value="female">
            <label for="female"><g:message code="customer.registration.gender.female"/></label>
          </span>
        </div>
      </div>

    </div>

    <div class="column_layout">

      <div class="column1">
        <div class="field">
          <div class="lbl"><g:message code="customer.registration.dateOfBirth.label"/>:</div>
        </div>
      </div>

      <div class="column2">
        <div class="field">
          <div class="error_wrap">
            <input class="inp date_inp birthdate_inp hasDatepicker ${hasErrors(bean: testSubject, field: 'dateOfBirth', 'error')}"
                value="${params.dateOfBirth}" required="" type="text" name="dateOfBirth" />
            <g:hasErrors bean="${testSubject}" field="dateOfBirth">
              <i class="close_icon"></i>
            </g:hasErrors>
          </div>
        </div>
      </div>

    </div>

    <div class="column_layout">
<div class="column1">&nbsp;</div>
<div class="column2">
    <div class="field">
      <button type="submit" class="btn action_btn"><g:message code="customer.registration.submit"/></button>
</div>
    </div>

</div>
  </g:form>
</g:if>

<g:else>
  <div class="alert-box">
    <button class="close" data-close="alert"></button>
    <span><g:message code="customer.test.cantTakeWithMobile"/></span>
  </div>

  <div class="field">
    <g:link controller="info" action="landing" class="btn action_btn">
      <g:message code="customer.registration.goToHome"/>
    </g:link>
  </div>

</g:else>
</div>
</body>
</html>