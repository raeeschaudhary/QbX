<div class="step_line">
  <g:each in="${1..3}" var="i">
    <div class="step step${i}">
      <i class="icon${i < current ? ' complete' : i == current ? ' current' : ''}">${i}</i>
      <g:if test="${i == 1 && current == 2}">
        <a href="#"><i class="icon complete">${i}</i></a>
      </g:if>
      <g:elseif test="${i == 2 && current == 3}">
        <a href="#/performPracticeTest"><i class="icon complete">${i}</i></a>
      </g:elseif>
      <g:message code="test.step.${i}"/>
    </div>
  </g:each>
</div>