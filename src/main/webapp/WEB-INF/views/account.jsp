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
                max-width: 700px;
                margin: 20px auto;
                border-radius: 12px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            }
            .section-title, .section-subtitle {
                max-width: 700px;
                margin-left: auto;
                margin-right: auto;
            }
            .profile-img-container {
                position: relative;
                width: 100px;
                height: 100px;
                margin: auto;
            }

            .profile-img-container input[type="file"] {
                display: none;
            }

            .profile-img {
                width: 100px;
                height: 100px;
                object-fit: cover;
                border-radius: 50%;
                cursor: pointer;
            }

            .edit-icon {
                position: absolute;
                right: 0;
                bottom: 0;
                background-color: #fff;
                border-radius: 50%;
                padding: 5px;
            }

            .form-control[readonly] {
                background-color: #f8f9fa;
                border: none;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/WEB-INF/views/layout/header.jsp" />
        <jsp:include page="/WEB-INF/views/layout/navbar.jsp" />

        <div class="container-account">
            <form id="accountForm" action="updateAccount" method="post" enctype="multipart/form-data">
                <h5 class="mb-1 section-title">Account Settings</h5>
                <p class="text-muted mb-2 section-subtitle">Manage your personal information</p>
                <div class="card p-4 settings-card">
                    <div class="text-center mb-3 profile-img-container">
                        <label for="avatarInput">
                            <c:choose>
                                <c:when test="${empty thanhVien.urlAvatar}">
                                    <img src="${pageContext.request.contextPath}/src/images/default-avatar.png" alt="avatar" class="profile-img" />
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/src/images/${thanhVien.urlAvatar}" alt="avatar" class="profile-img" />
                                </c:otherwise>
                            </c:choose>
                            <img src="${pageContext.request.contextPath}/src/images/edit-icon.png" class="edit-icon" width="24" />
                        </label>
                        <input type="file" name="avatarFile" id="avatarInput" />
                    </div>

                    <input type="hidden" name="maThanhVien" value="${thanhVien.maThanhVien}" />

                    <div class="mb-3">
                        <label>Email</label>
                        <input type="email" class="form-control" value="${user.email}" readonly />
                    </div>

                    <div class="mb-3">
                        <label>Password</label>
                        <input type="password" class="form-control" value="${user.matKhau}" readonly />
                    </div>

                    <div class="mb-3">
                        <label>Họ tên</label>
                        <input type="text" class="form-control" name="hoTen" value="${thanhVien.hoTen}" readonly />
                    </div>

                    <div class="mb-3">
                        <label>Số điện thoại</label>
                        <input type="text" class="form-control" name="sdt" value="${thanhVien.sdt}" readonly />
                    </div>

                    <div class="mb-3">
                        <label>Địa chỉ</label>
                        <input type="text" class="form-control" name="diaChi" value="${thanhVien.diaChi}" readonly />
                    </div>

                    <div class="d-flex justify-content-between">
                        <button type="button" class="btn btn-secondary" onclick="enableEdit()">Edit</button>
                        <button type="submit" class="btn btn-primary" id="saveBtn" disabled>Cập nhật</button>
                    </div>
                </div>
            </form>
        </div>

        <script>
            function enableEdit() {
                const form = document.getElementById("accountForm");
                const inputs = form.querySelectorAll("input.form-control");

                inputs.forEach(input => {
                    input.removeAttribute("readonly");
                });

                document.getElementById("saveBtn").removeAttribute("disabled");
            }
        </script>

        <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
    </body>
</html>
