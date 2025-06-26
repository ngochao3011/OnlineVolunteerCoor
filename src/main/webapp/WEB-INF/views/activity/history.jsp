<%-- 
    Document   : history
    Created on : Jun 25, 2025, 7:37:44 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>

<div class="container">
    <div class="section-title text-center">
        <h4>Lịch Sử Thao Tác Hoạt Động</h4>
    </div>
    <div class="text-end mb-4">
        <a href="javascript:history.back()" class="btn btn-secondary">Quay lại</a>
    </div>

    <!-- Bảng lịch sử thao tác -->
    <c:if test="${not empty lichSuHoatDongs}">
        <div class="table-container">
            <table class="professional-table">
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Hành Động</th>
                        <th>Trước khi thay đổi</th>
                        <th>Sau khi thay đổi</th>
                        <th>Thời gian</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="lichSu" items="${lichSuHoatDongs}" varStatus="loop">
                        <c:set var="newLine" value="\n" />
                        <c:set var="beforeChange" value="${fn:replace(lichSu.truocKhiSua, newLine, '<br>')}" />
                        <c:set var="afterChange" value="${fn:replace(lichSu.sauKhiSua, newLine, '<br>')}" />
                        <tr>
                            <td>${loop.count}</td>
                            <td>${lichSu.hanhDong}</td>
                            <td style="white-space: pre-wrap;">${beforeChange}</td>
                            <td style="white-space: pre-wrap;">${afterChange}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty lichSu.thoiGian}">
                                        ${lichSu.thoiGian.year}-${lichSu.thoiGian.monthValue}-${lichSu.thoiGian.dayOfMonth} 
                                        ${lichSu.thoiGian.hour}:${lichSu.thoiGian.minute < 10 ? '0' : ''}${lichSu.thoiGian.minute}
                                    </c:when>
                                    <c:otherwise>
                                        Chưa có thời gian
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
    <c:if test="${empty lichSuHoatDongs}">
        <div class="alert alert-info text-center mt-5" role="alert">
            Không có lịch sử thao tác nào.
        </div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert alert-danger text-center mt-5" role="alert">
            ${error}
        </div>
    </c:if>
</div>

<style>
    .section-title {
        padding: 20px 0;
    }
    .table-container {
        overflow-x: auto;
        margin-bottom: 40px;
    }
    .professional-table {
        width: 100%;
        border-collapse: separate;
        border-spacing: 0;
        background-color: #ffffff;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
        overflow: hidden;
    }
    .professional-table th,
    .professional-table td {
        padding: 12px 15px;
        text-align: left;
        border-bottom: 1px solid #e8eef4;
    }
    .professional-table th {
        background-color: #2c3e50;
        color: #ffffff;
        font-weight: 600;
        text-transform: uppercase;
        letter-spacing: 0.5px;
    }
    .professional-table tr {
        transition: background-color 0.3s ease;
    }
    .professional-table tr:hover {
        background-color: #f5f7fa;
    }
    .professional-table td {
        word-wrap: break-word;
        color: #34495e;
    }
    .professional-table td[style*="white-space: pre-wrap"] {
        max-width: 300px;
    }
    .alert {
        padding: 15px;
    }
</style>