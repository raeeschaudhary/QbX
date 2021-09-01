<g:render template="steps/stepLine" model="[current: 3]"/>

<div class="form_box start_test">

  <div data-ng-show="showCanvas">
    <g:render template="fullScreenCanvas"/>
  </div>

  <div data-ng-hide="showCanvas">
    <div class="test_box">
      <!--<p><g:message code="test.remember"/></p>-->

      <p><g:message code="test.ready"/></p>

    </div>

    <div class="btn_block">
      <a href="" data-ng-click="startTest()" class="btn action_btn"><g:message code="test.start"/></a>
    </div>
  </div>

  <div data-ng-show="showCanvas">
    <div class="test_box">
      <p><g:message code="test.completed"/></p>

    </div>
  </div>
</div>

<g:form style="display: none;" name="testResultsForm" controller="customer" action="thankYou">
  <input type="text" name="testResults" id="testResults"/>
</g:form>