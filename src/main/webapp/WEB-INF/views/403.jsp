<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Error Page</title>
        
        <!-- CSS FILES -->
        <link href="${pageContext.request.contextPath}/src/css/bootstrap.min.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath}/src/css/bootstrap-icons.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath}/src/css/template-homepage.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath}/src/css/template-navbar.css" rel="stylesheet">
        
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <!-- CSS CUSTOME -->
        <style>
            body {
                background-color: #e3f2fd; /* xanh dương nhạt */
            }
            h4 {
                color: #1976d2;
            }
        </style>
        
    </head>
    <body>
        <jsp:include page="layout/header.jsp" />
        
        <jsp:include page="layout/navbar.jsp" />
        
        <div class="section-title text-center">
            <h4>403 - Bạn không có quyền truy cập trang này!</h4>
        </div>
        
        <%@ include file="layout/footer.jsp" %>
    </body>
</html>