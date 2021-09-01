<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<head>
  <meta name="layout" content="customer"/>
  <title><g:message code="customer.report.title"/></title>
</head>

<body data-ng-app="reportModule">
<div class="container report_page" data-ng-controller="reportCtrl" data-test-res="${testResult.id}">
  <h1><g:message code="customer.report.number" args="[testResult.id]"/></h1>

  <g:render template="/customer/report/testResults"/>

  <div class="reports_block" style="margin-top: 20px;">
    <g:render template="/customer/report/reportItem"/>
    <g:render template="/customer/report/reportItem"/>
    <g:render template="/customer/report/reportItem"/>
  </div>

  <div class="report_info">
    <p><g:message code="customer.report.link"
                  args="[g.createLink(uri: '/report/' + testResult.publicId, absolute: true)]"/></p>

    <p><a href="" data-ng-click="setEmail()"><g:message code="customer.report.email"/></a></p>

    <p>
      <a href="${g.createLink(uri: '/reportPdf/' + testResult.publicId, absolute: true)}">
        <g:message code="customer.report.pdf"/>
      </a>
    </p>
  </div>
</div>
</body>
</html>