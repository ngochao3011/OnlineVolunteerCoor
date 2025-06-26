<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<main class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-10">
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${successMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
        </div>
    </div>
    <div class="card details-card">
        <div class="card-header details-card-header">
            <h1 class="mb-0">${hoatDong.tenHoatDong}</h1>
        </div>
        <div class="card-body p-4 p-md-5">
            <div class="row">
                <div class="col-lg-7">
                    <h4 class="mb-3">Thông tin chi tiết</h4>
                    <div class="info-item">
                        <i class="fas fa-calendar-alt"></i>
                        <div>
                            <strong>Thời gian:</strong>
                            ${hoatDong.getThoiGianBatDauFormatted()} - ${hoatDong.getThoiGianKetThucFormatted()}
                        </div>
                    </div>
                    <div class="info-item">
                        <i class="fas fa-map-marker-alt"></i>
                        <div><strong>Địa điểm:</strong> ${hoatDong.diaDiem}</div>
                    </div>
                    <div class="info-item">
                        <i class="fas fa-info-circle"></i>
                        <div>
                            <strong>Trạng thái:</strong> 
                            <span class="badge fs-6 ${hoatDong.trangThai == 'Đang hoạt động' ? 'bg-success' : (hoatDong.trangThai == 'Sắp diễn ra' ? 'bg-warning' : 'bg-secondary')}">
                                ${hoatDong.trangThai}
                            </span>
                        </div>
                    </div>
                    <hr class="my-4">
                    <h4 class="mb-3">Mô tả hoạt động</h4>
                    <p>${hoatDong.moTa}</p>
                </div>
                <div class="col-lg-5 mt-4 mt-lg-0">
                    <img src="https://mir-s3-cdn-cf.behance.net/projects/404/0967f4197995765.Y3JvcCwxNTM0LDEyMDAsMzAwLDA.png" class="card-img-top" alt="Ảnh hoạt động">
                </div>
            </div>
        </div>
        <div class="card-footer bg-light p-3 text-center">
            <a href="${pageContext.request.contextPath}/activity" class="btn btn-outline-secondary me-2">
                <i class="fas fa-arrow-left me-2"></i> Quay lại danh sách
            </a>
            <c:if test="${coTheDanhGia}">
                <a href="${pageContext.request.contextPath}/review/form?maHoatDong=${hoatDong.maHoatDong}" class="btn btn-warning">
                    <i class="fas fa-star me-2"></i> Viết đánh giá
                </a>
            </c:if>
            <c:if test="${daDanhGia}">
                <button class="btn btn-success" disabled><i class="fas fa-check-circle me-2"></i> Đã gửi đánh giá</button>
            </c:if>

        </div>
    </div>
</main>
