<%--
  Created by IntelliJ IDEA.
  User: muhammad
  Date: 4/28/14
  Time: 9:48 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="test" />
  <title><g:message code="questionnaire.page.title" /></title>
  <script type="text/javascript">
    function isAnswered(question, div){
      var radio_group = document.getElementsByName(question);
      for(var x=0;x<radio_group.length;x++){
        if(radio_group[x].checked){
          document.getElementById(div).className = "newClass"
          return 0;
        }
      }
      document.getElementById(div).className = "notAnswered"
      return 1;
    }
    function checkQuestionAnswer(){
      var leftBlank = 0;
      for (var i = 1; i <= 18; i++){
        var question = document.getElementById("question" + i.toString())
        var answers = question.getElementsByTagName("input")[0]
        leftBlank += isAnswered(answers.name.toString(), question.id.toString());
      }
      if(leftBlank > 0){
        document.getElementById("alertBox").style.display = "block"
        alert("Please answer all questions");
        return false;
      }else{
        document.getElementById("alertBox").style.display = "none"
        return true;
      }
    }
    function checkChanged(questionNumber){
      var leftBlank = 0;
        var question = document.getElementById("question" + questionNumber.toString())
        var answers = question.getElementsByTagName("input")[0]
        leftBlank = isAnswered(answers.name.toString(), question.id.toString());
    }

  </script>
</head>

<body class="inner_page">
<div class="container content_page">
  <section class="container">
    <div class="alert-box" id="alertBox" style="display: none">Please answer all questions</div>
    <g:if test="${flash.message}">
      <div class="alert-box">${flash.message}</div>
    </g:if>

    <g:if test="${ageGroup == 0}">
      <h1><g:message code="questionnaire.heading.child" /></h1>
      <p class="bigger_text"><g:message code="questionnaire.instruction.child" /></p><br />
    </g:if>
    <g:else>
      <h1><g:message code="questionnaire.heading.adult" /></h1>
      <p class="bigger_text"><g:message code="questionnaire.instruction.adult" /></p><br />
    </g:else>

    <g:form controller="customer" action="createTest" onsubmit="return checkQuestionAnswer()">
      <g:set var="counter" value="${1}"></g:set>

      <g:each in="${questions}" var="question">
        <div class="newClass" id="question${counter}">
          <p class="bigger_text question_box"><b>${question.questionDescription}</b> </p>

          <g:each in="${options}" var="option">
            <span class="inp_field">
              <input type="radio" name="${question.id}" id="${option.scaleValue}"
                     value="${option.scaleValue}" onchange="checkChanged(${counter})"/>
              <label for="${question.id}"> ${option.answer}</label>
            </span>
          </g:each>

          <br /><br />
          <g:set var="counter" value="${counter + 1}"></g:set></div>
      </g:each>

      <br />
      <input type="hidden" name="testSubjectId" value="${testSubjectId}" />
      <input type="hidden" value="${ageGroup}" name="ageGroup">

      <div class="field btn_block_p10">
        <button type="submit" class="btn action_btn"><g:message code="questionnaire.submit" /></button>
      </div>
    </g:form>

  </section>
</div>
</body>
</html>