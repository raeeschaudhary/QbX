<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="contentPage"/>
  <title><g:message code="info.home.title"/></title>
</head>

<body>
<section class="container home_page">
  <div class="form_box">
    <div class="column_layout_3x">
      <div class="column1 col1">
        <div class="pic_block"></div>

        <h3><g:message code="info.home.subHeader"/></h3>

        <div class="text">
          <g:message code="info.home.left.description"/>
        </div>

        <div class="btn_block">
          <a href="${g.createLink(controller: 'customer', action: 'registration')}" class="btn type2">
            <g:message code="info.home.left.link"/>
          </a>
        </div>
      </div>

      <div class="column2 col2">
        <div class="pic_block"></div>

        <h3><g:message code="info.home.mid.subHeader"/></h3>

        <div class="text">
          <g:message code="info.home.mid.description"/>
        </div>

        <div class="btn_block">
          <a href="${g.createLink(uri: '/professionalUserActions')}/#testSubjects" class="btn type2">
            <g:message code="info.home.mid.link"/>
          </a>
        </div>
      </div>

      <div class="column3 col3">
        <div class="pic_block"></div>

        <h3><g:message code="info.home.right.subHeader"/></h3>

        <div class="text">
          <g:message code="info.home.right.description"/>
        </div>

        <div class="btn_block">
          <a href="${g.createLink(controller: 'professionalUserActions')}" class="btn type2">
            <g:message code="info.home.right.link"/>
          </a>
        </div>
      </div>
    </div>
  </div>
</section>
</body>
</html>