<g:render template="/common/member/list/header" model="[entity: g.message(code: 'site.label')]"/>

<div class="table sites_table">
  <div class="tbl_overlay" data-ng-show="table.editMode"></div>
  <g:render template="/common/list/deleteDialog"/>
  <table infinite-scroll="loadMoreRows()" infinite-scroll-immediate-check="false">
    <thead>
    <tr>
      <g:render template="/common/list/th" model="[code: 'site.id.label']"/>
      <g:render template="/common/list/th" model="[code: 'site.name.label']"/>
      <g:render template="/common/list/th" model="[code: 'site.address.label']"/>
      <g:render template="/common/list/th" model="[code: 'site.email.label']"/>
      <g:render template="/common/list/th" model="[code: 'site.phone.label']"/>
      <g:render template="/common/list/th" model="[code: 'site.state.label']"/>
      <g:render template="/common/list/th" model="[code: 'site.paymentMethod.label']"/>
      <g:render template="/common/list/th" model="[code: 'site.billingAddress.label']"/>
      <g:render template="/common/list/th" model="[code: 'site.notes.label', col: 3]"/>
    </tr>
    </thead>
    <tbody>
    <tr class="sub_head">
      <td></td>
      <g:render template="/common/list/td" model="[tooltipField: 'name', tooltipId: 'undefined']">
        <g:textField name="name" data-ng-model="newItem.name"
                     data-ng-class="{'error-field' : errorMessages[undefined]['name'] }"/>
      </g:render>

      <g:render template="/common/list/td" model="[className: 'plainText']">
        <a data-ng-click="editAddress(newItem, 'address')">
          <span data-ng-show="newItem.address.address1">{{newItem.address.address1}}, {{newItem.address.city}}</span>
          <span data-ng-show="!newItem.address.address1"><g:message code="site.address.change"/></span>
        </a>
      </g:render>

      <g:render template="/common/list/td" model="[tooltipField: 'email', tooltipId: 'undefined']">
        <g:textField name="email" data-ng-model="newItem.email"
                     data-ng-class="{'error-field' : errorMessages[undefined]['email'] }"/>
      </g:render>

      <g:render template="/common/list/td" model="[tooltipField: 'phone', tooltipId: 'undefined']">
        <g:textField name="phone" data-ng-model="newItem.phone"
                     data-ng-class="{'error-field' : errorMessages[undefined]['phone'] }"/>
      </g:render>

      <g:render template="/common/list/td">
        <span class="inp_field">
          <input type="checkbox" data-ng-model="newItem.isEnabled"/>
          <label></label>
        </span>
      </g:render>

      <g:render template="/common/list/td" model="[tooltipField: 'paymentMethod', tooltipId: 'undefined']">
        <g:select ui-select2="select2Options" class="select_type2" name="newItem.paymentMethod" from="${paymentMethods}"
                  data-ng-model="newItem.paymentMethod"
                  noSelection="['': message(code: 'site.paymentMethod.placeholder')]"
                  data-ng-class="{'error-field' : errorMessages[undefined]['paymentMethod']}"/>
      </g:render>

      <g:render template="/common/list/td" model="[className: 'plainText']">
        <a data-ng-click="editAddress(newItem, 'billingAddress')">
          <span data-ng-show="newItem.billingAddress.address1">
            {{newItem.billingAddress.address1}}, {{newItem.billingAddress.city}}
          </span>
          <span data-ng-show="!newItem.billingAddress.address1"><g:message code="site.billingAddress.change"/></span>
        </a>
      </g:render>

      <g:render template="/common/list/td">
        <g:textField name="notes" data-ng-model="newItem.notes"/>
      </g:render>

      <g:render template="/ng-templates/newRowActions"/>
    </tr>

    <tr data-ng-repeat="item in list | orderBy:'id':true" data-ng-class="{'editable_row': item.editMode}"
        delete-dialog="">
      <g:render template="/common/list/td" model="[className: 'id']">
        {{item.id}}
      </g:render>

      <g:render template="/ng-templates/editableTextCell" model="[propertyName: 'name']"/>

      <g:render template="/common/list/td" model="[className: 'plainText']">
        <a data-ng-click="editAddress(item, 'address')">
          <span data-ng-show="item.address.address1">{{item.address.address1}}, {{item.address.city}}</span>
          <span data-ng-show="!item.address.address1"><g:message code="site.address.change"/></span>
        </a>
      </g:render>

      <g:render template="/ng-templates/editableTextCell" model="[propertyName: 'email', type: 'email']"/>

      <g:render template="/ng-templates/editableTextCell" model="[propertyName: 'phone', type: 'tel']"/>

      <g:render template="/common/list/td">
        <span data-ng-hide="item.editMode">{{item.isEnabled}}</span>
        <span class="inp_field">
          <input type="checkbox" data-ng-show="item.editMode" data-ng-model="item.isEnabled"/>
          <label data-ng-show="item.editMode"></label>
        </span>
      </g:render>

      <g:render template="/ng-templates/editableSelectCell" model="[propertyName: 'paymentMethod.name',
          selectPropertyName: 'paymentMethod.name', valuesList: paymentMethods]"/>

      <g:render template="/common/list/td" model="[className: 'plainText']">
        <a data-ng-click="editAddress(item, 'billingAddress')">
          <span
              data-ng-show="item.billingAddress.address1">{{item.billingAddress.address1}}, {{item.billingAddress.city}}</span>
          <span data-ng-show="!item.billingAddress.address1"><g:message code="site.billingAddress.change"/></span>
        </a>
      </g:render>

      <g:render template="/ng-templates/editableTextCell" model="[propertyName: 'notes']"/>

      <g:render template="/ng-templates/rowActions"/>
    </tr>

    </tbody>
  </table>

  <div class="preloader" data-ng-show="busy"></div>
</div>