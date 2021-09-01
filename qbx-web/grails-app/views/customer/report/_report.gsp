<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="customer"/>
  <style>
  @page {
    size: 6in 4in;  /* width height */
    margin: 0.25in;
  }
  </style>
</head>

<body>
<div class="container report_page">
  <h1><g:message code="customer.report.number" args="[testResult.id]"/></h1>

  <g:render template="/customer/report/testResults"/>

  <div class="reports_block" style="margin-top: 20px;">
    <g:render template="/customer/report/reportItemPdf"/>
    <g:render template="/customer/report/reportItemPdf"/>
    <g:render template="/customer/report/reportItemPdf"/>
  </div>

  <div class="report_info">
    <p><g:message code="customer.report.link"
                  args="[g.createLink(uri: '/report/' + testResult.publicId, absolute: true)]"/></p>
  </div>
</div>
</body>
</html>