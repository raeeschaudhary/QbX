<g:render template="steps/stepLine" model="[current: 1]"/>
<div id="arrow" class="arrow">
  <div>Please allow</div>
  <div>access to</div>
  <div>camera</div>
</div>
<div class="form_box setup_test">
  <div class="test_col1">
    <div class="field" data-ng-hide="showFpsError"><p><g:message code="test.subtitle"/></p>
    </div>
  </div>
  <div class="field">
    <div class="col1">
      <div class="test__error-box" data-ng-show="showFpsError">
        <p><g:message code="test.fps.wrong"/></p>
        <div class="btn_block_p10">
          <a href="" data-ng-click="restartCheckComputer()" class="btn action_btn"><g:message code="goto.computer.check"/></a>
        </div>
      </div>

      <div class="video_holder">
        <div class="text">
          <g:message code="allow.access.camera" />
        </div>
      </div>
    <div class="btn_block">
      <span class="lbl" data-ng-hide="showFpsError"><g:message code="test.start.ready1"/></span>
      <a href="" data-ng-click="goToPracticeTest()" class="btn action_btn" data-ng-hide="showFpsError"
         data-ng-class="{'btn-link_disabled' : !isComputerCorrect}"><g:message code="test.start.button"/></a>
    </div>

    </div>
    <div class="col2">
      <ul class="step_list">
        <li>
          <i class="icon" data-ng-class="{'complete' : cptTestData.isFaceTracked}">1</i>
          <g:message code="test.requirements.track"/>
        </li>
        <li>
          <i class="icon" data-ng-class="{'complete' : isComputerCorrect}">2</i>
          <g:message code="test.requirements.fps"/>
        </li>
      </ul>
    </div>
  </div>
</div>