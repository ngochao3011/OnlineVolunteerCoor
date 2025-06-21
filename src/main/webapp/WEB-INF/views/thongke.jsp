<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<main class="container mx-auto px-4 py-6">

    <!-- Bộ lọc -->
    <div class="bg-white rounded-xl shadow-md p-6 mb-6">
        <h2 class="text-lg font-semibold text-gray-800 mb-4 flex items-center">
            <i class="fas fa-filter mr-2 text-blue-500"></i> Bộ lọc báo cáo
        </h2>
        <form method="get" action="${pageContext.request.contextPath}/thongke">
            <div class="grid grid-cols-1 md:grid-cols-3 gap-4 items-end" style="display: flex">
                <div style="width: 50%">
                    <label class="block text-sm font-medium text-gray-700 mb-1">Khoảng thời gian:</label>
                    <div class="flex items-center space-x-2">
                        <input type="date" name="from" value="${param.from}" class="w-full px-3 py-2 border border-gray-300 rounded-lg">
                        <span class="text-sm text-gray-500">đến</span>
                        <input type="date" name="to" value="${param.to}" class="w-full px-3 py-2 border border-gray-300 rounded-lg">
                    </div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Trạng thái:</label>
                    <div class="flex items-center space-x-2">
                        <select name="status" class="w-full px-3 py-2 border border-gray-300 rounded-lg">
                            <option value="">Tất cả trạng thái</option>
                            <option value="Hoàn thành" ${param.status == 'Hoàn thành' ? 'selected' : ''}>Hoàn thành</option>
                            <option value="Đang thực hiện" ${param.status == 'Đang thực hiện' ? 'selected' : ''}>Đang thực hiện</option>
                            <option value="Đã hủy" ${param.status == 'Đã hủy' ? 'selected' : ''}>Đã hủy</option>
                        </select>
                    </div>
                    <div class="flex justify-end mt-4 space-x-1">
                        <a href="${pageContext.request.contextPath}/thongke"
                           class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-100 transition">
                            <i class="fas fa-redo mr-2"></i> Đặt lại
                        </a>
                        <button type="submit"
                                class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-100 transition flex items-center">
                            <i class="fas fa-search mr-2"></i> Tìm kiếm
                        </button>
                    </div>
                </div>
            </div>
        </form>    
    </div>                   


    <!-- Summary cards -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 text-sm mb-6">
        <!-- Tổng -->

        <div class="bg-white rounded-xl shadow-md p-4">
            <div class="flex justify-between items-center mb-2">
                <span class="font-medium text-gray-600">Tổng số hoạt động</span>
                <div class="bg-blue-100 text-blue-600 rounded-full p-2"><i class="fas fa-calendar-check"></i></div>
            </div>
            <div class="text-2xl font-bold text-gray-800">${summary.tongSoHoatDong}</div>
            <div class="text-sm mt-1 ${summary.tiLeTangTruongTong >= 0 ? 'text-green-600' : 'text-red-600'}">
                <i class="${summary.tiLeTangTruongTong >= 0 ? 'fas fa-arrow-up' : 'fas fa-arrow-down'} mr-1"></i>
                <fmt:formatNumber value="${summary.tiLeTangTruongTong}" type="number" maxFractionDigits="1"/>% so với tháng trước
            </div>
        </div>
        <!-- Hoàn thành -->
        <div class="bg-white rounded-xl shadow-md p-4">
            <div class="flex justify-between items-center mb-2">
                <span class="font-medium text-gray-600">Hoạt động hoàn thành</span>
                <div class="bg-green-100 text-green-600 rounded-full p-2"><i class="fas fa-check-circle"></i></div>
            </div>
            <div class="text-2xl font-bold text-gray-800">${summary.soHoanThanh}</div>
            <div class="text-sm mt-1 ${summary.tiLeTangTruongHoanThanh >= 0 ? 'text-green-600' : 'text-red-600'}">
                <i class="${summary.tiLeTangTruongHoanThanh >= 0 ? 'fas fa-arrow-up' : 'fas fa-arrow-down'} mr-1"></i>
                <fmt:formatNumber value="${summary.tiLeTangTruongHoanThanh}" type="number" maxFractionDigits="1"/>% so với tháng trước
            </div>
        </div>
        <!-- Đang thực hiện -->
        <div class="bg-white rounded-xl shadow-md p-4">
            <div class="flex justify-between items-center mb-2">
                <span class="font-medium text-gray-600">Hoạt động đang thực hiện</span>
                <div class="bg-yellow-100 text-yellow-600 rounded-full p-2"><i class="fas fa-spinner"></i></div>
            </div>
            <div class="text-2xl font-bold text-gray-800">${summary.soDangThucHien}</div>
            <div class="text-sm mt-1 ${summary.tiLeTangTruongDangThucHien >= 0 ? 'text-green-600' : 'text-red-600'}">
                <i class="${summary.tiLeTangTruongDangThucHien >= 0 ? 'fas fa-arrow-up' : 'fas fa-arrow-down'} mr-1"></i>
                <fmt:formatNumber value="${summary.tiLeTangTruongDangThucHien}" type="number" maxFractionDigits="1"/>% so với tháng trước
            </div>
        </div>
        <!-- Đã hủy -->
        <div class="bg-white rounded-xl shadow-md p-4">
            <div class="flex justify-between items-center mb-2">
                <span class="font-medium text-gray-600">Hoạt động bị hủy</span>
                <div class="bg-red-100 text-red-600 rounded-full p-2"><i class="fas fa-times-circle"></i></div>
            </div>
            <div class="text-2xl font-bold text-gray-800">${summary.soDaHuy}</div>
            <div class="text-sm mt-1 ${summary.tiLeTangTruongDaHuy >= 0 ? 'text-green-600' : 'text-red-600'}">
                <i class="${summary.tiLeTangTruongDaHuy >= 0 ? 'fas fa-arrow-up' : 'fas fa-arrow-down'} mr-1"></i>
                <fmt:formatNumber value="${summary.tiLeTangTruongDaHuy}" type="number" maxFractionDigits="1"/>% so với tháng trước
            </div>
        </div>
    </div>

    <!-- Biểu đồ -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-4 items-end" style="display: flex">
        <div style="width: 50%">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <!-- Biểu đồ line theo tháng -->
                <div class="bg-white p-6 rounded-xl shadow-md">
                    <h3 class="text-lg font-semibold text-gray-700 mb-4">Thống kê hoạt động theo tháng</h3>
                    <canvas id="lineChart"></canvas>
                </div>

                <!-- Biểu đồ pie theo trạng thái -->
                <div class="bg-white p-6 rounded-xl shadow-md">
                    <h3 class="text-lg font-semibold text-gray-500 mb-2">Tỷ lệ trạng thái hoạt động</h3>
                    <canvas id="pieChart"></canvas>

                </div>
            </div>
            </main>

            <!-- Dữ liệu cho biểu đồ -->
            <script>
                const dataLine = {
                labels: [<c:forEach items="${thongKeTheoThang}" var="entry">"${entry.key}",</c:forEach>],
                        datasets: [{
                        label: "Số hoạt động",
                                data: [<c:forEach items="${thongKeTheoThang}" var="entry">${entry.value},</c:forEach>],
                                borderColor: "rgb(37, 99, 235)",
                                fill: false,
                                tension: 0.2
                        }]
                };
                const configLine = {
                type: 'line',
                        data: dataLine,
                };
                new Chart(document.getElementById('lineChart'), configLine);
                const dataPie = {
                labels: [<c:forEach items="${thongKeTheoTrangThai}" var="entry">"${entry.key}",</c:forEach>],
                        datasets: [{
                        data: [<c:forEach items="${thongKeTheoTrangThai}" var="entry">${entry.value},</c:forEach>],
                                backgroundColor: ['#10B981', '#F59E0B', '#EF4444']
                        }]
                };
                new Chart(document.getElementById('pieChart'), {
                type: 'pie',
                        data: dataPie
                });
            </script>

