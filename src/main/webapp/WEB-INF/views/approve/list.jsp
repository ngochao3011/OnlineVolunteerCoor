<%-- 
    Document   : list
    Created on : Jun 20, 2025, 8:54:54 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<section class="blog-section spad">
    <div class="container">
        <div class="section-title text-center">
            <h4>Danh sách đăng ký cần duyệt</h4>
        </div>
        <!-- Thêm thông báo thành công với JavaScript để tự động ẩn -->
        <c:if test="${not empty message}">
            <div id="successAlert" class="alert alert-success alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Mã ĐDK</th>
                        <th>Họ Tên</th>
                        <th>Email</th>
                        <th>Hoạt Động</th>
                        <th>Thời Gian Bắt Đầu</th>
                        <th>Thời Gian Kết Thúc</th>
                        <th>Địa Điểm</th>
                        <th>Trạng Thái Hoạt Động</th>
                        <th>Trạng Thái Duyệt</th>
                        <th>Hành Động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty duyetDangKyList}">
                        <tr>
                            <td colspan="10" class="text-center">Hiện chưa có đăng ký nào cần duyệt.</td>
                        </tr>
                    </c:if>
                    <c:forEach var="dangKy" items="${duyetDangKyList}">
                        <tr>
                            <td>${dangKy.maDDK}</td>
                            <td>${not empty dangKy.hoTen ? dangKy.hoTen : 'Chưa có tên'}</td>
                            <td>${not empty dangKy.email ? dangKy.email : 'Chưa có email'}</td>
                            <td>${not empty dangKy.hoatDong && not empty dangKy.hoatDong.tenHoatDong ? dangKy.hoatDong.tenHoatDong : 'Chưa có hoạt động'}</td>
                            <td>${not empty dangKy.hoatDong ? dangKy.hoatDong.getThoiGianBatDauFormatted() : ''}</td>
                            <td>${not empty dangKy.hoatDong ? dangKy.hoatDong.getThoiGianKetThucFormatted() : ''}</td>
                            <td>${not empty dangKy.hoatDong ? dangKy.hoatDong.diaDiem : ''}</td>
                            <td>${isUpcoming ? 'Sắp diễn ra' : (not empty dangKy.hoatDong ? dangKy.hoatDong.trangThai : '')}</td>
                            <td>${not empty dangKy.trangThaiDuyet ? dangKy.trangThaiDuyet : 'Chưa duyệt'}</td>
                            <td>
                                <!-- Nút kích hoạt modal duyệt -->
                                <button type="button" class="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#approveModal-${dangKy.maDDK}">
                                    Duyệt
                                </button>
                                <!-- Nút kích hoạt modal từ chối -->
                                <button type="button" class="btn btn-sm btn-danger" data-bs-toggle="modal" data-bs-target="#rejectModal-${dangKy.maDDK}">
                                    Từ chối
                                </button>
                            </td>
                        </tr>

                        <!-- Modal cho Duyệt -->
                    <div class="modal fade" id="approveModal-${dangKy.maDDK}" tabindex="-1" aria-labelledby="approveModalLabel-${dangKy.maDDK}" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="approveModalLabel-${dangKy.maDDK}">Xác nhận duyệt</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <p>Bạn có chắc chắn muốn duyệt đăng ký với mã <strong>${dangKy.maDDK}</strong>?</p>
                                    <div class="mb-3">
                                        <label for="ghiChuApprove-${dangKy.maDDK}" class="form-label">Ghi chú (tùy chọn):</label>
                                        <input type="text" class="form-control" id="ghiChuApprove-${dangKy.maDDK}" name="ghiChu">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                    <form action="${pageContext.request.contextPath}/activity/approve/update" method="post" style="display:inline;">
                                        <input type="hidden" name="maDDK" value="${dangKy.maDDK}">
                                        <input type="hidden" name="trangThaiDuyet" value="Đã duyệt">
                                        <input type="hidden" name="ghiChu" id="ghiChuInputApprove-${dangKy.maDDK}">
                                        <input type="hidden" name="page" value="${currentPage}">
                                        <input type="hidden" name="keyword" value="${keyword}">
                                        <input type="hidden" name="location" value="${location}">
                                        <input type="hidden" name="trangThai" value="${trangThai}">
                                        <button type="submit" class="btn btn-success">Xác nhận</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Modal cho Từ chối -->
                    <div class="modal fade" id="rejectModal-${dangKy.maDDK}" tabindex="-1" aria-labelledby="rejectModalLabel-${dangKy.maDDK}" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="rejectModalLabel-${dangKy.maDDK}">Xác nhận từ chối</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <p>Bạn có chắc chắn muốn từ chối đăng ký với mã <strong>${dangKy.maDDK}</strong>?</p>
                                    <div class="mb-3">
                                        <label for="ghiChuReject-${dangKy.maDDK}" class="form-label">Ghi chú (tùy chọn):</label>
                                        <input type="text" class="form-control" id="ghiChuReject-${dangKy.maDDK}" name="ghiChu">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                    <form action="${pageContext.request.contextPath}/activity/approve/update" method="post" style="display:inline;">
                                        <input type="hidden" name="maDDK" value="${dangKy.maDDK}">
                                        <input type="hidden" name="trangThaiDuyet" value="Từ chối">
                                        <input type="hidden" name="ghiChu" id="ghiChuInputReject-${dangKy.maDDK}">
                                        <input type="hidden" name="page" value="${currentPage}">
                                        <input type="hidden" name="keyword" value="${keyword}">
                                        <input type="hidden" name="location" value="${location}">
                                        <input type="hidden" name="trangThai" value="${trangThai}">
                                        <button type="submit" class="btn btn-danger">Xác nhận</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Phân trang -->
        <c:if test="${not empty duyetDangKyList}">
            <%@ include file="/WEB-INF/views/layout/pagination.jsp" %>
        </c:if>
    </div>
