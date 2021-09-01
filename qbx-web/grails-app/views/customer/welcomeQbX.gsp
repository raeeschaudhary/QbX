<!doctype html>
<html>
<head>
  <meta name="layout" content="test">

  <title><g:message code="welcome.page.title" /></title>
</head>

<body class="inner_page">

<g:if test="${child}">
  <div class="form_box login_form age_18">
    <h1><g:message code="age.not.adult.heading" /></h1>
    <div class="text">
      <p><g:message code="age.not.adult" /></p>
    </div>

    <g:form controller="customer" action="welcomeQbX">
      <input type="hidden" name="testSubjectId" value="${testSubjectId}" />
      <input type="hidden" name="child" value="${child}" />
      <input type="hidden" name="permission" value="true" />
      <div class="field">
        <button type="submit" class="btn action_btn">
          <g:message code="age.permission.guardian"/>
        </button>
      </div>
    </g:form>
  </div>
</g:if>


<g:else>
  <div class="container content_page">
    <section class="container">
      <h1 class="panel-heading"><g:message code="welcome.page.title" /></h1>
    </section>
    <div class="info_txt">
      <p><g:message code="welcome.page.p1" /></p>
      <p><g:message code="welcome.page.p2" /></p>
      <p><g:message code="welcome.page.p3" /></p>
      <p><g:message code="welcome.page.p4" /></p>
    </div>

    <g:form controller="customer" action="questionnaire" method="post">
      <input type="hidden" name="testSubjectId" value="${testSubjectId}" />

      <div class="field btn_block_p10">
        <button type="submit" class="btn action_btn"><g:message code="welcome.page.link" /></button>
      </div>
    </g:form>
  </div>
</g:else>
</body>
</html>