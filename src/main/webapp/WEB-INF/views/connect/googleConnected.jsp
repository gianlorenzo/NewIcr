<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>ICR</title>

    <!--[if lte IE 8]><script src="/js/ie/html5shiv.js"></script><![endif]-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>
    <!--[if lte IE 8]><link rel="stylesheet" href="/css/ie8.css" /><![endif]-->
    <!--[if lte IE 9]><link rel="stylesheet" href="/css/ie9.css" /><![endif]-->

    <!-- Favicon -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/siteImages/favicon.ico"/>
</head>
<body class="landing">
<!-- Page Wrapper -->
<div id="page-wrapper">
    <!-- Header -->
    <header id="header" class="alt">
        <h1>
            <a href="index.html">In Codice Ratio</a>
        </h1>
    </header>
</div>
<!-- Banner -->
<section id="banner">
    <div class="inner">
        <h2>Hai eseguito il login dal tuo account Google con successo </h2>
        <form action="${pageContext.request.contextPath}/googleLogin" method="post">
            <button type="submit" style='cursor:pointer'>Prosegui</button>
            <input type="hidden" name="daGoogle"/>
            <input type="hidden" name="social" value="goo"/>
        </form>
        <span class="container"></span>
    </div>
</section>

<!-- Scripts -->
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollex.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrolly.min.js"></script>
<script src="${pageContext.request.contextPath}/js/skel.min.js"></script>
<script src="${pageContext.request.contextPath}/js/util.js"></script>
<!--[if lte IE 8]><script src="/js/ie/respond.min.js"></script><![endif]-->
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script src="${pageContext.request.contextPath}/js/backButton.js"></script>
</body>
</html>