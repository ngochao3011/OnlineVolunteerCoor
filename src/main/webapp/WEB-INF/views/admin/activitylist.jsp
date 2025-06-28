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
        <!-- Hiển thị thông báo -->
        <c:if test="${not empty successMessage}">
            <div id="successAlert" class="alert alert-success alert-dismissible fade show" role="alert">
                ${successMessage}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div id="errorAlert" class="alert alert-danger alert-dismissible fade show" role="alert">
                ${errorMessage}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>

        <div class="section-title text-center">
            <h4>Danh Sách Hoạt Động Tình Nguyện</h4>
        </div>
        <div class="row">
            <div class="col-12">
                <div class="action-buttons">
                    <div class="left-buttons">
                        <a href="${pageContext.request.contextPath}/admin/activity/add" class="btn btn-primary">Thêm Hoạt Động Mới</a>
                        <a href="${pageContext.request.contextPath}/admin/activity/history" class="btn btn-outline-info"> <i class="bi bi-clock-history"></i> Lịch sử</a>
                    </div>
                    <div class="right-button">
                        <a href="${pageContext.request.contextPath}/admin/activity/approve" class="btn btn-approve">Duyệt đăng ký</a>
                    </div>
                </div>
            </div>
            <c:forEach var="hoatDong" items="${danhSachHoatDong}">
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="blog-item">
                        <div class="bi-thumb">
                            <img src="https://mir-s3-cdn-cf.behance.net/projects/404/0967f4197995765.Y3JvcCwxNTM0LDEyMDAsMzAwLDA.png" alt="${hoatDong.tenHoatDong}" style="width:100%; height:200px; object-fit:cover;">
                        </div>
                        <div class="bi-content">
                            <h5><a href="${pageContext.request.contextPath}/admin/activity/edit/${hoatDong.maHoatDong}">${hoatDong.tenHoatDong}</a></h5>
                            <p>
                                <strong>Thời gian:</strong> ${hoatDong.getThoiGianBatDauFormatted()} - ${hoatDong.getThoiGianKetThucFormatted()}<br/>
                                <strong>Địa điểm:</strong> ${hoatDong.diaDiem}<br/>
                                <strong>Trạng thái:</strong> ${hoatDong.trangThai}
                            </p>
                            <a href="${pageContext.request.contextPath}/admin/activity/edit/${hoatDong.maHoatDong}" class="btn btn-sm btn-outline-primary">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/activity/delete?maHoatDong=${hoatDong.maHoatDong}" class="btn btn-sm btn-outline-danger" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
                            <a href="${pageContext.request.contextPath}/admin/activity/checkin/${hoatDong.maHoatDong}" class="btn btn-sm btn-outline-primary">Điểm danh</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty danhSachHoatDong}">
                <p class="text-center mt-4">Hiện chưa có hoạt động nào.</p>
            </c:if>
        </div>

        <!-- Phân trang -->
        <c:if test="${not empty danhSachHoatDong}">
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/activity?page=${currentPage - 1}" aria-label="Previous">
                            <span aria-hidden="true">«</span>
                        </a>
                    </li>
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/admin/activity?page=${i}">${i}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/activity?page=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">»</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </c:if>
    </div>
</section>

<style>
    .btn-approve {
        background-color: #28a745;
        border-color: #28a745;
        color: white;
    }
    .btn-approve:hover {
        background-color: #218838;
        border-color: #1e7e34;
    }
    .action-buttons {
        display: flex;
        justify-content: space-between;
        align-items: center;
        width: 100%;
        margin-bottom: 20px;
    }
    .left-buttons {
        display: flex;
        gap: 10px;
    }
    .right-button {
        margin-left: auto;
    }
    .btn-outline-info i {
        margin-right: 5px;
    }
    .alert {
        margin-bottom: 20px;
    }
</style>

<script>
    // Tự động ẩn thông báo sau 5 giây
    setTimeout(function() {
        var successAlert = document.getElementById('successAlert');
        var errorAlert = document.getElementById('errorAlert');
        if (successAlert) {
            successAlert.classList.remove('show');
            setTimeout(() => successAlert.remove(), 500);
        }
        if (errorAlert) {
            errorAlert.classList.remove('show');
            setTimeout(() => errorAlert.remove(), 500);
        }
    }, 5000);
</script>
