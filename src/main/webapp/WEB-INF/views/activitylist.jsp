<%-- 
    Document   : activitylist
    Created on : Jun 8, 2025, 2:48:28 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Activity List</title>

        <!-- CSS FILES -->
        <link href="${pageContext.request.contextPath}/src/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-homepage.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-navbar.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-activitylist.css" rel="stylesheet">

        <!-- Thêm CSS tùy chỉnh -->
        <style>
            .blog-item {
                border: 2px solid #ccc; /* Viền màu xám, độ dày 2px */
                border-radius: 6px; /* Góc bo tròn */
                overflow: hidden; /* Đảm bảo nội dung không tràn ra ngoài viền */
                box-shadow: 0 2px 4px rgba(0,0,0,0.1); /* Thêm bóng nhẹ */
            }
            .pagination {
                justify-content: center;
                margin-top: 20px;
            }
            .pagination .page-item.active .page-link {
                background-color: #007bff;
                border-color: #007bff;
                color: white;
            }
            .pagination .page-link {
                color: #007bff;
            }
            .pagination .page-link:hover {
                background-color: #e9ecef;
            }
        </style>

    </head>
    <body>

        <jsp:include page="layout/header.jsp" />
        <jsp:include page="layout/navbar.jsp" />

        <section class="blog-section spad">
            <div class="container">
                <div class="section-title text-center">
                    <h2>Danh Sách Hoạt Động Tình Nguyện</h2>
                    <p>Cùng tham gia các hoạt động ý nghĩa cho cộng đồng</p>
                </div>
                <div class="row">
                    <div class="col-12 mb-4 text-end">
                        <a href="${pageContext.request.contextPath}/activity/add" class="btn btn-primary">Thêm Hoạt Động Mới</a>
                    </div>
                    <c:forEach var="suKien" items="${danhSachSuKien}">
                        <div class="col-lg-4 col-md-6 mb-4">
                            <div class="blog-item">
                                <div class="bi-thumb">
                                    <img src="${pageContext.request.contextPath}/src/images/volunteer-4.png" alt="${suKien.tenSuKien}" style="width:100%; height:200px; object-fit:cover;">
                                </div>
                                <div class="bi-content">
                                    <h5><a href="${pageContext.request.contextPath}/activity/edit/${suKien.maSuKien}">${suKien.tenSuKien}</a></h5>
                                    <p>
                                        <strong>Thời gian:</strong> ${suKien.thoiGianBatDau} - ${suKien.thoiGianKetThuc}<br/>
                                        <strong>Địa điểm:</strong> ${suKien.diaDiem}<br/>
                                        <strong>Trạng thái:</strong> ${suKien.trangThai}
                                    </p>
                                    <a href="${pageContext.request.contextPath}/activity/edit/${suKien.maSuKien}" class="btn btn-sm btn-outline-primary">Sửa</a>
                                    <a href="${pageContext.request.contextPath}/activity/delete/${suKien.maSuKien}" class="btn btn-sm btn-outline-danger" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <c:if test="${empty danhSachSuKien}">
                        <p class="text-center mt-4">Hiện chưa có hoạt động nào.</p>
                    </c:if>
                </div>
                <c:if test="${empty danhSachSuKien}">
                    <p class="text-center mt-4">Hiện chưa có hoạt động nào.</p>
                </c:if>
            </div>

            <!-- Phân trang -->
            <c:if test="${not empty danhSachSuKien}">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <!-- Nút Previous -->
                        <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/activity?page=${currentPage - 1}" aria-label="Previous">
                                <span aria-hidden="true">«</span>
                            </a>
                        </li>
                        <!-- Các trang -->
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/activity?page=${i}">${i}</a>
                            </li>
                        </c:forEach>
                        <!-- Nút Next -->
                        <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/activity?page=${currentPage + 1}" aria-label="Next">
                                <span aria-hidden="true">»</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </c:if>

        </section>

        <jsp:include page="layout/footer.jsp" />
    </body>
</html>

