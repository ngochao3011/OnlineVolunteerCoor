<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<div class="container-history">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4 class="d-flex align-items-center mb-0">
            <i class="fas fa-clock-rotate-left me-2 text-primary"></i> Lịch sử thao tác
        </h4>
        <a href="${pageContext.request.contextPath}/admin/volunteer" class="btn btn-outline-primary">
            <i class="bi bi-arrow-left-circle"></i> Quay lại danh sách tình nguyện viên
        </a>
    </div>
    <c:if test="${empty lichSuList}">
        <div class="alert alert-warning mt-3">Chưa có thao tác nào được ghi nhận.</div>
    </c:if>

    <c:if test="${not empty lichSuList}">
        <div class="table-responsive mt-4">
            <table class="table table-hover table-bordered text-center align-middle">
                <thead class="table-light">
                    <tr>
                        <th>#</th>
                        <th>Mã Thành Viên</th>
                        <th>Hành động</th>
                        <th>Trước khi sửa</th>
                        <th>Sau khi sửa</th>
                        <th>Thời gian</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="ls" items="${lichSuList}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${ls.maThanhVien}</td>
                            <td>${ls.hanhDong}</td>
                            <td><c:out value="${ls.truocKhiSua}" /></td>
                            <td><c:out value="${ls.sauKhiSua}" /></td>
                            <td><fmt:formatDate value="${ls.thoiGian}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>