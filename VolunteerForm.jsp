<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8"/>
        <title>
            <c:choose>
                <c:when test="${volunteer.maTNV != 0}">
                    Cập nhật tình nguyện viên
                </c:when>
                <c:otherwise>
                    Thêm tình nguyện viên
                </c:otherwise>
            </c:choose>
        </title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <style>
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
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="mb-3">
                <c:choose>
                    <c:when test="${volunteer.maTNV != 0}">
                        Cập nhật tình nguyện viên
                    </c:when>
                    <c:otherwise>
                        Thêm tình nguyện viên
                    </c:otherwise>
                </c:choose>
            </h1>

            <!-- Hiển thị thông báo -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-warning">${errorMessage}</div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success">${successMessage}</div>
            </c:if>

            <!-- Form -->
            <form action="${pageContext.request.contextPath}/volunteer${volunteer.maTNV != 0 ? '/edit' : '/add'}" 
                  method="post" onsubmit="return validateForm();" novalidate>
                <c:if test="${volunteer.maTNV != 0}">
                    <input type="hidden" name="volunteer.maTNV" value="${volunteer.maTNV}"/>
                </c:if>

                <!-- Trường cho TaiKhoan (chỉ khi thêm mới) -->
                <c:if test="${volunteer.maTNV == 0}">
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" name="taiKhoan.email" id="email" class="form-control" required
                               value="${taiKhoan.email}"/>
                    </div>
                    <div class="mb-3">
                        <label for="matKhau" class="form-label">Mật khẩu</label>
                        <input type="password" name="taiKhoan.matKhau" id="matKhau" class="form-control" required/>
                    </div>
                    <input type="hidden" name="taiKhoan.quyenHan" value="Tình nguyện viên"/>
                </c:if>

                <!-- Trường cho Volunteer -->
                <div class="mb-3">
                    <label for="hoTen" class="form-label">Họ tên</label>
                    <input type="text" name="volunteer.hoTen" id="hoTen" class="form-control" required
                           value="${volunteer.hoTen}"/>
                </div>
                <div class="mb-3">
                    <label for="sdtDienThoai" class="form-label">Số điện thoại</label>
                    <input type="text" name="taiKhoan.Sdt" id="sdtDienThoai" class="form-control" required
                           value="${volunteer.sdtDienThoai}"/>
                </div>
                <div class="mb-3">
                    <label for="diaChi" class="form-label">Địa chỉ</label>
                    <input type="text" name="volunteer.diaChi" id="diaChi" class="form-control" required
                           value="${volunteer.diaChi}"/>
                </div>
                <div class="mb-3">
                    <label for="trangThai" class="form-label">Trạng thái</label>
                    <select name="volunteer.trangThai" id="trangThai" class="form-select" required>
                        <option value="">-- Chọn trạng thái --</option>
                        <option value="Hoạt động" ${volunteer.trangThai == 'Hoạt động' ? 'selected' : ''}>Hoạt động</option>
                        <option value="Đã đăng ký" ${volunteer.trangThai == 'Đã đăng ký' ? 'selected' : ''}>Đã đăng ký</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="ngayDangKy" class="form-label">Ngày đăng ký</label>
                    <input type="date" name="volunteer.ngayDangKy" id="ngayDangKy" class="form-control"
                           value="${volunteer.ngayDangKy != null ? volunteer.ngayDangKy : ''}"
                           ${volunteer.maTNV != 0 ? 'readonly' : ''}/>
                </div>

                <button type="submit" class="btn btn-primary">Lưu</button>
                <a href="${pageContext.request.contextPath}/volunteer" class="btn btn-secondary">Quay lại</a>
            </form>
        </div>

        <script>
            function validateForm() {
                const hoTen = document.getElementById("hoTen").value.trim();
                const sdt = document.getElementById("sdtDienThoai").value.trim();
                const diaChi = document.getElementById("diaChi").value.trim();
                const trangThai = document.getElementById("trangThai").value;
                const isEdit = ${volunteer != null && volunteer.maTNV != 0};

                // Kiểm tra các trường bắt buộc
                if (!hoTen || !sdt || !diaChi || !trangThai) {
                    alert("Vui lòng điền đầy đủ thông tin.");
                    return false;
                }

                // Kiểm tra email và mật khẩu khi thêm mới
                if (!isEdit) {
                    const email = document.getElementById("email")?.value.trim();
                    const matKhau = document.getElementById("matKhau")?.value.trim();
                    if (!email || !matKhau) {
                        alert("Vui lòng nhập email và mật khẩu.");
                        document.getElementById("email")?.classList.add("invalid");
                        document.getElementById("matKhau")?.classList.add("invalid");
                        return false;
                    }
                    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                    if (!emailRegex.test(email)) {
                        alert("Email không hợp lệ.");
                        document.getElementById("email").classList.add("invalid");
                        return false;
                    }
                    if (matKhau.length < 6) {
                        alert("Mật khẩu phải có ít nhất 6 ký tự.");
                        document.getElementById("matKhau").classList.add("invalid");
                        return false;
                    }
                }

                // Kiểm tra họ tên
                const nameRegex = /^[^\d]+$/;
                if (!nameRegex.test(hoTen)) {
                    alert("Họ tên không được chứa số.");
                    document.getElementById("hoTen").classList.add("invalid");
                    return false;
                }

                // Kiểm tra số điện thoại
                const phoneRegex = /^0\d{9}$/;
                if (!phoneRegex.test(sdt)) {
                    alert("Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số.");
                    document.getElementById("sdtDienThoai").classList.add("invalid");
                    return false;
                }

                // Xóa class invalid nếu hợp lệ
                document.querySelectorAll(".form-control, .form-select").forEach(elem => {
                    elem.classList.remove("invalid");
                    if (elem.value.trim())
                        elem.classList.add("valid");
                });

                return true;
            }
        </script>
    </body>
</html>