<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags/form"
           prefix="springForm" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>ICR</title>

    <!--[if lte IE 8]><script src="/js/ie/html5shiv.js"></script><![endif]-->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/newTaskWord.css"/>
    <!--[if lte IE 8]><link rel="stylesheet" href="/css/ie8.css" /><![endif]-->
    <!--[if lte IE 9]><link rel="stylesheet" href="/css/ie9.css" /><![endif]-->

    <!-- Favicon -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/siteImages/favicon.ico"/>

</head>

<body class="landing">
<ul>
    <li>
        <h2>${student.name} ${student.surname}</h2>
        <h2>Stai svolgendo il task ${student.taskEffettuati}</h2>
    </li>
</ul>
<div align="center">
    <h3>
        ${task.job.description}
    </h3>
        <table class="pos">
            <tr>
                <c:forEach varStatus="vs" var="sample" items="${positiveSamples}">
                    <td><img
                            src="${pageContext.request.contextPath}/${sample.path}"
                            alt="${sample.id}"/></td>
                </c:forEach>
            </tr>
        </table>
</div>
<form:form method="post" action="secondConsoleWord"
           modelAttribute="taskResults" name="form">
    <div align="center">
        <table>
            <c:forEach varStatus="vs" var="result"
                       items="${taskResults.resultList}">
                <div id="canvasWrapper" style="height: 200px"></div>
                <!-- immagine da etichettare -->
                    <div class="componi">
                        <h3 class="compose">
                            Scrivi nell'area di testo sottostante
                            <br/> quella che secondo te è la parola completa
                        </h3>
                        <input type="text" id="text" name="name"/>
                    </div>
                    <button class="toStart" type=button id="undotoStart">RICOMINCIA</button>
                    <button class="undoColor" type=button id="undoColor">ANNULLA</button>

                    <button class="undoRiga" type=button id="undoRiga">ANNULLA</button>

                    <button class="componi" type=button id="undoComponi">ANNULLA</button>
                    <input class="buttonSi" type="submit" name="action" id="buttonSI"
                           value="SI">
                    <input class="buttonNo" type="submit" name="action" id="buttonNO"
                           value="NO">
                <form:input type="hidden" id="output"
                            path="resultList[${vs.index}].answer"/>
                <br>
                <form:hidden path="resultList[${vs.index}].id"/>
                <form:hidden path="resultList[${vs.index}].image.id"/>
                <form:hidden path="resultList[${vs.index}].task.id"/>
                <form:hidden path="resultList[${vs.index}].task.student.id"/>
                <form:hidden path="resultList[${vs.index}].task.batch"/>
                <form:hidden path="resultList[${vs.index}].task.job.id"/>
                <form:hidden path="resultList[${vs.index}].task.startDate"/>
            </c:forEach>
        </table>
    </div>
        <div align="center" class="selectword">
            <input type="submit" name="action" id="confermaForm"
                   value="Conferma e vai al prossimo task">
        </div>

        <div align="center" class="componi">
            <input type="submit" name="action" id="confermaFormCompleta"
                   value="Conferma e vai al prossimo task">
        </div>
        <div class="negSamp" align="center">
            <p></p>
            <h3>Attenzione: non vanno bene immagini come queste</h3>
            <table class="neg">
                <tr>
                    <c:forEach varStatus="vs" var="sample" items="${negativeSamples}">
                        <td><img
                                src="${pageContext.request.contextPath}/${sample.path}"
                                alt="${sample.id}"/></td>
                    </c:forEach>
                </tr>
            </table>
        </div>
</form:form>
<div align="center">
    <form:form method="post" action="homeStudent">
        <input type="submit" value="Torna alla pagina dello studente">
    </form:form>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function () {

        var c = new ExtendedCanvas('#canvasWrapper', '${pageContext.request.contextPath}/${taskResults.resultList[0].image.path}', '${hint}', ${taskResults.resultList[0].task.job.tutorial});
        c.element.addEventListener('click', function (e) {
            var x = e.pageX - this.offsetLeft;
            var y = e.pageY - this.offsetTop;
            c.fill(x, y);
        });
        c.element.addEventListener('click', function (e) {
            c.drawLines(e)
        });
        $("#undoColor").click(function () {
            c.undoColor();
        });
        $("#undoRiga").click(function () {
            c.undoRiga();
        });
        $("#undotoStart").click(function () {
            c.undoToStart();
        });
        $("#undoComponi").click(function () {
            c.undoComponi();
        });
        $("#confermaForm").click(function () {
            $("#output")[0].value = JSON.stringify(c.getOutput().sort());
            if (!c.checkAnswer()) $(".wrongAnswer").show();
            return c.checkAnswer();
        });
        $("#confermaFormCompleta").click(function () {
            c.setOutput($("#text").val());
            $("#output")[0].value = JSON.stringify(c.getOutput());
            return c.checkAnswer();
        });
        $("#buttonSI").click(function () {
            c.setOutput("SI");
            $("#output")[0].value = JSON.stringify(c.getOutput());
            $("#buttonSI").click();
        });
        $("#buttonNO").click(function () {
            c.setOutput("NO");
            $("#output")[0].value = JSON.stringify(c.getOutput());
            $("#buttonNO").click();
        });
        $("#showHint").mousedown(function () {
            c.showAnswer();
        });
        $("#showHint").mouseup(function () {
            c.hideAnswer();
        });
    });

</script>
<script src="js/selectAll.js"></script>
<script src="${pageContext.request.contextPath}/jobScripts/${task.job.typology}/${jsPath}"></script>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script
        src="${pageContext.request.contextPath}/js/jquery.scrollex.min.js"></script>
<script
        src="${pageContext.request.contextPath}/js/jquery.scrolly.min.js"></script>
<script src="${pageContext.request.contextPath}/js/skel.min.js"></script>
<script src="${pageContext.request.contextPath}/js/util.js"></script>
<!--[if lte IE 8]><script src="/js/ie/respond.min.js"></script><![endif]-->
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script src="${pageContext.request.contextPath}/js/backButton.js"></script>
</body>
</html>