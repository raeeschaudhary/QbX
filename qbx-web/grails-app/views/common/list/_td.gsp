<td <g:if test="${className}">class="${className}"</g:if>>
  <div class="innerWrapper"
    <g:if test="${tooltipField && tooltipId}">
      data-tooltip="{{errorMessages['${tooltipId}']['${tooltipField}']}}"
    </g:if>>
    ${body()}
  </div>
</td>