<%-- 
    Document   : checkin
    Created on : Jun 24, 2025, 1:38:01 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div style="text-align: center; padding: 30px;">
    <h3 style="color: #1e63af;">Quét mã QR để điểm danh hoạt động</h3>

    <img src="${pageContext.request.contextPath}${qrImage}" alt="QR Code" width="250" height="250" style="margin: 20px 0;" />

    <p>
        <a href="${qrLink}" >Link xác nhận điểm danh</a>
    </p>
</div>