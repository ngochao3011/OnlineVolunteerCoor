<%-- 
    Document   : edit
    Created on : Jun 9, 2025, 8:13:40 PM
    Author     : HP
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Chỉnh Sửa Hoạt Động</title>
        <!-- CSS FILES -->
        <link href="${pageContext.request.contextPath}/src/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-homepage.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-navbar.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-activitylist.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="../layout/header.jsp" />
        <jsp:include page="../layout/navbar.jsp" />

        <section class="blog-section spad">
            <div class="container">
                <div class="section-title text-center">
                    <h2>Chỉnh Sửa Hoạt Động</h2>
                </div>
                <div class="row justify-content-center">
                    <div class="col-lg-6">
                        <form action="${pageContext.request.contextPath}/activity/edit" method="post" class="p-4 border rounded">
                            <input type="hidden" name="maSuKien" value="${suKien.maSuKien}">
                            <div class="mb-3">
                                <label for="tenSuKien" class="form-label">Tên Hoạt Động:</label>
                                <input type="text" class="form-control" id="tenSuKien" name="tenSuKien" value="${suKien.tenSuKien}" required>
                            </div>
                            <div class="mb-3">
                                <label for="moTa" class="form-label">Mô Tả:</label>
                                <textarea class="form-control" id="moTa" name="moTa" rows="3">${suKien.moTa}</textarea>
                            </div>
                            <div class="mb-3">
                                <label for="thoiGianBatDau" class="form-label">Thời Gian Bắt Đầu:</label>
                                <input type="datetime-local" class="form-control" id="thoiGianBatDau" name="thoiGianBatDau" value="${suKien.thoiGianBatDau}" required>
                            </div>
                            <div class="mb-3">
                                <label for="thoiGianKetThuc" class="form-label">Thời Gian Kết Thúc:</label>
                                <input type="datetime-local" class="form-control" id="thoiGianKetThuc" name="thoiGianKetThuc" value="${suKien.thoiGianKetThuc}" required>
                            </div>
                            <div class="mb-3">
                                <label for="diaDiem" class="form-label">Địa Điểm:</label>
                                <input type="text" class="form-control" id="diaDiem" name="diaDiem" value="${suKien.diaDiem}" required>
                            </div>
                            <div class="mb-3">
                                <label for="trangThai" class="form-label">Trạng Thái:</label>
                                <select class="form-select" id="trangThai" name="trangThai" required>
                                    <option value="Sắp tới" ${suKien.trangThai == 'Sắp tới' ? 'selected' : ''}>Upcoming</option>
                                    <option value="Đang diễn ra" ${suKien.trangThai == 'Đang diễn ra' ? 'selected' : ''}>In Progress</option>
                                    <option value="Đã hoàn thành" ${suKien.trangThai == 'Đã hoàn thành' ? 'selected' : ''}>Completed</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">Cập Nhật</button>
                            <a href="${pageContext.request.contextPath}/activity" class="btn btn-secondary">Quay Lại</a>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="../layout/footer.jsp" />
    </body>
</html>
