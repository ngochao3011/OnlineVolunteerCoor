<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<main class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h3>Viết đánh giá</h3>
                </div>
                <div class="card-body p-4">
                    <h4 class="card-title mb-4">Hoạt động: <span class="fw-bold">${hoatDong.tenHoatDong}</span></h4>

                    <%-- Form sử dụng Spring Form Tags để bind dữ liệu --%>
                    <form action="${pageContext.request.contextPath}/review/submit" method="post">

                        <%-- 1. THÊM DÒNG NÀY ĐỂ CHÈN CSRF TOKEN --%>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <%-- 2. Dùng input ẩn HTML tiêu chuẩn --%>
                        <input type="hidden" name="maHoatDong" value="${hoatDong.maHoatDong}" />

                        <div class="mb-3">
                            <label for="moTa" class="form-label fs-5">Cảm nhận của bạn về hoạt động:</label>
                            <textarea name="moTa" id="moTa" class="form-control" rows="6" required="true" placeholder="Hoạt động rất ý nghĩa và được tổ chức chuyên nghiệp..."></textarea>
                        </div>

                        <div class="mb-4">
                            <label for="ghiChu" class="form-label fs-5">Bạn có góp ý gì để chúng tôi cải thiện không? (Không bắt buộc)</label>
                            <textarea name="ghiChu" id="ghiChu" class="form-control" rows="4" placeholder="Nên có thêm... / Cần cải thiện về..."></textarea>
                        </div>

                        <div class="d-flex justify-content-end border-top pt-3">
                            <a href="${pageContext.request.contextPath}/user/history" class="btn btn-outline-secondary me-2">Hủy</a>
                            <button type="submit" class="btn btn-primary px-4">
                                <i class="fas fa-paper-plane me-2"></i>Gửi đánh giá
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>