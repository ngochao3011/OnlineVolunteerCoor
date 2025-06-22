<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div class="container-account">
    <form id="accountForm" action="${pageContext.request.contextPath}/updateAccount" method="post" enctype="multipart/form-data">
        <h5 class="mb-1 section-title">Account Settings</h5>
        <p class="text-muted mb-2 section-subtitle">Manage your personal information</p>
        <div class="card p-4 settings-card">
            <div class="text-center mb-3 profile-img-container">
                <label for="avatarInput">
                    <img id="avatarPreview" src="${pageContext.request.contextPath}${sessionScope.urlAvatar}" alt="avatar" class="profile-img" />
                    <img id="editIcon" src="${pageContext.request.contextPath}/src/images/edit-icon.png" class="edit-icon" width="24" style="display: none;" />
                </label>
                <input type="file" name="avatarFile" id="avatarInput" accept="image/*" onchange="previewAvatar(this)" style="display: none;" disabled />
            </div>

            <input type="hidden" name="maThanhVien" value="${thanhVien.maThanhVien}" />

            <div class="mb-3">
                <label>Email</label>
                <input type="email" name="email" class="form-control editable-field" value="${user.email}" readonly />
            </div>

            <div class="mb-3">
                <label>Họ tên</label>
                <input type="text" class="form-control editable-field" name="hoTen" value="${thanhVien.hoTen}" readonly />
            </div>

            <div class="mb-3">
                <label>Số điện thoại</label>
                <input type="text" class="form-control editable-field" name="sdt" value="${thanhVien.sdt}" readonly />
            </div>

            <div class="mb-3">
                <label>Địa chỉ</label>
                <input type="text" class="form-control editable-field" name="diaChi" value="${thanhVien.diaChi}" readonly />
            </div>

            <div class="d-flex justify-content-between mt-3">
                <div>
                    <button type="button" id="editBtn" class="btn btn-secondary">Edit</button>
                    <button type="submit" id="updateBtn" class="btn btn-primary" style="display:none;">Cập nhật</button>
                </div>

                <div>
                    <button type="button" class="btn btn-warning" onclick="openPasswordDialog()" >Đổi mật khẩu</button>
                </div>
            </div>
        </div>
    </form>
    <div id="passwordModal" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%;
         background-color: rgba(0,0,0,0.5); z-index:1000;">
        <div style="background:#fff; width:400px; margin:100px auto; padding:20px; border-radius:8px;">
            <h5>Đổi mật khẩu</h5>
            <form method="post" action="${pageContext.request.contextPath}/changePassword">
                <div class="form-group">
                    <label>Mật khẩu hiện tại:</label>
                    <input type="password" name="currentPassword" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>Mật khẩu mới:</label>
                    <input type="password" name="newPassword" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>Nhập lại mật khẩu mới:</label>
                    <input type="password" name="confirmNewPassword" class="form-control" required>
                </div>
                <div class="mt-3 text-end">
                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                    <button type="button" class="btn btn-secondary" onclick="closePasswordDialog()">Hủy</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    const editBtn = document.getElementById('editBtn');
    const updateBtn = document.getElementById('updateBtn');
    const avatarInput = document.getElementById('avatarInput');
    const avatarPreview = document.getElementById('avatarPreview');
    const editIcon = document.getElementById('editIcon');

    editBtn.addEventListener('click', () => {
        document.querySelectorAll('.editable-field').forEach(input => input.removeAttribute('readonly'));
        avatarInput.removeAttribute('disabled');
        updateBtn.style.display = 'inline-block';
        editIcon.style.display = '';
    });

    avatarInput.addEventListener('change', function () {
        const file = this.files[0];
        if (file) {
            avatarPreview.src = URL.createObjectURL(file); // Hiển thị trước ảnh mới
        }
    });
    function openPasswordDialog() {
        document.getElementById('passwordModal').style.display = 'block';
    }
    ;

    function closePasswordDialog() {
        document.getElementById('passwordModal').style.display = 'none';
    }
    ;
</script>