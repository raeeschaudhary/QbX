<g:render template="steps/stepLine" model="[current: 3]"/>

<div class="form_box start_test">
  <div class="test__error-box" data-ng-show="omission">
    <p><g:message code="test.fail.omission"/></p>
  </div>
  <div class="test__error-box" data-ng-show="commission">
   <p><g:message code="test.fail.commission"/></p>
  </div>
  <div class="test__error-box" data-ng-show="testTime">
    <p><g:message code="test.fail.escaped"/></p>
  </div>

  <div data-ng-show="showCanvas">
    <g:render template="fullScreenCanvas"/>
  </div>

  <div class="btn_block">
    <a href="" data-ng-click="reviewTestInstructions()" class="btn action_btn"><g:message code="test.fail.review"/></a>
    <a href="" data-ng-click="doPracticeTestAgain()" class="btn action_btn"><g:message code="test.fail.again"/></a>
      </div>
</div>

<g:form style="display: none;" name="testResultsForm" controller="customer" action="finishTest">
  <input type="text" name="testResults" id="testResults"/>
</g:form>