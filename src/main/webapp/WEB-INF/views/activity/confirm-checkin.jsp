<%-- 
    Document   : confirm-checkin
    Created on : Jun 24, 2025, 2:17:49 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div class="container mt-5">
    <h2 class="mb-4 text-center">Xác nhận tham gia hoạt động</h2>

    <div class="card shadow-sm p-4 mb-5">
        <div class="mb-3">
            <strong>Tên hoạt động:</strong> ${hoatDong.tenHoatDong}
        </div>
        <div class="mb-3">
            <strong>Mô tả:</strong> ${hoatDong.moTa}
        </div>
        <div class="mb-3">
            <strong>Thời gian:</strong>
                ${hoatDong.thoiGianBatDau} - ${hoatDong.thoiGianKetThuc}
        </div>
        <div class="mb-4">
            <strong>Địa điểm:</strong> ${hoatDong.diaDiem}
        </div>

        <form method="post" action="${pageContext.request.contextPath}/activity/confirm-checkin" class="d-flex gap-2">
            <input type="hidden" name="maHoatDong" value="${hoatDong.maHoatDong}" />
            <button type="submit" class="btn btn-success">Xác nhận</button>
            <a href="${pageContext.request.contextPath}/activity" class="btn btn-secondary">Hủy</a>
        </form>
    </div>
</div>        
