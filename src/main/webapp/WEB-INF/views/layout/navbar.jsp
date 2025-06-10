<%-- 
    Document   : navbar
    Created on : Jun 8, 2025, 2:58:07 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header class="header-section">
    <nav class="navbar navbar-expand-lg bg-light shadow-lg">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <img src="${pageContext.request.contextPath}/src/images/logo.png" class="logo img-fluid" alt="Kind Heart Charity">
                <span>
                    Online Volunteer Coor
                    <small>Non-profit Organization</small>
                </span>
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link click-scroll" href="${pageContext.request.contextPath}/">Home</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link click-scroll" href="#section_2">About</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link click-scroll" href="#section_3">Causes</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link click-scroll" href="#section_4">Volunteer</a>
                    </li>
                    
                    <!-- Nếu đã đăng nhập -->
                    <c:if test="${not empty sessionScope.user}">
                        <li class="nav-item">
                            <a class="nav-link btn" href="${pageContext.request.contextPath}/activity">Activity</a>
                        </li>
                    </c:if>

                    <li class="nav-item dropdown">
                        <a class="nav-link click-scroll dropdown-toggle" href="#section_5"
                            id="navbarLightDropdownMenuLink" role="button" data-bs-toggle="dropdown"
                            aria-expanded="false">News</a>

                        <ul class="dropdown-menu dropdown-menu-light" aria-labelledby="navbarLightDropdownMenuLink">
                            <li><a class="dropdown-item" href="news.html">News Listing</a></li>

                            <li><a class="dropdown-item" href="news-detail.html">News Detail</a></li>
                        </ul>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link click-scroll" href="#section_6">Contact</a>
                    </li>
                    
                    <!-- Nếu đã đăng nhập -->
                    <c:if test="${not empty sessionScope.user}">
                        <li class="nav-item navbar-img ms-3">
                            <div class="dropdown">
                                <img src="${pageContext.request.contextPath}/src/images/default-avatar.png" 
                                     alt="Avatar" class="avatar" onclick="toggleDropdown()" />
                                <div id="accountDropdown" class="dropdown-content">
                                    <a href="${pageContext.request.contextPath}/profile">Tài khoản</a>
                                    <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                                </div>
                            </div>
                        </li>
                    </c:if>

                    <!-- Nếu chưa đăng nhập -->
                    <c:if test="${empty sessionScope.user}">
                        <li class="nav-item ms-3">
                            <a class="nav-link custom-btn custom-border-btn btn" href="${pageContext.request.contextPath}/sign-in">Sign in</a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>
</header>
