<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Danh sách Tình nguyện viên</title>
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
            .container-volunteer {
                background-color: #ffffff;
                padding: 15px 30px;
                box-shadow: 0 0 20px rgba(0,0,0,0.1);
            }
            h4 {
                color: #1976d2;
            }
            .export-btn {
                margin-left: 0;
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
            .pagination {
                justify-content: center;
                margin-top: 20px;
            }
            .page-item.active .page-link {
                background-color: #1976d2;
                border-color: #1976d2;
            }
            .page-link {
                color: #1976d2;
            }
            .page-link:hover {
                color: #ffffff;
                background-color: #1565c0;
                border-color: #1565c0;
            }
        </style>
        <script>
            function validateSearch() {
                const input = document.getElementById('keyword').value.trim();
                if (input === '') {
                    alert('Vui lòng nhập tên để tìm kiếm.');
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
        <jsp:include page="/WEB-INF/views/layout/header.jsp" />
        <jsp:include page="/WEB-INF/views/layout/navbar.jsp" />

        <div class="container-volunteer">
            <h4 class="d-flex align-items-center">
                <i class="fas fa-users me-2 text-primary"></i> Danh sách Tình nguyện viên
            </h4>

            <c:if test="${not empty successMessage}">
                <div class="alert alert-success mt-3">${successMessage}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-warning mt-3">${errorMessage}</div>
            </c:if>

            <div class="row mb-3">
                <div class="col-md-8">
                    <a href="${pageContext.request.contextPath}/volunteer/export" class="btn btn-success export-btn">
                        <i class="fa fa-file-csv"></i> Xuất CSV
                    </a>
                    <a href="${pageContext.request.contextPath}/lichsu" class="btn btn-secondary ms-2">
                        <i class="fa fa-history"></i> Lịch sử 
                    </a>
                </div>
                <div class="col-md-4 text-end">
                    <form class="d-flex" id="searchForm" action="${pageContext.request.contextPath}/volunteer/search" method="get" onsubmit="return validateSearch();">
                        <div class="input-group">
                            <input type="text" id="keyword" name="keyword" value="${searchKeyword}" 
                                   placeholder="Tìm kiếm tình nguyện viên" class="form-control">
                            <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                        </div>
                    </form>
                </div>
            </div>

            <script>
                const keywordInput = document.getElementById('keyword');
                keywordInput.addEventListener('input', function () {
                    if (this.value.trim() === '' && '${searchKeyword}' !== '') {
                        window.location.href = '${pageContext.request.contextPath}/volunteer';
                    }
                });
            </script>

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
                                        <td>${loop.index + 1 + (currentPage - 1) * pageSize}</td>
                                        <td>${v.maTNV}</td>
                                        <td>${fn:escapeXml(v.hoTen)}</td>
                                        <td>${fn:escapeXml(v.sdtDienThoai)}</td>
                                        <td>${fn:escapeXml(v.diaChi)}</td>
                                        <td>
                                            <span class="badge ${v.trangThai == 'Hoạt động' ? 'badge-active' : 'badge-inactive'}">
                                                ${fn:escapeXml(v.trangThai)}
                                            </span>
                                        </td>
                                        <td>
                                            <c:if test="${not empty v.ngayDangKy}">
                                                <fmt:formatDate value="${v.ngayDangKy}" pattern="dd/MM/yyyy" />
                                            </c:if>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/volunteer/edit/${v.maTNV}" class="btn btn-sm btn-warning">
                                                <i class="fa fa-pen"></i> Sửa
                                            </a>
                                            <form action="${pageContext.request.contextPath}/volunteer/delete" method="post" style="display:inline;">
                                                <input type="hidden" name="maTNV" value="${v.maTNV}"/>
                                                <input type="hidden" name="page" value="${currentPage}"/>
                                                <input type="hidden" name="keyword" value="${searchKeyword}"/>
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
                                    <td colspan="8" class="text-center text-muted">
                                        <c:choose>
                                            <c:when test="${not empty searchKeyword}">
                                                Không tìm thấy tình nguyện viên với từ khóa "${fn:escapeXml(searchKeyword)}".
                                            </c:when>
                                            <c:otherwise>
                                                Không có tình nguyện viên nào.
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

            <c:if test="${not empty volunteers}">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/volunteer?page=${currentPage - 1}&keyword=${searchKeyword}" aria-label="Previous">
                                <span aria-hidden="true">«</span>
                            </a>
                        </li>
                        <c:set var="startPage" value="${currentPage - 2}"/>
                        <c:set var="endPage" value="${currentPage + 2}"/>
                        <c:if test="${startPage < 1}">
                            <c:set var="startPage" value="1"/>
                            <c:set var="endPage" value="${startPage + 4}"/>
                        </c:if>
                        <c:if test="${endPage > totalPages}">
                            <c:set var="endPage" value="${totalPages}"/>
                            <c:set var="startPage" value="${endPage - 4 > 0 ? endPage - 4 : 1}"/>
                        </c:if>
                        <c:if test="${startPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/volunteer?page=1&keyword=${searchKeyword}">1</a>
                            </li>
                            <c:if test="${startPage > 2}">
                                <li class="page-item disabled"><span class="page-link">...</span></li>
                                </c:if>
                            </c:if>
                            <c:forEach begin="${startPage}" end="${endPage}" var="i">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/volunteer?page=${i}&keyword=${searchKeyword}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${endPage < totalPages}">
                            <c:if test="${endPage < totalPages - 1}">
                                <li class="page-item disabled"><span class="page-link">...</span></li>
                                </c:if>
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/volunteer?page=${totalPages}&keyword=${searchKeyword}">${totalPages}</a>
                            </li>
                        </c:if>
                        <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/volunteer?page=${currentPage + 1}&keyword=${searchKeyword}" aria-label="Next">
                                <span aria-hidden="true">»</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </c:if>
        </div>

        <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
    </body>
</html>
