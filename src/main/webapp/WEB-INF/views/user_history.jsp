<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<main class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="mb-0">Lịch sử Hoạt động đã tham gia</h1>
        <a href="#" class="btn btn-success disabled" title="Chức năng đang phát triển">
            <i class="fas fa-file-export me-2"></i>Xuất danh sách
        </a>
    </div>

    <p class="lead text-muted">Đây là danh sách các hoạt động bạn đã được ghi nhận tham gia. Bạn có thể gửi đánh giá cho các hoạt động này.</p>

    <div class="card mt-4">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover align-middle table-history">
                    <thead>
                        <tr>
                            <th style="width: 5%;">#</th>
                            <th style="width: 50%;">Tên Hoạt Động</th>
                            <th style="width: 25%;">Ngày kết thúc</th>
                            <th class="text-center" style="width: 20%;">Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty attendedEvents}">
                                <c:forEach var="event" items="${attendedEvents}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/activity/details/${event.maHoatDong}">${event.tenHoatDong}</a>
                                        </td>
                                        <td>${event.getThoiGianKetThucFormatted()}</td>
                                        <td class="text-center">
                                            <c:choose>
                                                <%-- Nếu đã đánh giá, hiển thị nút đã xong --%>
                                                <c:when test="${reviewedEventIds.contains(event.maHoatDong)}">
                                                    <button class="btn btn-sm btn-success" disabled>
                                                        <i class="fas fa-check-circle me-1"></i> Đã đánh giá
                                                    </button>
                                                </c:when>
                                                <%-- Nếu chưa, hiển thị nút để viết đánh giá --%>
                                                <c:otherwise>
                                                    <a href="${pageContext.request.contextPath}/review/form?maHoatDong=${event.maHoatDong}" class="btn btn-sm btn-warning">
                                                        <i class="fas fa-star me-1"></i> Viết đánh giá
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="4" class="text-center text-muted py-4">Bạn chưa tham gia hoạt động nào đã kết thúc.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>

<!-- Bảng lịch sử đăng ký hoạt động -->
<main class="container py-3">
    <h2 class="mb-3">Lịch sử đăng ký hoạt động</h2>
    <div class="card mb-4">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover align-middle table-history">
                    <thead>
                        <tr>
                            <th style="width: 5%;">#</th>
                            <th style="width: 30%;">Tên Hoạt Động</th>
                            <th style="width: 20%;">Ngày kết thúc</th>
                            <th style="width: 20%;">Địa điểm</th>
                            <th style="width: 15%;">Ngày đăng ký</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty lichSuDangKy}">
                                <c:forEach var="ls" items="${lichSuDangKy}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${ls.tenHoatDong}</td>
                                        <td><fmt:formatDate value="${ls.thoiGianKetThuc}" pattern="dd/MM/yyyy HH:mm"/></td>
                                        <td>${ls.diaDiem}</td>
                                        <td><fmt:formatDate value="${ls.createAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="5" class="text-center text-muted py-4">Bạn chưa có lịch sử đăng ký hoạt động nào.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>

<!-- Bảng lịch sử hủy đăng ký hoạt động -->
<main class="container py-3">
    <h2 class="mb-3">Lịch sử hủy đăng ký hoạt động</h2>
    <div class="card mb-4">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover align-middle table-history">
                    <thead>
                        <tr>
                            <th style="width: 5%;">#</th>
                            <th style="width: 30%;">Tên Hoạt Động</th>
                            <th style="width: 20%;">Ngày kết thúc</th>
                            <th style="width: 20%;">Địa điểm</th>
                            <th style="width: 15%;">Ngày hủy</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty lichSuHuyDangKy}">
                                <c:forEach var="ls" items="${lichSuHuyDangKy}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${ls.tenHoatDong}</td>
                                        <td><fmt:formatDate value="${ls.thoiGianKetThuc}" pattern="dd/MM/yyyy HH:mm"/></td>
                                        <td>${ls.diaDiem}</td>
                                        <td><fmt:formatDate value="${ls.createAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="5" class="text-center text-muted py-4">Bạn chưa có lịch sử hủy đăng ký hoạt động nào.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>