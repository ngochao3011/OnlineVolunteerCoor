<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Chi tiết Hoạt động - ${hoatDong.tenHoatDong}</title>

        <link href="${pageContext.request.contextPath}/src/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-homepage.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-navbar.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <style>
            .details-card {
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                border: none;
            }
            .details-card-header {
                background-color: #0057b8;
                color: white;
                padding: 1.5rem;
            }
            .details-card-body .info-item {
                display: flex;
                align-items: flex-start;
                margin-bottom: 1rem;
                font-size: 1.1rem;
            }
            .details-card-body .info-item i {
                width: 30px;
                text-align: center;
                margin-top: 4px;
                color: #0057b8;
            }
        </style>
    </head>
    <body>
        <%-- Nhúng Header và Navbar --%>
        <jsp:include page="../layout/header.jsp" />
        <jsp:include page="../layout/navbar.jsp" />

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
                            <img src="${pageContext.request.contextPath}/src/images/activity-details-placeholder.jpg" class="img-fluid rounded shadow" alt="Hình ảnh hoạt động">
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

        <%-- Nhúng Footer và các file JS --%>
        <%@ include file="../layout/footer.jsp" %>
    </body>
</html>