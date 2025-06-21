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
                        <h3>Sign Up</h3>
                        <c:if test="${error != null && error != ''}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>
                        <div class="input-group">
                            <i class='bx bx-mail-send'></i>
                            <input type="email" name="email" placeholder="Email" required />
                        </div>
                        <div class="input-group">
                            <i class='bx bxs-user'></i>
                            <input type="text" name="hoTen" placeholder="Full name" required />
                        </div>
                        <div class="input-group">
                            <i class='bx bxs-phone'></i>
                            <input type="text" name="sdt" placeholder="Phone number" />
                        </div>
                        <div class="input-group">
                            <i class='bx bxs-lock-alt'></i>
                            <input type="password" name="matKhau" placeholder="Password" required />
                        </div>
                        <div class="input-group">
                            <i class='bx bxs-lock-alt'></i>
                            <input type="password" name="xacNhanMatKhau" placeholder="Confirm password" required />
                        </div>
                        <button type="submit">Sign up</button>
                        <p>
                            <span>Already have an account?</span>
                            <b id="switch-link" onclick="event.preventDefault(); toggle();" class="pointer">
                                Sign in here
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
                        <h3>Sign In</h3>
                        <c:if test="${error != null && error != ''}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>
                        <div class="input-group">
                            <i class='bx bxs-user'></i>
                            <input type="text" name="username" placeholder="Email" required />
                        </div>
                        <div class="input-group">
                            <i class='bx bxs-lock-alt'></i>
                            <input type="password" name="password" placeholder="Password" required />
                        </div>
                        <button type="submit">Sign In</button>
                        <p><b>Forgot password?</b></p>
                        <p>
                            <span>Don't have an account?</span>
                            <b id="switch-link" onclick="event.preventDefault(); toggle();" class="pointer">
                                Sign up here
                            </b>
                        </p>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/src/js/signin-signup.js"></script>
