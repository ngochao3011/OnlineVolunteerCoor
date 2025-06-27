<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<section class="blog-section spad">
    <div class="container">
        <div class="section-title text-center mb-4">
            <h4>Thống Kê Hoạt Động Tình Nguyện</h4>
        </div>

        <!-- Bộ lọc -->
        <form method="get" action="${pageContext.request.contextPath}/admin/thongke/hoatdong" class="mb-4">
            <div class="row g-3 align-items-end">
                <div class="col-md-3">
                    <label for="from" class="form-label">Từ ngày</label>
                    <input type="date" class="form-control" name="from" id="from" value="${from}">
                </div>
                <div class="col-md-3">
                    <label for="to" class="form-label">Đến ngày</label>
                    <input type="date" class="form-control" name="to" id="to" value="${to}">
                </div>
                <div class="col-md-3">
                    <label for="status" class="form-label">Trạng thái</label>
                    <select class="form-select" name="status" id="status">
                        <option value="">Tất cả</option>
                        <option value="Đã kết thúc" ${status == 'Đã kết thúc' ? 'selected' : ''}>Đã kết thúc</option>
                        <option value="Đang hoạt động" ${status == 'Đang hoạt động' ? 'selected' : ''}>Đang hoạt động</option>
                        <option value="Sắp diễn ra" ${status == 'Sắp diễn ra' ? 'selected' : ''}>Sắp diễn ra</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label d-block invisible">Hành động</label>
                    <div class="d-flex justify-content-between w-100 gap-2">
                        <a href="${pageContext.request.contextPath}/admin/thongke/hoatdong"
                           class="btn btn-primary w-50 d-flex align-items-center justify-content-center">
                            <i class="fas fa-redo me-2"></i> Đặt lại
                        </a>
                        <button type="submit"
                                class="btn btn-primary w-50 d-flex align-items-center justify-content-center">
                            <i class="fas fa-search me-2"></i> Tìm kiếm
                        </button>
                    </div>
                </div>
            </div>
        </form>

        <!-- Summary Cards -->
        <div class="row g-4 mb-4">
            <div class="col-md-3">
                <div class="card card-box p-3">
                    <h6>Tổng số hoạt động</h6>
                    <h3 class="text-4xl font-bold text-gray-800">${thongKe.tongSoHoatDong}</h3>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card card-box p-3">
                    <h6>Đã kết thúc</h6>
                    <h3 class="text-4xl font-bold text-gray-800">${thongKe.soHoanThanh}</h3>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card card-box p-3">
                    <h6>Đang hoạt động</h6>
                    <h3 class="text-4xl font-bold text-gray-800">${thongKe.soDangThucHien}</h3>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card card-box p-3">
                    <h6>Sắp diễn ra</h6>
                    <h3 class="text-4xl font-bold text-gray-800">${thongKe.soDaHuy}</h3>
                </div>
            </div>
        </div>

        <!-- Biểu đồ -->
        <div class="row g-4">
            <div class="col-md-6">
                <div class="card p-3">
                    <h6 class="text-center">Biểu đồ hoạt động theo tháng</h6>
                    <div class="chart-container">
                        <canvas id="lineChart"></canvas>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card p-3">
                    <h6 class="text-center">Biểu đồ trạng thái</h6>
                    <div class="chart-container">
                        <canvas id="pieChart"></canvas>
                    </div>
                </div>
            </div>
        </div>

        <!-- Export Excel -->
        <form method="get" action="${pageContext.request.contextPath}/admin/thongke/export/csv">
            <input type="hidden" name="from" value="${from}">
            <input type="hidden" name="to" value="${to}">
            <input type="hidden" name="status" value="${status}">
            <button type="submit" class="btn btn-outline-primary">
                <i class="fa fa-file-csv"></i> Xuất CSV
            </button>
        </form>

    </div>
</section>

<!-- ChartJS -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    // Line Chart
    const lineLabels = [<c:forEach var="entry" items="${thongKeTheoThang}">"${entry.key}",</c:forEach>];
    const lineValues = [<c:forEach var="entry" items="${thongKeTheoThang}">${entry.value},</c:forEach>];

    const lineChart = new Chart(document.getElementById('lineChart'), {
        type: 'line',
        data: {
            labels: lineLabels,
            datasets: [{
                    label: 'Số hoạt động',
                    data: lineValues,
                    borderColor: '#0d6efd',
                    backgroundColor: 'rgba(13, 110, 253, 0.2)',
                    fill: true,
                    tension: 0.3
                }]
        },
        options: {
            maintainAspectRatio: false
        }
    });

    // Pie Chart
    const pieLabels = [<c:forEach var="entry" items="${thongKeTheoTrangThai}">"${entry.key}",</c:forEach>];
    const pieValues = [<c:forEach var="entry" items="${thongKeTheoTrangThai}">${entry.value},</c:forEach>];

    const pieChart = new Chart(document.getElementById('pieChart'), {
        type: 'pie',
        data: {
            labels: pieLabels,
            datasets: [{
                    data: pieValues,
                    backgroundColor: ['#10B981', '#F59E0B', '#EF4444']
                }]
        },
        options: {
            maintainAspectRatio: false
        }
    });
</script>