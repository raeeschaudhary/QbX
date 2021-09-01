<ul class="tabs_list">
  <li class="tab_item ${params.controller == 'professionalUser' ? 'tabs-active' : ''}">
    <a href="${createLink(uri: '/professionalUser/')}" class="ui-tabs-link"><g:message code="sidebar.users"/></a>
  </li>

  <li class="tab_item sites_tab ${params.controller == 'site' ? 'tabs-active' : ''}">
    <a href="${createLink(uri: '/site/')}" class="ui-tabs-link"><g:message code="sidebar.sites"/></a>
  </li>

  <li class="tab_item test_tab ${params.controller == 'testSubject' ? 'tabs-active' : ''}">
    <a href="${createLink(uri: '/testSubject/')}" class="ui-tabs-link"><g:message code="sidebar.test"/></a>
  </li>

  <li class="tab_item vouchers_tab ${params.controller == 'voucher' ? 'tabs-active' : ''}">
    <a href="${createLink(uri: '/voucher/')}" class="ui-tabs-link"><g:message code="sidebar.vouchers"/></a>
  </li>

  <li class="tab_item trans_tab ${params.controller == 'transaction' ? 'tabs-active' : ''}">
    <a href="${createLink(uri: '/transaction/')}" class="ui-tabs-link"><g:message code="sidebar.transactions"/></a>
  </li>
</ul>