<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Cập nhật Tình nguyện viên</title>

        <!-- CSS FILES -->
        <link href="${pageContext.request.contextPath}/src/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-homepage.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/src/css/template-navbar.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <!-- CSS CUSTOM -->
        <style>
            body {
                background-color: #e3f2fd; /* xanh dương nhạt */
            }
            .container-volunteer {
                background-color: #ffffff;
                padding: 15px 30px;
                box-shadow: 0 0 20px rgba(0,0,0,0.1);
            }
            h4 {
                color: #1976d2;
            }
            .error-message {
                color: red;
            }
            .success-message {
                color: green;
            }
            .invalid {
                border-color: red !important;
            }
            .valid {
                border-color: green !important;
            }
            .form-label {
                font-weight: 500;
            }
            .btn-back {
                margin-left: 10px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="layout/header.jsp" />
        <jsp:include page="layout/navbar.jsp" />

        <div class="container-volunteer">
            <h4 class="d-flex align-items-center">
                <i class="fas fa-user-edit me-2 text-primary"></i> Cập nhật Tình nguyện viên
            </h4>

            <!-- Hiển thị thông báo -->
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success mt-3">${successMessage}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-warning mt-3">${errorMessage}</div>
            </c:if>

            <!-- Form cập nhật -->
            <form action="${pageContext.request.contextPath}/volunteer/edit" method="post" onsubmit="return validateForm();" novalidate>
                <input type="hidden" name="maTNV" value="${volunteer.maTNV}"/>

                <div class="mb-3">
                    <label for="hoTen" class="form-label">Họ tên <span class="text-danger">*</span></label>
                    <input type="text" name="hoTen" id="hoTen" class="form-control"
                           value="${fn:escapeXml(volunteer.hoTen)}" required/>
                </div>
                <div class="mb-3">
                    <label for="sdtDienThoai" class="form-label">Số điện thoại <span class="text-danger">*</span></label>
                    <input type="text" name="sdtDienThoai" id="sdtDienThoai" class="form-control"
                           value="${fn:escapeXml(volunteer.sdtDienThoai)}" required/>
                </div>
                <div class="mb-3">
                    <label for="diaChi" class="form-label">Địa chỉ <span class="text-danger">*</span></label>
                    <input type="text" name="diaChi" id="diaChi" class="form-control"
                           value="${fn:escapeXml(volunteer.diaChi)}" required/>
                </div>
                <div class="mb-3">
                    <label for="trangThai" class="form-label">Trạng thái <span class="text-danger">*</span></label>
                    <select name="trangThai" id="trangThai" class="form-select" required>
                        <option value="">-- Chọn trạng thái --</option>
                        <option value="Hoạt động" ${volunteer.trangThai == 'Hoạt động' ? 'selected' : ''}>Hoạt động</option>
                        <option value="Đã đăng ký" ${volunteer.trangThai == 'Đã đăng ký' ? 'selected' : ''}>Đã đăng ký</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="ngayDangKy" class="form-label">Ngày đăng ký</label>
                    <input type="text" id="ngayDangKy" class="form-control"
                           value="<fmt:formatDate value='${volunteer.ngayDangKy}' pattern='dd/MM/yyyy'/>" readonly disabled/>
                    <input type="hidden" name="ngayDangKy" value="<fmt:formatDate value='${volunteer.ngayDangKy}' pattern='yyyy-MM-dd'/>"/>
                </div>

                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i> Lưu
                </button>
                <a href="${pageContext.request.contextPath}/volunteer" class="btn btn-secondary btn-back">
                    <i class="fas fa-arrow-left"></i> Quay lại
                </a>
            </form>
        </div>

        <jsp:include page="layout/footer.jsp" />

        <!-- JavaScript validation -->
        <script>
            function validateForm() {
                const hoTen = document.getElementById("hoTen").value.trim();
                const sdt = document.getElementById("sdtDienThoai").value.trim();
                const diaChi = document.getElementById("diaChi").value.trim();
                const trangThai = document.getElementById("trangThai").value;

                if (!hoTen || !sdt || !diaChi || !trangThai) {
                    alert("Vui lòng điền đầy đủ thông tin.");
                    return false;
                }

                if (hoTen.length > 100) {
                    alert("Họ tên không được vượt quá 100 ký tự.");
                    document.getElementById("hoTen").classList.add("invalid");
                    return false;
                }

                if (sdt.length > 20) {
                    alert("Số điện thoại không được vượt quá 20 ký tự.");
                    document.getElementById("sdtDienThoai").classList.add("invalid");
                    return false;
                }

                if (diaChi.length > 255) {
                    alert("Địa chỉ không được vượt quá 255 ký tự.");
                    document.getElementById("diaChi").classList.add("invalid");
                    return false;
                }

                const phoneRegex = /^0\d{9}$/;
                if (!phoneRegex.test(sdt)) {
                    alert("Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số.");
                    document.getElementById("sdtDienThoai").classList.add("invalid");
                    return false;
                }

                document.querySelectorAll(".form-control, .form-select").forEach(elem => {
                    elem.classList.remove("invalid");
                    if (elem.value.trim()) {
                        elem.classList.add("valid");
                    }
                });

                return true;
            }
        </script>
    </body>
</html>
