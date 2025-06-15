<%-- 
    Document   : account
    Created on : Jun 16, 2025, 1:40:18 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Account Settings</title>
        
        <!-- CSS FILES -->
        <link href="${pageContext.request.contextPath}/src/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-homepage.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-navbar.css" rel="stylesheet">
        
        <style>
            .container-account {
                background-color: #ffffff;
                padding: 15px 30px;
                box-shadow: 0 0 20px rgba(0,0,0,0.1);
            }
            .settings-card {
                max-width: 600px;
                margin: 50px auto;
                border-radius: 12px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            }
            .profile-img {
                width: 100px;
                height: 100px;
                object-fit: cover;
                border-radius: 50%;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/WEB-INF/views/layout/header.jsp" />
        <jsp:include page="/WEB-INF/views/layout/navbar.jsp" />

            <div class="container-account">
                <div class="card p-4 settings-card">
                    <h2 class="mb-3">Account Settings</h2>
                    <p class="text-muted mb-4">Manage your user profile</p>

                    <div class="text-center mb-3">
                        <img src="" alt="avatar" class="profile-img"/>
                    </div>

                    <form action="updateAccount" method="post">
                        <div class="mb-3">
                            <label>Email</label>
                            <p></p>
                        </div>

                        <div class="mb-3">
                            <label>Password</label>
                            <p>*********</p>
                        </div>

                        <div class="mb-3">
                            <label>Name</label>
                            <input type="text" class="form-control" name="name" value="" required>
                        </div>

                        <button type="submit" class="btn btn-primary">Update</button>
                    </form>
                </div>
            </div>

        <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
    </body>
</html>
