<g:render template="steps/stepLine" model="[current: 2]"/>

<div class="form_box perform_test">

  <div class="perform_box">

    <div class="video_holder">
      <iframe src="${linkToVideo}" width="560" height="315" frameborder="0"
              webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>
    </div>

<div class="info_block">
  <p class="bigger_text"><g:message code="test.instructions.defintion" /></p>
</div>
    <g:javascript>
        var updatePracticeTestsUrl = "${createLink(controller:'customer',action:'updatePracticeTestsCounter')}"
    </g:javascript>
<div class="btn_block">
  <span class="lbl"><g:message code="test.start.ready"/></span>
  <a href="" class="btn action_btn" data-ng-class="{'btn-link_disabled' : !isComputerCorrect}"
     data-ng-click="startPracticeTest()"><g:message code="test.start.title"/></a>
</div>
</div>

<div class="intro_test">
  <h2 class="center_align_h2"><g:message code="test.steps.practice.subtitle" /></h2>
  <g:if test="${child}">
    <g:render template="steps/instructions/childInstruct" />
  </g:if>
  <g:else>
    <g:render template="steps/instructions/adultInstruct" />
  </g:else>
</div>
<div data-ng-show="showCanvas">
  <g:render template="fullScreenCanvas"/>
</div>
<g:textField name="practiseTestResult" data-ng-model="cptTestData.practiseTestResult" class="hidden"/>
<g:textField name="omission" data-ng-model="cptTestData.omission" class="hidden"/>
<g:textField name="commission" data-ng-model="cptTestData.commission" class="hidden"/>
<g:textField name="testTime" data-ng-model="cptTestData.testTime" class="hidden"/>