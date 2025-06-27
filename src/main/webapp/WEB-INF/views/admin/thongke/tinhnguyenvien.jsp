<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<section class="blog-section spad">
    <div class="container">
        <div class="section-title text-center mb-4">
            <h2 class="text-center my-4 fw-bold">Thống Kê Hoạt Động Tình Nguyện</h2>
        </div>

        <!-- Bộ lọc -->
        <form method="get" class="row g-3 align-items-end mb-4" action="${pageContext.request.contextPath}/admin/thongke/tinhnguyenvien">
            <div class="col-md-4">
                <label for="from" class="form-label">Từ ngày</label>
                <input type="date" class="form-control" name="from" value="${from}">
            </div>
            <div class="col-md-4">
                <label for="to" class="form-label">Đến ngày</label>
                <input type="date" class="form-control" name="to" value="${to}">
            </div>
            <div class="col-md-4">
                <label class="form-label d-block invisible">Hành động</label>
                <div class="d-flex justify-content-between w-100 gap-2">
                    <a href="${pageContext.request.contextPath}/admin/thongke/tinhnguyenvien"
                       class="btn btn-primary w-50"><i class="fas fa-redo me-2"></i> Đặt lại</a>
                    <button type="submit" class="btn btn-primary w-50"><i class="fas fa-search me-2"></i> Tìm kiếm</button>
                </div>
            </div>
        </form>

        <!-- TÍNH TỔNG TNV THỰC TẾ -->
        <c:set var="tongTNVThucTe" value="0" />
        <c:forEach var="tk" items="${thongKeTNV}">
            <c:set var="tongTNVThucTe" value="${tongTNVThucTe + tk.soLuongTNV}" />
        </c:forEach>


        <div class="row g-4 mb-4">
            <div class="col-md-6">
                <div class="card card-box p-3">
                    <h6>Tổng tình nguyện viên</h6>
                    <h3 class="text-4xl font-bold text-gray-800">${tongTNV}</h3>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card card-box p-3">
                    <h6>Số lượt tham gia hoạt động </h6>
                    <h3 class="text-4xl font-bold text-gray-800">
                        <c:out value="${tongTNVThucTe}" default="0" />
                    </h3>
                </div>
            </div>
        </div>


        <!-- Biểu đồ cột: TNV theo mã hoạt động -->
        <div class="card mt-4 p-4">
            <h5 class="mb-3">Biểu đồ số lượng TNV theo mã hoạt động</h5>
            <canvas id="barChartTNV"></canvas>
        </div>

        <script>
            // Lấy dữ liệu từ JSTL để truyền vào biểu đồ
            const labels = [];
            const data = [];

            <c:forEach var="tk" items="${thongKeTNV}">
            labels.push("${tk.maHoatDong}");
            data.push(${tk.soLuongTNV});
            </c:forEach>
        </script>

        <!-- Thêm thư viện Chart.js nếu chưa có -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

        <script>
            const ctx = document.getElementById('barChartTNV').getContext('2d');

            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                            label: 'Số lượng TNV',
                            data: data,
                            backgroundColor: 'rgba(54, 162, 235, 0.7)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1
                        }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true,
                            title: {
                                display: true,
                                text: 'Số TNV'
                            }
                        },
                        x: {
                            title: {
                                display: true,
                                text: 'Mã hoạt động'
                            }
                        }
                    }
                }
            });
        </script>


        <!-- Xuất CSV -->
        <form method="get" action="${pageContext.request.contextPath}/admin/thongke/tinhnguyenvien/export/csv">
            <input type="hidden" name="from" value="${from}">
            <input type="hidden" name="to" value="${to}">
            <button type="submit" class="btn btn-outline-primary">
                <i class="fa fa-file-csv"></i> Xuất CSV 
            </button>
        </form>
</section>
