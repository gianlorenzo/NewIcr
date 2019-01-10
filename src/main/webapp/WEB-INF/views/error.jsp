<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<html>
<head>
    <title>ICR</title>
    <style>
        table td {
            vertical-align: top;
            border: solid 1px #888;
            padding: 10px;
        }
    </style>
    <!-- Favicon -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/siteImages/favicon.ico"/>
</head>
<body>
<h1>Ops...Qualcosa è andato storto!</h1>
<a href="logout">Torna alla pagina di login</a>
<table>
    <tr>
        <td>Date:</td>
        <td><c:out value="${timestamp}"/></td>
    </tr>
    <tr>
        <td>Path:</td>
        <td><c:out value="${path}"/></td>
    </tr>
    <tr>
        <td>Error:</td>
        <td><c:out value="${error}"/></td>
    </tr>
    <tr>
        <td>Status:</td>
        <td><c:out value="${status}"/></td>
    </tr>
    <tr>
        <td>Exception:</td>
        <td><c:out value="${exception}"/></td>
    </tr>
    </tr>
    <tr>
        <td>Trace:</td>
        <td><c:out value="${trace}"/></td>
    </tr>
    <tr>
        <td>Date:</td>
        <td>
            <pre> <c:out value="${timestamp}"/> </pre>
        </td>
    </tr>
</table>
</body>
</html>
