<%-- 
    Document   : layoutmaster
    Created on : Jun 4, 2025, 11:42:27 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title><c:out value="${pageTitle}" /></title>

        <!-- CSS FILES -->
        <link href="${pageContext.request.contextPath}/src/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-master.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <!-- CSS riêng cho từng page nếu có -->
        <c:if test="${not empty customCss}">
            <link href="${pageContext.request.contextPath}${customCss}" rel="stylesheet" />
        </c:if>
        <c:if test="${includeChartJs}">
            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        </c:if>
    </head>

    <body>

        <!-- Header -->
        <jsp:include page="/WEB-INF/views/layout/header.jsp" />

        <!-- Navbar -->
        <jsp:include page="/WEB-INF/views/layout/navbar.jsp" />

        <!-- Hiển thị thông báo -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                <i class="fas fa-exclamation-circle me-2"></i> ${error}
            </div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success">
                <i class="fas fa-check-circle me-2"></i> ${success}
            </div>
        </c:if>

        <!-- Page Content -->
        <jsp:include page="${pageContent}" />

        <!-- Footer -->
        <%@ include file="/WEB-INF/views/layout/footer.jsp" %>

    </body>
</html>