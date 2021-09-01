<g:render template="/common/member/list/header" model="[entity: g.message(code: 'professionalUser.label')]"/>

<div class="table users_table">
  <div class="tbl_overlay" data-ng-show="table.editMode"></div>
  <g:render template="/common/list/deleteDialog"/>
  <table infinite-scroll="loadMoreRows()" infinite-scroll-immediate-check="false">
    <thead>
    <tr>
      <g:render template="/common/list/th" model="[code: 'professionalUser.id.label']"/>
      <g:render template="/common/list/th" model="[code: 'professionalUser.firstName.label']"/>
      <g:render template="/common/list/th" model="[code: 'professionalUser.lastName.label']"/>
      <g:render template="/common/list/th" model="[code: 'professionalUser.site.label']"/>
      <g:render template="/common/list/th" model="[code: 'professionalUser.email.label']"/>
      <g:render template="/common/list/th" model="[code: 'professionalUser.password.label']"/>
      <g:render template="/common/list/th" model="[code: 'professionalUser.lastLoginDate.label']"/>
      <g:render template="/common/list/th" model="[code: 'professionalUser.telephone.label']"/>
      <g:render template="/common/list/th" model="[code: 'professionalUser.preferredDateFormat.label']"/>
      <g:render template="/common/list/th" model="[code: 'professionalUser.language.label', col: 3]"/>
    </tr>
    </thead>
    <tbody>
    <tr class="sub_head">
      <td></td>

      <g:render template="/common/list/td" model="[tooltipField: 'firstName', tooltipId: 'undefined']">
        <g:textField name="firstName" data-ng-model="newItem.firstName"
                     data-ng-class="{'error-field' : errorMessages['undefined']['firstName']}"/>
      </g:render>

      <g:render template="/common/list/td" model="[tooltipField: 'lastName', tooltipId: 'undefined']">
        <g:textField name="lastName" data-ng-model="newItem.lastName"
                     data-ng-class="{'error-field' : errorMessages['undefined']['lastName']}"/>
      </g:render>

      <g:render template="/common/list/td" model="[tooltipField: 'site', tooltipId: 'undefined']">
        <g:select ui-select2="select2Options" class="select_type2" name="site.id" from="${sites}" optionKey="id"
                  optionValue="name" data-ng-model="newItem.site.id"
                  noSelection="['': message(code: 'professionalUser.site.placeholder')]"
                  data-ng-class="{'error-field' : errorMessages['undefined']['site']}"/>
      </g:render>

      <g:render template="/common/list/td" model="[tooltipField: 'email', tooltipId: 'undefined']">
        <input type="email" data-ng-model="newItem.email" name="email"
               data-ng-class="{'error-field' : errorMessages['undefined']['email']}"/>
      </g:render>

      <g:render template="/common/list/td" model="[tooltipField: 'password', tooltipId: 'undefined']">
        <input type="password" data-ng-model="newItem.password" name="password"
               data-ng-class="{'error-field' : errorMessages['undefined']['password']}"/>
      </g:render>

      <td></td>

      <g:render template="/common/list/td" model="[tooltipField: 'telephone', tooltipId: 'undefined']">
        <g:textField name="telephone" data-ng-model="newItem.telephone"
                     data-ng-class="{'error-field' : errorMessages['undefined']['telephone']}"/>
      </g:render>
      <g:render template="/common/list/td" model="[tooltipField: 'preferredDateFormat', tooltipId: 'undefined']">
        <g:select ui-select2="select2Options" class="select_type2" name="preferredDateFormat"
                  from="${se.qbtech.qbx.domain.model.site.ProfessionalUser.VALID_DATE_FORMATS}"
                  noSelection="['': message(code: 'professionalUser.dateFormat.placeholder')]"
                  data-ng-class="{'error-field' : errorMessages['undefined']['preferredDateFormat']}"
                  data-ng-model="newItem.preferredDateFormat"/>
      </g:render>

      <g:render template="/common/list/td" model="[tooltipField: 'language', tooltipId: 'undefined']">
        <g:select ui-select2="select2Options" class="select_type2" name="language" from="['en']"
                  optionValue="${{ message(code: 'professionalUser.language.' + it) }}"
                  data-ng-model="newItem.language"
                  noSelection="['': message(code: 'professionalUser.language.placeholder')]"
                  data-ng-class="{'error-field' : errorMessages['undefined']['language']}"/>
      </g:render>

      <g:render template="/ng-templates/newRowActions"/>

    </tr>

    <tr data-ng-repeat="item in list | orderBy:'id':true" data-ng-class="{'editable_row': item.editMode}"
        delete-dialog="">
      <g:render template="/common/list/td" model="[className: 'id']">
        {{item.id}}
      </g:render>

      <g:render template="/ng-templates/editableTextCell" model="[propertyName: 'firstName']"/>
      <g:render template="/ng-templates/editableTextCell" model="[propertyName: 'lastName']"/>

      <g:render template="/ng-templates/editableSelectCell"
                model="[propertyName: 'site.name', selectPropertyName: 'site.id', valuesList: sites, optionKey: 'id', optionValue: 'name', errorMessageKey: 'site']"/>

      <g:render template="/ng-templates/editableTextCell" model="[propertyName: 'email', type: 'email']"/>

      <g:render template="/common/list/td" model="[className: 'plainText']">
        <a data-ng-click="updatePassword(item.id)"><g:message code="common.changePassword"/></a>
      </g:render>

      <g:render template="/common/list/td" model="[className: 'plainText']">
        {{item.lastLoginDate | date:'<g:message code="default.date.format"/>'}}
      </g:render>

      <g:render template="/ng-templates/editableTextCell" model="[propertyName: 'telephone']"/>
      <g:render template="/ng-templates/editableSelectCell"
                model="[propertyName: 'preferredDateFormat', selectPropertyName: 'preferredDateFormat',
                    valuesList: se.qbtech.qbx.domain.model.site.ProfessionalUser.VALID_DATE_FORMATS,
                    noSelection: message(code: 'professionalUser.dateFormat.placeholder')
                ]"/>

      <g:render template="/ng-templates/editableSelectCell"
                model="[propertyName: 'languageName', selectPropertyName: 'language', valuesList: ['en'], optionValue: {
                  message(code: 'professionalUser.language.' + it)
                }, noSelection: message(code: 'professionalUser.language.placeholder')]"/>

      <g:render template="/ng-templates/rowActions"/>
    </tr>

    </tbody>
  </table>

  <div class="preloader" data-ng-show="busy"></div>
</div>