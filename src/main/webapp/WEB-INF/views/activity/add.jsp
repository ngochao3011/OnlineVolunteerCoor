<%-- 
    Document   : add
    Created on : Jun 9, 2025, 8:13:29 PM
    Author     : HP
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<section class="blog-section spad">
    <div class="container">
        <div class="section-title text-center">
            <h2>Thêm Hoạt Động Mới</h2>
        </div>
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </c:if>
                <form action="${pageContext.request.contextPath}/activity/add" method="post" class="p-4 border rounded">
                    <div class="mb-3">
                        <label for="tenHoatDong" class="form-label">Tên Hoạt Động:</label>
                        <input type="text" class="form-control" id="tenHoatDong" name="tenHoatDong" required>
                    </div>
                    <div class="mb-3">
                        <label for="moTa" class="form-label">Mô Tả:</label>
                        <textarea class="form-control" id="moTa" name="moTa" rows="4" placeholder="Nhập mô tả chi tiết về hoạt động..."></textarea>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label for="thoiGianBatDau" class="form-label">Thời Gian Bắt Đầu:</label>
                                <input type="datetime-local" class="form-control" id="thoiGianBatDau" name="thoiGianBatDau" required>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label for="thoiGianKetThuc" class="form-label">Thời Gian Kết Thúc:</label>
                                <input type="datetime-local" class="form-control" id="thoiGianKetThuc" name="thoiGianKetThuc" required>
                            </div>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="diaDiem" class="form-label">Địa Điểm:</label>
                        <input type="text" class="form-control" id="diaDiem" name="diaDiem" placeholder="Nhập địa điểm tổ chức hoạt động" required>
                    </div>
                    <div class="mb-3">
                        <label for="trangThai" class="form-label">Trạng Thái:</label>
                        <select class="form-select" id="trangThai" name="trangThai" required>
                            <option value="">Chọn trạng thái</option>
                            <option value="Sắp diễn ra">Sắp diễn ra</option>
                            <option value="Đang hoạt động">Đang hoạt động</option>
                            <option value="Đã kết thúc">Đã kết thúc</option>
                        </select>
                    </div>
                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-plus me-2"></i>Thêm Hoạt Động
                        </button>
                        <a href="${pageContext.request.contextPath}/activity" class="btn btn-secondary">
                            <i class="fas fa-arrow-left me-2"></i>Quay Lại
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>

<script>
    // Validation script
    document.getElementById('thoiGianKetThuc').addEventListener('change', function () {
        const startTime = document.getElementById('thoiGianBatDau').value;
        const endTime = this.value;

        if (startTime && endTime && startTime >= endTime) {
            alert('Thời gian kết thúc phải sau thời gian bắt đầu!');
            this.value = '';
        }
    });
</script>
