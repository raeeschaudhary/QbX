<h3><g:message code="test.params.title"/>:</h3>

<g:each in="${testResult.testParams}" var="param">
  <div>${param.key + " : " + param.value}</div>
</g:each>

<h3><g:message code="test.qparams.title"/>:</h3>
<g:each in="${testResult.qualityPrams}" var="param">
  <div>${param.key + " : " + param.value}</div>
</g:each>