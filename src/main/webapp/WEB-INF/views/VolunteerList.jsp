<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Danh sách Tình nguyện viên</title>
        
        <!-- CSS FILES -->
        <link href="${pageContext.request.contextPath}/src/css/bootstrap.min.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath}/src/css/bootstrap-icons.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath}/src/css/template-homepage.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath}/src/css/template-navbar.css" rel="stylesheet">
        
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        
        <!-- CSS CUSTOME -->
        <style>
            body {
                background-color: #e3f2fd; /* xanh dương nhạt */
            }
            .container-volunteer {
                background-color: #ffffff;
                padding: 15px;
                padding-left: 30px;
                padding-right: 30px;
                box-shadow: 0 0 20px rgba(0,0,0,0.1);
            }
            h4 {
                color: #1976d2;
            }
            .export-btn {
                margin-left: 0px;
            }
            .table thead {
                background-color: #bbdefb;
            }
            .badge-active {
                background-color: #4caf50;
            }
            .badge-inactive {
                background-color: #9e9e9e;
            }
        </style>
        <script>
            function validateSearch() {
                const input = document.getElementById('keyword').value.trim();
                if (input === '') {
                    alert('Vui lòng nhập tên để tìm kiếm.');
                    return false;
                }
                // Bỏ kiểm tra không nhập số để cho phép tên chứa số
                return true;
            }
        </script>
        
    </head>
    <body>
        <jsp:include page="layout/header.jsp" />

        <jsp:include page="layout/navbar.jsp" />
        
        <div class="container-volunteer">
            <h4 class="d-flex align-items-center">
                <i class="fas fa-users me-2 text-primary"></i> Danh sách Tình nguyện viên
            </h4>

            <!-- Hiển thị thông báo -->
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success mt-3">${successMessage}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-warning mt-3">${errorMessage}</div>
            </c:if>

            <!-- Nút Xuất CSV -->
            <div class="row mb-1">
                <div class="col col-md-8">
                    <a href="${pageContext.request.contextPath}/volunteer/export" class="btn btn-success export-btn">
                        <i class="fa fa-file-csv"></i> Xuất CSV
                    </a>
                </div>
                <div class="col-md-4 text-end">
                    <!-- Form tìm kiếm -->
                    <form class="d-flex" id="searchForm" action="${pageContext.request.contextPath}/volunteer/search" method="get" onsubmit="return validateSearch();">
                        <div class="input-group mb-3">
                            <input type="text" id="keyword" name="keyword" value="${searchKeyword}" 
                                   placeholder="Tìm kiếm tình nguyện viên" class="form-control">
                            <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                        </div>
                    </form>
                </div>
            </div>

            <script>
                const keywordInput = document.getElementById('keyword');
                const searchForm = document.getElementById('searchForm');
                keywordInput.addEventListener('input', function () {
                    if (this.value.trim() === '') {
                        searchForm.submit();
                    }
                });
            </script>

            <!-- Bảng danh sách -->
            <div class="table-responsive">
                <table class="table table-hover table-bordered text-center align-middle">
                    <thead class="table-light">
                        <tr>
                            <th>#</th>
                            <th>Mã TNV</th>
                            <th>Họ tên</th>
                            <th>SĐT</th>
                            <th>Địa chỉ</th>
                            <th>Trạng thái</th>
                            <th>Ngày ĐK</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty volunteers}">
                                <c:forEach var="v" items="${volunteers}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${v.maTNV}</td>
                                        <td>${v.hoTen}</td>
                                        <td>${v.sdtDienThoai}</td>
                                        <td>${v.diaChi}</td>
                                        <td>
                                            <span class="badge ${v.trangThai == 'Hoạt động' ? 'badge-active' : 'badge-inactive'}">
                                                ${v.trangThai}
                                            </span>
                                        </td>
                                        <td>
                                            <c:if test="${v.ngayDangKy != null}">
                                                <fmt:formatDate value="${v.ngayDangKy}" pattern="dd/MM/yyyy" />
                                            </c:if>
                                        </td>
                                        <td>            
                                            <form action="${pageContext.request.contextPath}/volunteer/delete" method="post" style="display:inline;">
                                                <input type="hidden" name="maTNV" value="${v.maTNV}"/>
                                                <button type="submit" class="btn btn-sm btn-danger"
                                                        onclick="return confirm('Xác nhận xóa tình nguyện viên này?');">
                                                    <i class="fa fa-trash"></i> Xóa
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="8" class="text-center text-muted">Không có tình nguyện viên nào.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
        <%@ include file="layout/footer.jsp" %>
    </body>
</html>