</section>

<!-- JavaScript để truyền giá trị ghi chú vào form và tự động ẩn thông báo -->
<script>
    // Hàm lấy giá trị ghi chú và đặt vào input ẩn khi xác nhận duyệt
    function setApproveGhiChu(maDDK) {
        var ghiChu = document.getElementById('ghiChuApprove-' + maDDK).value;
        document.getElementById('ghiChuInputApprove-' + maDDK).value = ghiChu;
    }

    // Hàm lấy giá trị ghi chú và đặt vào input ẩn khi xác nhận từ chối
    function setRejectGhiChu(maDDK) {
        var ghiChu = document.getElementById('ghiChuReject-' + maDDK).value;
        document.getElementById('ghiChuInputReject-' + maDDK).value = ghiChu;
    }

    // Gán sự kiện cho nút Xác nhận trong modal
    document.querySelectorAll('.modal-footer form').forEach(form => {
        form.addEventListener('submit', function (event) {
            var maDDK = this.querySelector('input[name="maDDK"]').value;
            var trangThaiDuyet = this.querySelector('input[name="trangThaiDuyet"]').value;
            if (trangThaiDuyet === 'Đã duyệt') {
                setApproveGhiChu(maDDK);
            } else if (trangThaiDuyet === 'Từ chối') {
                setRejectGhiChu(maDDK);
            }
        });
    });

    // Tự động ẩn thông báo sau 3 giây
    document.addEventListener('DOMContentLoaded', function () {
        var alert = document.getElementById('successAlert');
        if (alert) {
            setTimeout(function () {
                alert.classList.remove('show');
                alert.classList.add('fade');
                setTimeout(function () {
                    alert.style.display = 'none';
                }, 500); // Thời gian fade out (0.5 giây)
            }, 3000); // Thời gian hiển thị trước khi ẩn (3 giây)
        }
    });
</script>
