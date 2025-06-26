<%-- 
    Document   : edit
    Created on : Jun 9, 2025, 8:13:40 PM
    Author     : HP
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<section class="blog-section spad">
    <div class="container">
        <div class="section-title text-center">
            <h2>Chỉnh Sửa Hoạt Động</h2>
        </div>
        <div class="row justify-content-center">
            <div class="col-lg-6">
                <form action="${pageContext.request.contextPath}/activity/edit" method="post" class="p-4 border rounded">
                    <input type="hidden" name="maHoatDong" value="${hoatDong.maHoatDong}">
                    <div class="mb-3">
                        <label for="tenHoatDong" class="form-label">Tên Hoạt Động:</label>
                        <input type="text" class="form-control" id="tenHoatDong" name="tenHoatDong" value="${hoatDong.tenHoatDong}" required>
                    </div>
                    <div class="mb-3">
                        <label for="moTa" class="form-label">Mô Tả:</label>
                        <textarea class="form-control" id="moTa" name="moTa" rows="3">${hoatDong.moTa}</textarea>
                    </div>
                    <div class="mb-3">
                        <label for="thoiGianBatDau" class="form-label">Thời Gian Bắt Đầu:</label>
                        <input type="datetime-local" class="form-control" id="thoiGianBatDau" name="thoiGianBatDau" value="${hoatDong.thoiGianBatDau}" required>
                    </div>
                    <div class="mb-3">
                        <label for="thoiGianKetThuc" class="form-label">Thời Gian Kết Thúc:</label>
                        <input type="datetime-local" class="form-control" id="thoiGianKetThuc" name="thoiGianKetThuc" value="${hoatDong.thoiGianKetThuc}" required>
                    </div>
                    <div class="mb-3">
                        <label for="diaDiem" class="form-label">Địa Điểm:</label>
                        <input type="text" class="form-control" id="diaDiem" name="diaDiem" value="${hoatDong.diaDiem}" required>
                    </div>
                    <div class="mb-3">
                        <label for="trangThai" class="form-label">Trạng Thái:</label>
                        <select class="form-select" id="trangThai" name="trangThai" required>
                            <option value="Sắp diễn ra" ${hoatDong.trangThai == 'Sắp diễn ra' ? 'selected' : ''}>Upcoming</option>
                            <option value="Đang hoạt động" ${hoatDong.trangThai == 'Đang hoạt động' ? 'selected' : ''}>In Progress</option>
                            <option value="Đã kết thúc" ${hoatDong.trangThai == 'Đã kết thúc' ? 'selected' : ''}>Completed</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Cập Nhật</button>
                    <a href="${pageContext.request.contextPath}/activity" class="btn btn-secondary">Quay Lại</a>
                </form>
            </div>
        </div>
    </div>
</section>
