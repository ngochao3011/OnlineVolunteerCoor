<%-- 
    Document   : admin_sidebar
    Created on : Jun 28, 2025, 12:12:22 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<!-- partial:partials/_settings-panel.html -->
<div class="theme-setting-wrapper">
    <div id="settings-trigger"><i class="ti-settings"></i></div>
    <div id="theme-settings" class="settings-panel">
        <i class="settings-close ti-close"></i>
        <p class="settings-heading">Giao diện menu</p>
        <div class="sidebar-bg-options selected" id="sidebar-light-theme"><div class="img-ss rounded-circle bg-light border me-3"></div>Light</div>
        <div class="sidebar-bg-options" id="sidebar-dark-theme"><div class="img-ss rounded-circle bg-dark border me-3"></div>Dark</div>
        <p class="settings-heading mt-2">Giao diện thanh tiêu đề</p>
        <div class="color-tiles mx-0 px-4">
            <div class="tiles success"></div>
            <div class="tiles warning"></div>
            <div class="tiles danger"></div>
            <div class="tiles info"></div>
            <div class="tiles dark"></div>
            <div class="tiles default"></div>
        </div>
    </div>
</div>
<!-- partial:partials/_sidebar.html -->
<nav class="sidebar sidebar-offcanvas" id="sidebar">
    <ul class="nav">
        <li class="nav-item ${pageActive == 'activity' ? 'active' : ''}">
            <a class="nav-link" href="${pageContext.request.contextPath}/admin/activity">
                <i class="menu-icon mdi mdi-card-text-outline"></i>
                <span class="menu-title">Hoạt động</span>
            </a>
        </li>
        <li class="nav-item ${pageActive == 'volunteer' ? 'active' : ''}">
            <a class="nav-link" href="${pageContext.request.contextPath}/admin/volunteer">
                <i class="menu-icon mdi mdi-account-circle-outline"></i>
                <span class="menu-title">Tình nguyện viên</span>
            </a>
        </li>
        <li class="nav-item ${pageActive == 'thongke' ? 'active' : ''}">
            <a class="nav-link" data-bs-toggle="collapse" href="#charts" aria-expanded="false" aria-controls="charts">
                <i class="menu-icon mdi mdi-chart-line"></i>
                <span class="menu-title">Thống kê</span>
                <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="charts">
                <ul class="nav flex-column sub-menu">
                    <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/thongke/hoatdong">Hoạt động</a></li>
                </ul>
                <ul class="nav flex-column sub-menu">
                    <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/thongke/tinhnguyenvien">Tình nguyện viên</a></li>
                </ul>
            </div>
        </li>
    </ul>
</nav>
<!-- partial -->
