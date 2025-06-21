<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Test Registration Buttons</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Test Registration Buttons</h2>
        
        <!-- Debug Info -->
        <div class="alert alert-info">
            <h5>Debug Information:</h5>
            <p><strong>Registered Event IDs:</strong> ${registeredEventIds}</p>
            <p><strong>User Email:</strong> ${sessionScope.user.email}</p>
            <p><strong>User Role:</strong> ${sessionScope.user.quyenHan}</p>
            <p><strong>User ID:</strong> ${sessionScope.user.maTaiKhoan}</p>
        </div>

        <!-- Test Activities -->
        <div class="row">
            <c:forEach var="hoatDong" items="${danhSachHoatDong}" varStatus="status">
                <c:if test="${status.index < 3}">
                    <div class="col-md-4 mb-3">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">${hoatDong.tenHoatDong}</h5>
                                <p class="card-text">ID: ${hoatDong.maHoatDong}</p>
                                <p class="card-text">Status: ${hoatDong.trangThai}</p>
                                
                                <!-- Debug for this activity -->
                                <div style="font-size: 10px; color: #666; margin-bottom: 10px;">
                                    <strong>Debug:</strong><br>
                                    Activity ID: ${hoatDong.maHoatDong}<br>
                                    Is in registeredEventIds: ${registeredEventIds.contains(hoatDong.maHoatDong)}<br>
                                    registeredEventIds not empty: ${not empty registeredEventIds}
                                </div>

                                <!-- Buttons -->
                                <c:choose>
                                    <c:when test="${not empty registeredEventIds and registeredEventIds.contains(hoatDong.maHoatDong)}">
                                        <form action="${pageContext.request.contextPath}/register/delete" method="post">
                                            <input type="hidden" name="maHoatDong" value="${hoatDong.maHoatDong}">
                                            <button type="submit" class="btn btn-danger">Hủy Đăng Ký</button>
                                        </form>
                                    </c:when>
                                    <c:when test="${hoatDong.trangThai == 'Sắp diễn ra' || hoatDong.trangThai == 'Đang hoạt động'}">
                                        <form action="${pageContext.request.contextPath}/register/add" method="post">
                                            <input type="hidden" name="maHoatDong" value="${hoatDong.maHoatDong}">
                                            <button type="submit" class="btn btn-primary">Đăng Ký Ngay</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn btn-secondary" disabled>Không thể đăng ký</button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>

        <!-- Manual Test -->
        <div class="mt-4">
            <h4>Manual Test</h4>
            <form action="${pageContext.request.contextPath}/register/add" method="post" class="d-inline">
                <input type="hidden" name="maHoatDong" value="151">
                <button type="submit" class="btn btn-success">Test Đăng Ký Activity 151</button>
            </form>
            
            <form action="${pageContext.request.contextPath}/register/delete" method="post" class="d-inline ms-2">
                <input type="hidden" name="maHoatDong" value="151">
                <button type="submit" class="btn btn-warning">Test Hủy Đăng Ký Activity 151</button>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 