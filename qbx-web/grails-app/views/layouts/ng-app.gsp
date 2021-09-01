<!doctype html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title><g:layoutTitle/></title>
  <meta name="viewport" content="width=device-width">
  <asset:stylesheet href="scaffolding.css"/>
  <asset:stylesheet href="application.css"/>
  <asset:stylesheet href="manifest.css"/>
  <g:layoutHead/>
</head>

<body class="inner_page member_area"
      data-ng-app="${pageProperty(name: 'body.data-ng-app')}"
      data-base-url="${pageProperty(name: 'body.data-base-url', default: createLink(action: 'index').replaceAll(/index$/, ''))}"
      data-template-url="${pageProperty(name: 'body.data-template-url', default: createLink(uri: "/ng-templates/$controllerName"))}"
      data-i18n-messages-url="${pageProperty(name: 'body.i18n-messages-url', default: createLink(controller: "info", action: "loadMessage"))}"
      data-common-template-url="${pageProperty(name: 'body.data-common-template-url', default: createLink(uri: '/ng-templates'))}">
<g:render template="/common/member/header"/>

<section class="bg_wrapper">
  <div class="tab_container">
    <div class="tabs" id="tabs">
      <div class="tabs_tbl">
        <div class="tabs_tbl_row">
          <div class="col1">
            <g:render template="/common/member/sidebar"/>
          </div>

          <div class="col2">
            <div class="tab_content">
              <div class="users_tab_content" data-ng-view>
                <g:layoutBody/>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>

<asset:javascript src="ng-application.js"/>

</body>
</html>