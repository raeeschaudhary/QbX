<g:render template="/common/member/list/header" model="[entity: g.message(code: 'testSubject.label')]"/>

<div class="table test_table">
  <div class="tbl_overlay" data-ng-show="table.editMode"></div>
  <g:form controller="customer" action="createTest" name="createTestForm" method="post" style="display: none;">
    <input type="hidden" name="testSubjectId" id="testSubjectId" value=""/>
  </g:form>

  <g:render template="/common/list/deleteDialog"/>

  <table infinite-scroll="loadMoreRows()" infinite-scroll-immediate-check="false">
    <thead>
    <tr>
      <g:render template="/common/list/th" model="[code: 'testSubject.id.label']"/>
      <g:render template="/common/list/th" model="[code: 'testSubject.gender.label']"/>
      <g:render template="/common/list/th" model="[code: 'testSubject.dateOfBirth.label']"/>
      <g:render template="/common/list/th" model="[code: 'testSubject.site.label']"/>
      <g:render template="/common/list/th" model="[code: 'testSubject.sitePatientId.label']"/>
      <g:render template="/common/list/th" model="[code: 'testSubject.dateRegistered.label']"/>
      <g:render template="/common/list/th" model="[code: 'testSubject.qbTestPatientId.label']"/>
      <g:render template="/common/list/th" model="[code: 'testSubject.results', col: 4]"/>
    </tr>
    </thead>
    <tbody>
    <tr class="sub_head">
      <td></td>

      <g:render template="/common/list/td" model="[tooltipField: 'gender', tooltipId: 'undefined']">
        <g:select ui-select2="select2Options" name="gender" from="['male', 'female']" data-ng-init="male"
                  data-ng-model="newItem.gender" class="select_type2"
                  data-ng-class="{'error-field' : errorMessages['undefined']['gender']}"
                  noSelection="['': message(code: 'testSubject.gender.select')]"/>
      </g:render>

      <g:render template="/common/list/td" model="[tooltipField: 'dateOfBirth', tooltipId: 'undefined']">
        <input type="text" data-ng-model="newItem.dateOfBirth" datepick data-valid-date
               data-ng-class="{'error-field' : errorMessages['undefined']['dateOfBirth']}"/>
      </g:render>

      <g:render template="/common/list/td" model="[tooltipField: 'site', tooltipId: 'undefined']">
        <g:select ui-select2="select2Options" class="select_type2" name="newItem.site" optionKey="id" optionValue="name"
                  from="${sites}" data-ng-model="newItem.site.id"
                  data-ng-class="{'error-field' : errorMessages['undefined']['site']}"
                  noSelection="['': message(code: 'testSubject.site.select')]"/>
      </g:render>

      <td></td>
      <td></td>

      <g:render template="/common/list/td" model="[tooltipField: 'qbTestPatientId', tooltipId: 'undefined']">
        <input type="text" data-ng-model="newItem.qbTestPatientId"
               data-ng-class="{'error-field' : errorMessages['undefined']['qbTestPatientId']}"/>
      </g:render>

      <td colspan="2"></td>

      <g:render template="/ng-templates/newRowActions"/>
    </tr>

    <tr data-ng-repeat="item in list | orderBy:'id':true" delete-dialog="" data-ng-class="{'editable_row': item.editMode}">
      <g:render template="/common/list/td" model="[className: 'id']">
        {{item.id}}
      </g:render>

      <g:render template="/common/list/td">
        <span data-ng-hide="item.editMode">{{item.gender}}</span>
        <g:select ui-select2="" name="gender" from="['male', 'female']" data-ng-show="item.editMode"
                  data-ng-model="item.gender"/>
      </g:render>

      <g:render template="/common/list/td">
        <span data-ng-hide="item.editMode">{{item.dateOfBirth | date:'<g:message
            code="default.date.format"/>' }}</span>
        <input type="text" data-ng-show="item.editMode" datepick data-ng-model="item.dateOfBirth"/>
      </g:render>

      <g:render template="/common/list/td">
        <span data-ng-hide="item.editMode">{{item.site.name}}</span>
        <g:select ui-select2="" name="item.site" optionKey="id" optionValue="name"
                  noSelection="['': message(code: 'testSubject.site.select')]"
                  from="${sites}" data-ng-show="item.editMode" data-ng-model="item.site.id"/>
      </g:render>

      <g:render template="/common/list/td" model="[className: 'plainText']">
        {{item.publicId}}
      </g:render>

      <g:render template="/common/list/td" model="[className: 'plainText']">
        {{item.dateCreated | date:'<g:message code="default.date.format"/>'}}
      </g:render>

      <g:render template="/ng-templates/editableTextCell" model="[propertyName: 'qbTestPatientId']"/>

      <g:render template="/common/list/td" model="[className: 'plainText']">
        <a data-ng-click="showTestResults()"><g:message code="testSubject.testResult"/></a>
      </g:render>

      <g:render template="/ng-templates/rowActions"/>

      <g:render template="/common/list/td" model="[className: 'doc']">
        <a data-ng-click="createNewTest(item)">
          <i class="icon" title="${g.message(code: 'operation.new.test')}"></i>
        </a>
      </g:render>
    </tr>

    </tbody>
  </table>

  <div class="preloader" data-ng-show="busy"></div>
</div>