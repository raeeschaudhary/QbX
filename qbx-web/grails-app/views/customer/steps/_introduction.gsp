<g:render template="steps/stepLine" model="[current: 1]"/>

<div class="form_box perform_test">
  <div class="perform_box">
    <div class="video_holder">
      <iframe src="//player.vimeo.com/video/91159987"
              width="560" height="315" frameborder="0" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>
    </div>

    <div class="info_block">
      <p class="bigger_text"><g:message code="test.introduction.definition" /></p>
    </div>
    <g:javascript>
        var updateComputerChecksUrl = "${createLink(controller:'customer',action:'updateComputerChecksCounter')}"
    </g:javascript>

    <div class="btn_block">
      <span class="lbl"><g:message code="test.start.ready" /> </span>
      <a href="" data-ng-click="checkComputer()" class="btn action_btn"><g:message code="goto.computer.check"/></a>
    </div>

  </div>

  <div class="text_info_block">

    <h2 class="center_align_h2"><g:message code="test.step.introduction.subtitle" /></h2>
    <p class="center_align_p"><g:message code="test.step.introduction.definition" /></p>
    <g:if test="${child}">
      <g:render template="steps/instructions/childIntro" />
    </g:if>

    <g:else>
      <g:render template ="steps/instructions/adultIntro" />
    </g:else>

  </div>

</div>