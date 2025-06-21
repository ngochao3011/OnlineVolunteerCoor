<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
                    <div style="white-space: pre-line;">${errorMessage}</div>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
        </div>
    </div>
    <div class="text-center mb-5">
        <h2 class="display-5 fw-bold">Tham gia hoạt động cùng chúng tôi</h2>
        <p class="lead text-muted">Khám phá và đăng ký các sự kiện tình nguyện ý nghĩa.</p>
    </div>

    <div class="row justify-content-center mb-5">
        <div class="col-lg-10 col-12 mx-auto">
            <form action="${pageContext.request.contextPath}/activity" method="get" class="row g-3 justify-content-center align-items-center">
                <div class="col-md-4">
                    <input name="keyword" type="search" class="form-control" placeholder="Tìm theo tên hoạt động..." value="${keyword}">
                </div>
                <div class="col-md-3">
                    <input name="location" type="search" class="form-control" placeholder="Tìm theo địa điểm..." value="${location}">
                </div>
                <div class="col-md-3">
                    <select name="trangThai" class="form-select">
                        <option value="">Tất cả trạng thái</option>
                        <option value="Sắp diễn ra" ${trangThai == 'Sắp diễn ra' ? 'selected' : ''}>Sắp diễn ra</option>
                        <option value="Đang hoạt động" ${trangThai == 'Đang hoạt động' ? 'selected' : ''}>Đang hoạt động</option>
                        <option value="Đã kết thúc" ${trangThai == 'Đã kết thúc' ? 'selected' : ''}>Đã kết thúc</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary w-100">Tìm kiếm</button>
                </div>
            </form>
        </div>            
    </div>

    <div class="row">
        <c:if test="${empty danhSachHoatDong}">
            <div class="col-12 text-center"><p>Không tìm thấy hoạt động nào phù hợp.</p></div>
        </c:if>

        <c:forEach var="hoatDong" items="${danhSachHoatDong}">
            <div class="col-md-6 col-lg-4 mb-4">
                <div class="card card-activity h-100">
                    <img src="https://mir-s3-cdn-cf.behance.net/projects/404/0967f4197995765.Y3JvcCwxNTM0LDEyMDAsMzAwLDA.png" class="card-img-top" alt="Ảnh hoạt động">
                    <div class="card-body">
                        <div>
                            <h5 class="card-title">${hoatDong.tenHoatDong}</h5>
                            <p class="card-text mb-1"><i class="fas fa-map-marker-alt me-2 text-secondary"></i>${hoatDong.diaDiem}</p>
                            <p class="card-text"><i class="fas fa-clock me-2 text-secondary"></i>${hoatDong.getThoiGianBatDauFormatted()}</p>
                        </div>
                        <span class="badge ${hoatDong.trangThai == 'Đang hoạt động' ? 'bg-success' : (hoatDong.trangThai == 'Sắp diễn ra' ? 'bg-warning' : 'bg-secondary')} mb-3 align-self-start">
                            ${hoatDong.trangThai}
                        </span>
                    </div>
                    <div class="card-footer d-grid gap-2">
                        <a href="${pageContext.request.contextPath}/activity/details/${hoatDong.maHoatDong}" class="btn btn-outline-primary">Xem chi tiết</a>

                        <c:choose>
                            <%-- TH1: Nếu hoạt động này đã được đăng ký bởi người dùng hiện tại --%>
                            <c:when test="${registeredEventIds.contains(hoatDong.maHoatDong)}">
                                <form action="${pageContext.request.contextPath}/register/delete" method="post" class="d-grid">
                                    <input type="hidden" name="maHoatDong" value="${hoatDong.maHoatDong}">
                                    <button type="submit" class="btn btn-danger fw-bold" onclick="return confirm('Bạn có chắc chắn muốn hủy đăng ký hoạt động này?');">Hủy Đăng Ký</button>
                                </form>
                            </c:when>

                            <%-- TH2: Nếu hoạt động "Sắp diễn ra" và chưa được đăng ký --%>
                            <c:when test="${hoatDong.trangThai == 'Sắp diễn ra'}">
                                <form action="${pageContext.request.contextPath}/register/add" method="post" class="d-grid">
                                    <input type="hidden" name="maHoatDong" value="${hoatDong.maHoatDong}">
                                    <button type="submit" class="btn btn-primary fw-bold">Đăng Ký Ngay</button>
                                </form>
                            </c:when>

                            <%-- TH3: Các trường hợp còn lại (đang diễn ra, đã kết thúc) -> Vô hiệu hóa nút --%>
                            <c:otherwise>
                                <button type="button" class="btn btn-secondary" disabled>Không thể đăng ký</button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${totalPages > 1}">
        <div class="mt-5">
            <jsp:include page="layout/pagination.jsp" />
        </div>
    </c:if>
</main>