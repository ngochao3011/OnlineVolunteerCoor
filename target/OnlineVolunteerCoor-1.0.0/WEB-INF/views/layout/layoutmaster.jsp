<%-- 
    Document   : layoutmaster
    Created on : Jun 4, 2025, 11:42:27 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <meta name="description" content="">
    <meta name="author" content="">

    <title><jsp:include page="${pageTitle}" /></title>

    <!-- CSS FILES -->
    <link href="${pageContext.request.contextPath}/src/css/bootstrap.min.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/src/css/bootstrap-icons.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/src/css/template-homepage.css" rel="stylesheet">

</head>

<body>

    <jsp:include page="/WEB-INF/views/layout/header.jsp" />

    <jsp:include page="${body}" />

    <%@ include file="/WEB-INF/views/layout/footer.jsp" %>

</body>

</html>