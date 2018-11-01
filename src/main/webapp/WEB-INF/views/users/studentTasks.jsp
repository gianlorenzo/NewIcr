<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="it.uniroma3.icr.model.Task"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ICR</title>

<!--[if lte IE 8]><script src="/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main.css" />
<!--[if lte IE 8]><lfnink rel="stylesheet" href="/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="/css/ie9.css" /><![endif]-->

<!-- Favicon -->
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/siteImages/favicon.ico" />
</head>
<body class="landing">
	<jsp:include page="menu.jsp" />
	<div class="relative">
		<h2>Task Effettuati da: ${s.name}</h2>
	</div>
	<table>
		<tr>
			<th>Numero di Task Effettuati</th>
		</tr>
		<tr>
			<th><c:out value="${s.taskEffettuati}"></c:out></th>
		</tr>
		<tr>
			<th>Tempo Impiegato</th>
		</tr>
		<tr>
			<th><c:out value="${time}"></c:out></th>
		</tr>
	<c:choose>
    <c:when test = "${s.tempoEffettuato/(s.taskEffettuati+1.) > 80}">
		<tr>
			<th><c:out value="${((s.tempoEffettuato - s.tempoEffettuato%3600)/3600).intValue()}:${((s.tempoEffettuato%3600 - s.tempoEffettuato%3600%60)/60).intValue()}:${s.tempoEffettuato%3600%60}"></c:out></th>
		</tr>
	</c:when>
	<c:when test = "${s.tempoEffettuato/(s.taskEffettuati+1.) <= 80}">
		<tr>
			<th>In manutenzione</th>
		</tr>
	</c:when>
	</c:choose>
		<tr>
			<th>Tempo medio per task (in secondi)</th>
		</tr>
		<tr>
			<th><c:out value="${(s.tempoEffettuato- s.tempoEffettuato%s.taskEffettuati)/s.taskEffettuati}"></c:out></th>
		</tr>
	</table>
		<!-- Scripts -->
		<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script
			src="${pageContext.request.contextPath}/js/jquery.scrollex.min.js"></script>
		<script
			src="${pageContext.request.contextPath}/js/jquery.scrolly.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/skel.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/util.js"></script>
		<!--[if lte IE 8]><script src="/js/ie/respond.min.js"></script><![endif]-->
		<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>