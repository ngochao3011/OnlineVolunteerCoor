<%-- 
    Document   : activitylist
    Created on : Jun 8, 2025, 2:48:28 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Activity List</title>
    
    <!-- CSS FILES -->
    <link href="${pageContext.request.contextPath}/src/css/bootstrap.min.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/src/css/bootstrap-icons.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/src/css/template-homepage.css" rel="stylesheet">
    
    <link href="${pageContext.request.contextPath}/src/css/template-navbar.css" rel="stylesheet">
    
    <link href="${pageContext.request.contextPath}/src/css/template-activitylist.css" rel="stylesheet">
</head>
<body>

    <jsp:include page="layout/header.jsp" />
    
    <jsp:include page="layout/navbar.jsp" />

    <section class="blog-section spad">
        <div class="container-title">
            <div class="section-title text-center">
                <h3>Danh sách Hoạt động</h3>
            </div>
            <div class="container-event">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/activity/add">Add New Activity</a>
                <table class="event-table">
                    <tr>
                        <th>Event ID</th>
                        <th>Event Name</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>Location</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    <c:forEach var="suKien" items="${danhSachSuKien}">
                        <tr>
                            <td>${suKien.maSuKien}</td>
                            <td>${suKien.tenSuKien}</td>
                            <td>${suKien.thoiGianBatDau}</td>
                            <td>${suKien.thoiGianKetThuc}</td>
                            <td>${suKien.diaDiem}</td>
                            <td>${suKien.trangThai}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/activity/edit/${suKien.maSuKien}">Edit</a>
                                <a href="${pageContext.request.contextPath}/activity/delete/${suKien.maSuKien}" onclick="return confirm('Are you sure?')">Delete</a>
                                <form action="${pageContext.request.contextPath}/activity/update" method="post" style="display:inline;">
                                    <input type="hidden" name="maSuKien" value="${suKien.maSuKien}">
                                    <select name="trangThai" onchange="this.form.submit()">
                                        <option value="Sắp tới" ${suKien.trangThai == 'Sắp tới' ? 'selected' : ''}>Upcoming</option>
                                        <option value="Đang diễn ra" ${suKien.trangThai == 'Đang diễn ra' ? 'selected' : ''}>In Progress</option>
                                        <option value="Đã hoàn thành" ${suKien.trangThai == 'Đã hoàn thành' ? 'selected' : ''}>Completed</option>
                                    </select>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <c:if test="${empty danhSachSuKien}">
                <p class="text-center mt-4">Hiện chưa có hoạt động nào.</p>
            </c:if>
        </div>
    </section>

    <jsp:include page="layout/footer.jsp" />
</body>
</html>

