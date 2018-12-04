<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>



<%@ page import="it.uniroma3.icr.model.Symbol"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ICR</title>

<!--[if lte IE 8]><script src="/js/ie/html5shiv.js"></script><![endif]-->
 <link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/registration.css?1" />
<!--[if lte IE 8]><link rel="stylesheet" href="/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="/css/ie9.css" /><![endif]-->

<!-- Favicon -->
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/siteImages/favicon.ico" />

	<style type="text/css">
		.symbolSelect {
			position:relative;
			margin-bottom:40px;
			display: none;
		}
	</style>

</head>
<body class="landing">
	<jsp:include page="menu.jsp" />
	<br>
	<h1>Creazione del Job</h1>
	<div class="form">
		<div class="tab-content">
			<div id="signup">
				<h1>Inserisci Dati del Job</h1>
				<form:form method="post" action="addJobByManuscript"
					modelAttribute="job" name="form">
					<div class="field-wrap">
						<label> Titolo </label>
						<form:input type="text" value="${job.title}" path="title"
							placeholder="Titolo" />
						${errTitle}
					</div>
					<div class="field-wrap">
						<div class="field-wrap">
							<label> #Studenti </label>
							<form:input type="text" value="${job.students}" path='students'
								placeholder="Numero di Studenti" onBlur="isnum(this)" />
							${errStudenti}
						</div>
						<div class="field-wrap">
							<label> Descrizione </label>
							<form:select id="desc" type="text" path="description">
								<font color="7a0000"><form:options items="${descriptions}"/> </font>
							</form:select>
						</div>
						<form:input type="hidden" value="1" path='taskSize'
							placeholder="Dimensione Task" onBlur="isnum(this)" />
						<div class="symbolSelect">
							<label> Simbolo </label>
								<form:select id="sy" path="symbol">
									<form:option value="nessun simbolo"></form:option>
									<font color="7a0000"><form:options items="${symbols}"
											itemLabel="transcription" itemValue="id" /> </font>
								</form:select>
						</div>
						<div class="field-wrap">
							<label> E' un tutorial? </label>
							<form:select path="tutorial">
								<option value="false">NO</option>
								<option value="true">SI</option>
							</form:select>
						</div>
						<div id="formsubmitbutton">
							<button type="submit" class="button button-block" name="action"
								value="WORD" onclick="ButtonClicked()">Conferma</button>
						</div>
					</div>
				</form:form>
			</div>
			<div id="buttonreplacement" style="margin-left: 100px; display: none;">
				<img
					src="${pageContext.request.contextPath}/img/siteImages/loadIcon.gif"
					alt="">
			</div>
			<div id="login"></div>
		</div>
		<!-- tab-content -->
	</div>
	<!-- /form -->



	<script src="js/selectAll.js"></script>


	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>

	<script>
        $(document).ready(function() {
            $('#desc').change(function() {
                var val = $('#desc').val();
                $('.symbolSelect').hide();
                if(val=='trovaPartiColorate' || val=='trovaInteroSimbolo') {
                    $('.symbolSelect').show();
                }
            });
        });
	</script>
	<script
		src="${pageContext.request.contextPath}/js/jquery.scrollex.min.js"></script>
	<script src="js/selectAll.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/jquery.scrolly.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/skel.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/util.js"></script>
	<!--[if lte IE 8]>-->
	<script src="${pageContext.request.contextPath}/js/ie/respond.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/main.js"></script>
	<script src="${pageContext.request.contextPath}/js/file.js"></script>
	<script src="${pageContext.request.contextPath}/js/load.js"></script>
</body>
</html>