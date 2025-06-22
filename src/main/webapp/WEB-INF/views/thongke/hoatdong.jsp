<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Thống kê Hoạt động</title>
        <link href="${pageContext.request.contextPath}/src/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-homepage.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-navbar.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />

        <style>
            h4 {
                color: #1976d2;
            }
            .card-box {
                border: 2px solid #ccc;
                border-radius: 6px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                overflow: hidden;
            }
            .section-title h4 {
                margin-bottom: 20px;
            }
            .chart-container {
                position: relative;
                height: 320px;
                width: 100%;
            }
        </style>
    </head>
    <body>

        <jsp:include page="/WEB-INF/views/layout/header.jsp" />
        <jsp:include page="/WEB-INF/views/layout/navbar.jsp" />

        <section class="blog-section spad">
            <div class="container">
                <div class="section-title text-center">
                    <h4>Thống Kê Hoạt Động Tình Nguyện</h4>
                </div>

                <!-- Bộ lọc -->
                <form method="get" action="${pageContext.request.contextPath}/thongke/hoatdong" class="mb-4">
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
                                <option value="Hoàn thành" ${status == 'Hoàn thành' ? 'selected' : ''}>Hoàn thành</option>
                                <option value="Đang thực hiện" ${status == 'Đang thực hiện' ? 'selected' : ''}>Đang thực hiện</option>
                                <option value="Đã hủy" ${status == 'Đã hủy' ? 'selected' : ''}>Đã hủy</option>
                            </select>
                        </div>
                        <div class="col-md-3 d-flex justify-content-end align-items-end">
                            <div class="flex justify-end space-x-3 w-100">
                                <a href="${pageContext.request.contextPath}/thongke/hoatdong"
                                   class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-100 transition w-50 text-center">
                                    <i class="fas fa-redo mr-2"></i> Đặt lại
                                </a>
                                <button type="submit"
                                        class="px-4 py-2 bg-blue-600 text-blue rounded-lg hover:bg-blue-700 transition flex items-center w-50 justify-center">
                                    <i class="fas fa-search mr-2"></i> Tìm kiếm
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
                            <h3>${summary.tongSoHoatDong}</h3>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card card-box p-3">
                            <h6>Hoàn thành</h6>
                            <h3>${summary.soHoanThanh}</h3>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card card-box p-3">
                            <h6>Đang thực hiện</h6>
                            <h3>${summary.soDangThucHien}</h3>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card card-box p-3">
                            <h6>Đã hủy</h6>
                            <h3>${summary.soDaHuy}</h3>
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

            </div>
            <div class="flex justify-end mb-4">
                <form method="get" action="${pageContext.request.contextPath}/thongke/hoatdong/export/excel">
                    <input type="hidden" name="from" value="${from}" />
                    <input type="hidden" name="to" value="${to}" />
                    <input type="hidden" name="status" value="${status}" />
                    <button type="submit" class="btn btn-success">
                        <i class="fas fa-file-excel"></i> Xuất Excel
                    </button>
                </form>
            </div>




        </section>

        <jsp:include page="/WEB-INF/views/layout/footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script>
            const lineData = {
            labels: [<c:forEach var="entry" items="${thongKeTheoThang}">"${entry.key}",</c:forEach>],
                    datasets: [{
                    label: 'Số hoạt động',
                            data: [<c:forEach var="entry" items="${thongKeTheoThang}">${entry.value},</c:forEach>],
                            borderColor: 'rgb(54, 162, 235)',
                            backgroundColor: 'rgba(54, 162, 235, 0.2)',
                            fill: true,
                            tension: 0.3
                    }]
            };
            new Chart(document.getElementById('lineChart'), {
            type: 'line',
                    data: lineData,
                    options: {
                    maintainAspectRatio: false
                    }
            });
            const pieData = {
            labels: [<c:forEach var="entry" items="${thongKeTheoTrangThai}">"${entry.key}",</c:forEach>],
                    datasets: [{
                    data: [<c:forEach var="entry" items="${thongKeTheoTrangThai}">${entry.value},</c:forEach>],
                            backgroundColor: ['#10B981', '#F59E0B', '#EF4444']
                    }]
            };
            new Chart(document.getElementById('pieChart'), {
            type: 'pie',
                    data: pieData,
                    options: {
                    maintainAspectRatio: false
                    }
            });
        </script>

    </body>
</html>
