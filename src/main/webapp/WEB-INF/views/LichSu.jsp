<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Lịch sử thao tác</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="${pageContext.request.contextPath}/src/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-homepage.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-navbar.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            body {
                background-color: #e3f2fd;
            }
            .container-history {
                background-color: #ffffff;
                padding: 20px 30px;
                box-shadow: 0 0 20px rgba(0,0,0,0.1);
            }
            h4 {
                color: #1976d2;
            }
            .table thead {
                background-color: #bbdefb;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/WEB-INF/views/layout/header.jsp" />
        <jsp:include page="/WEB-INF/views/layout/navbar.jsp" />
        <div class="container-history">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="d-flex align-items-center mb-0">
                    <i class="fas fa-clock-rotate-left me-2 text-primary"></i> Lịch sử thao tác
                </h4>
                <a href="${pageContext.request.contextPath}/volunteer" class="btn btn-outline-primary">
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
                                <th>Mã TNV</th>
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
                                    <td>${ls.maTNV}</td>
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

        <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
    </body>
</html>
