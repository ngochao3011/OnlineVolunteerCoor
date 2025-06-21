<%-- 
    Document   : activitylist
    Created on : Jun 8, 2025, 2:48:28 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<section class="blog-section spad">
    <div class="container">
        <div class="section-title text-center">
            <h4>Danh Sách Hoạt Động Tình Nguyện</h4>
        </div>
        <div class="row">
            <div class="col-12 mb-4 text-end">
                <a href="${pageContext.request.contextPath}/activity/add" class="btn btn-primary">Thêm Hoạt Động Mới</a>
            </div>
            <c:forEach var="hoatDong" items="${danhSachHoatDong}">
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="blog-item">
                        <div class="bi-thumb">
                            <img src="${pageContext.request.contextPath}/src/images/volunteer-4.png" alt="${hoatDong.tenHoatDong}" style="width:100%; height:200px; object-fit:cover;">
                        </div>
                        <div class="bi-content">
                            <h5><a href="${pageContext.request.contextPath}/activity/edit/${hoatDong.maHoatDong}">${hoatDong.tenHoatDong}</a></h5>
                            <p>
                                <strong>Thời gian:</strong> ${hoatDong.getThoiGianBatDauFormatted()} - ${hoatDong.getThoiGianKetThucFormatted()}<br/>
                                <strong>Địa điểm:</strong> ${hoatDong.diaDiem}<br/>
                                <strong>Trạng thái:</strong> ${hoatDong.trangThai}
                            </p>
                            <a href="${pageContext.request.contextPath}/activity/edit/${hoatDong.maHoatDong}" class="btn btn-sm btn-outline-primary">Sửa</a>
                            <a href="${pageContext.request.contextPath}/activity/delete/${hoatDong.maHoatDong}" class="btn btn-sm btn-outline-danger" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty danhSachHoatDong}">
                <p class="text-center mt-4">Hiện chưa có hoạt động nào.</p>
            </c:if>
        </div>
        <c:if test="${empty danhSachHoatDong}">
            <p class="text-center mt-4">Hiện chưa có hoạt động nào.</p>
        </c:if>
    </div>

    <!-- Phân trang -->
    <c:if test="${not empty danhSachHoatDong}">
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