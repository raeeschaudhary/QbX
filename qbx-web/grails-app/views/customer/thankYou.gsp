<%--
  Created by IntelliJ IDEA.
  User: muhammad
  Date: 4/29/14
  Time: 3:22 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="test" />
  <title><g:message code="thank.you.page.tile" /></title>
</head>

<body class="inner_page">

<div class="container content_page">
  <sction class="container">
    <h1 class="center_align_h"><g:message code="thank.you.page.done" /></h1>
    <br />
    <p class="bigger_text"><g:message code="thank.you.questionnaire.message" />
    </p><br />
    <p class="bigger_text"><g:message code="thank.you.contact" /> <a class="link" href="mailto:survey@qbtech.com">survey@qbtech.com</a></p>
    <br />
    <!--<p class="center_align_p"><g:message code="thank.you.page.message" /></p>-->

    <p class="center_align_h"><g:message code="thank.you.get.started" /> <a href="${g.message(code:'questionnaire.link')}" target="_blank" class="link"><g:message code="thank.you.click.here" /></a></p>

    <h2 class="center_align_h2"><g:message code="thank.you.page.thank.you" /></h2>
    <!--<g:form action="finishTest" controller="customer">
    <input type="hidden" value="${transactionId}" name="transactionId" />
    <input type="hidden" value="${testResultId}" name="testResultId" />
        <input type="hidden" value="${testSubjectId}" name="testSubjectId" />
    <div class="field btn_block_p10">
      <button type="submit" class="btn action_btn"><g:message code="thank.you.page.next.step" /></button>
    </div>
    </g:form>-->
  </sction>
</div>
</body>
</html>