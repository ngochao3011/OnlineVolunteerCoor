<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Thống kê Tình nguyện viên</title>
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
                    <h4>Thống Kê Tình Nguyện Viên Tham Gia Hoạt Động</h4>
                </div>

                <!-- Bộ lọc -->
                <form method="get" action="${pageContext.request.contextPath}/thongke/tinhnguyenvien" class="mb-4">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-4">
                            <label for="from" class="form-label">Từ ngày</label>
                            <input type="date" class="form-control" name="from" id="from" value="${from}">
                        </div>
                        <div class="col-md-4">
                            <label for="to" class="form-label">Đến ngày</label>
                            <input type="date" class="form-control" name="to" id="to" value="${to}">
                        </div>
                        <div class="col-md-4 d-flex justify-content-end align-items-end">
                            <div class="flex justify-end space-x-3 w-100">
                                <a href="${pageContext.request.contextPath}/thongke/tinhnguyenvien"
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

                <!-- Summary Card -->
                <div class="row g-4 mb-4">
                    <div class="col-md-6">
                        <div class="card card-box p-3 text-center">
                            <h6>Tổng số tình nguyện viên</h6>
                            <h3>${tongTNV}</h3>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card card-box p-3 text-center">
                            <h6>Tỷ lệ tăng</h6>
                            <h3 class="${tyLeTang >= 0 ? 'text-success' : 'text-danger'}">${tyLeTang}%</h3>
                        </div>
                    </div>
                </div>

                <!-- Biểu đồ -->
                <div class="card p-3 mb-4">
                    <h6 class="text-center">Số lượng tình nguyện viên theo hoạt động</h6>
                    <div class="chart-container">
                        <canvas id="barChart"></canvas>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="/WEB-INF/views/layout/footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script>
            const barData = {
                labels: [<c:forEach var="entry" items="${soTNVTheoHoatDong}">"${entry.key}",</c:forEach>],
                        datasets: [{
                                label: 'Số TNV',
                                data: [<c:forEach var="entry" items="${soTNVTheoHoatDong}">${entry.value},</c:forEach>],
                                backgroundColor: '#36A2EB'
                            }]
            };

            new Chart(document.getElementById('barChart'), {
                type: 'bar',
                data: barData,
                options: {
                    maintainAspectRatio: false,
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {
                                precision: 0
                            }
                        }
                    }
                }
            });
        </script>

    </body>
</html>
