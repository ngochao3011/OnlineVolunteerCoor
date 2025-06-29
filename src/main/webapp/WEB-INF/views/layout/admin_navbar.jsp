<%-- 
    Document   : admin_navbar
    Created on : Jun 27, 2025, 11:59:05 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<!-- partial:partials/_navbar.html -->
<nav class="navbar default-layout col-lg-12 col-12 p-0 fixed-top d-flex align-items-top flex-row">
    <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-start">
        <div class="me-3">
            <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-bs-toggle="minimize">
                <span class="icon-menu"></span>
            </button>
        </div>
        <div>
            <a class="navbar-brand brand-logo" href="${pageContext.request.contextPath}/">
                <img src="${pageContext.request.contextPath}/src/images/logo.png" alt="logo" />
                <span>
                    <h6>Admin Page</h6>
                </span>
            </a>
            <a class="navbar-brand brand-logo-mini" href="${pageContext.request.contextPath}/">
                <img src="${pageContext.request.contextPath}/src/images/logo.png" alt="logo" />
            </a>
        </div>
    </div>
    <div class="navbar-menu-wrapper d-flex align-items-top"> 
        <ul class="navbar-nav">
            <li class="nav-item font-weight-semibold d-none d-lg-block ms-0">
                <h1 class="welcome-text">Xin chào, 
                    <span class="text-black fw-bold">
                        <c:choose>
                            <c:when test="${isLoggedIn and not empty loggedInProfile and not empty loggedInAccount}">
                                ${loggedInProfile.hoTen}
                            </c:when>
                        </c:choose>
                    </span>
                </h1>
                <h3 class="welcome-sub-text">Chúc bạn một ngày tốt lành</h3>
            </li>
        </ul>
        <ul class="navbar-nav ms-auto">
            <li class="nav-item d-none d-lg-block">
                <div id="datepicker-popup" class="input-group date datepicker navbar-date-picker">
                    <span class="input-group-addon input-group-prepend border-right">
                        <span class="icon-calendar input-group-text calendar-icon"></span>
                    </span>
                    <input type="text" class="form-control">
                </div>
            </li>

            <li class="nav-item dropdown d-none d-lg-block user-dropdown">
                <a class="nav-link" id="UserDropdown" href="#" data-bs-toggle="dropdown" aria-expanded="false">
                    <img class="img-xs rounded-circle" src="${pageContext.request.contextPath}${sessionScope.urlAvatar}" alt="Profile image"> </a>
                <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="UserDropdown">
                    <div class="dropdown-header text-center">
                        <img class="img-md rounded-circle" src="${pageContext.request.contextPath}${sessionScope.urlAvatar}"
                             style="width: 120px; height: 120px; border-radius: 50%; object-fit: cover;" alt="Profile image">
                        <c:choose>
                            <c:when test="${isLoggedIn and not empty loggedInProfile and not empty loggedInAccount}">
                                <p class="mb-1 mt-3 font-weight-semibold">${loggedInProfile.hoTen}</p>
                                <p class="fw-light text-muted mb-0">${loggedInAccount.email}</p>
                            </c:when>
                        </c:choose>

                    </div>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/account"><i class="dropdown-item-icon mdi mdi-account-outline text-primary me-2"></i>Thông tin tài khoản</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/logout"><i class="dropdown-item-icon mdi mdi-power text-primary me-2"></i>Đăng xuất</a>
                </div>
            </li>
        </ul>
        <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-bs-toggle="offcanvas">
            <span class="mdi mdi-menu"></span>
        </button>
    </div>
</nav>
