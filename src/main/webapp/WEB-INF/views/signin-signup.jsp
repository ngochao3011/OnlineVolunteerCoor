<!-- File: signin-signup.jsp -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<div id="container-login" class="container-login sign-in">
    <div class="row">
        <!-- SIGN UP FORM -->
        <div class="col align-items-center flex-col sign-up">
            <div class="form-wrapper align-items-center">
                <div class="form sign-up">
                    <form action="${pageContext.request.contextPath}/sign-up" method="post">
                        <h3>Đăng ký</h3>
                        <c:if test="${not empty errMessage}">
                            <div class="alert alert-danger">${errMessage}</div>
                        </c:if>
                        <div class="input-group">
                            <i class='bx bx-mail-send'></i>
                            <input type="email" name="email" placeholder="Email" required />
                        </div>
                        <div class="input-group">
                            <i class='bx bxs-user'></i>
                            <input type="text" name="hoTen" placeholder="Họ và tên" required />
                        </div>
                        <div class="input-group">
                            <i class='bx bxs-phone'></i>
                            <input type="text" name="sdt" placeholder="Số điện thoại" />
                        </div>
                        <div class="input-group">
                            <i class='bx bxs-lock-alt'></i>
                            <input type="password" name="matKhau" placeholder="Mật khẩu" required />
                        </div>
                        <div class="input-group">
                            <i class='bx bxs-lock-alt'></i>
                            <input type="password" name="xacNhanMatKhau" placeholder="Xác nhận mật khẩu" required />
                        </div>
                        <button type="submit">Đăng ký</button>
                        <p>
                            <span>Bạn đã có tài khoản?</span>
                            <b id="switch-link" onclick="event.preventDefault(); toggle();" class="pointer">
                                Đăng nhập ở đây
                            </b>
                        </p>
                    </form>
                </div>
            </div>
        </div>

        <!-- SIGN IN FORM -->
        <div class="col align-items-center flex-col sign-in">
            <div class="form-wrapper align-items-center">
                <div class="form sign-in">
                    <form action="${pageContext.request.contextPath}/sign-in" method="post">
                        <h3>Đăng nhập</h3>
                        <c:if test="${not empty errMessage}">
                            <div class="alert alert-danger">${errMessage}</div>
                        </c:if>
                        <div class="input-group">
                            <i class='bx bxs-user'></i>
                            <input type="text" name="username" placeholder="Email" required />
                        </div>
                        <div class="input-group">
                            <i class='bx bxs-lock-alt'></i>
                            <input type="password" name="password" placeholder="Mật khẩu" required />
                        </div>
                        <input type="hidden" name="redirect" value="${param.redirect}">
                        <button type="submit">Đăng nhập</button>
                        <p>
                            <b class="pointer" onclick="forgotPassword()">Quên mật khẩu?</b>
                        </p>
                        <p>
                            <span>Bạn chưa có tài khoản?</span>
                            <b id="switch-link" onclick="event.preventDefault(); toggle();" class="pointer">
                                Đăng ký ở đây
                            </b>
                        </p>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function forgotPassword() {
        const email = document.querySelector('input[name="username"]').value;
        if (!email) {
            alert("Vui lòng nhập email trước!");
            return;
        }

        const formData = new URLSearchParams();
        formData.append("email", email);

        fetch("${pageContext.request.contextPath}/forgot-password", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: formData
        })
                .then(response => response.text())
                .then(message => alert(message))
                .catch(err => alert("Lỗi gửi yêu cầu!"));
    }
</script>

<script src="${pageContext.request.contextPath}/src/js/signin-signup.js"></script>
