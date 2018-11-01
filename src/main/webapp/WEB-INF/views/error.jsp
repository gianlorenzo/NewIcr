<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<html>
<head>
    <title>ICR</title>
    <style>
        table td{
            vertical-align:top;
            border:solid 1px #888;
            padding:10px;
        }
    </style>
</head>
<body>
<h1>ICR ERROR PAGE</h1>
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
        <td><c:out value="${expetion}"/></td>
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
