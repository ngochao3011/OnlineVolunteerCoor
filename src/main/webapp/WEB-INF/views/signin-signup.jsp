<%-- 
    Document   : signin
    Created on : Jun 5, 2025, 1:05:09 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
         <link href="${pageContext.request.contextPath}/src/css/template-signin-signup.css" rel="stylesheet">
    </head>
    <body>
        <div id="container" class="container sign-in">
            <!-- FORM SECTION -->
            <div class="row">
                <!-- SIGN UP -->
                <div class="col align-items-center flex-col sign-up">
                    <div class="form-wrapper align-items-center">
                        <div class="form sign-up">
                            <form action="${pageContext.request.contextPath}/sign-up" method="post">
                                <div class="input-group">
                                        <i class='bx bx-mail-send'></i>
                                        <input type="email" name="email" placeholder="Email" required>
                                </div>
                                <div class="input-group">
                                    <i class='bx bxs-user'></i>
                                    <input type="text" name="hoTen" placeholder="Full name" required>
                                </div>
                                <div class="input-group">
                                    <i class='bx bxs-phone'></i>
                                    <input type="text" name="sdt" placeholder="Phone number">
                                </div>
                                <div class="input-group">
                                        <i class='bx bxs-lock-alt'></i>
                                        <input type="password" name="matKhau" placeholder="Password" required>
                                </div>
                                <div class="input-group">
                                        <i class='bx bxs-lock-alt'></i>
                                        <input type="password" placeholder="Confirm password" required>
                                </div>
                                <button type="submit">
                                        Sign up
                                </button>
                                <p>
                                        <span>
                                                Already have an account?
                                        </span>
                                        <b id="switch-link" href="${pageContext.request.contextPath}/sign-in" onclick="event.preventDefault(); toggle();" class="pointer">
                                                Sign in here
                                        </b>
                                </p>
                            </form>
                        </div>
                    </div>
                </div>
                <!-- END SIGN UP -->
                <!-- SIGN IN -->
                <div class="col align-items-center flex-col sign-in">
                    <div class="form-wrapper align-items-center">
                        <div class="form sign-in">
                            <form action="${pageContext.request.contextPath}/sign-in" method="post">
                                <div class="input-group">
                                        <i class='bx bxs-user'></i>
                                        <input type="text" name="email" placeholder="Email">
                                </div>
                                <div class="input-group">
                                        <i class='bx bxs-lock-alt'></i>
                                        <input type="password" name="matKhau" placeholder="Password">
                                </div>
                                <button>
                                        Sign in
                                </button>
                                <p>
                                        <b>
                                                Forgot password?
                                        </b>
                                </p>
                                <p>
                                        <span>
                                                Don't have an account?
                                        </span>
                                        <b id="switch-link" href="${pageContext.request.contextPath}/sign-up" onclick="event.preventDefault(); toggle();" class="pointer">
                                                Sign up here
                                        </b>
                                </p>
                            </form>
                        </div>
                    </div>
                    <div class="form-wrapper">

                    </div>
                </div>
                <!-- END SIGN IN -->
            </div>
            <!-- END FORM SECTION -->
            <!-- CONTENT SECTION -->
            <div class="row content-row">
                    <!-- SIGN IN CONTENT -->
                    <div class="col align-items-center flex-col">
                            <div class="text sign-in">
                                    <h2>
                                            Welcome
                                    </h2>

                            </div>
                            <div class="img sign-in">

                            </div>
                    </div>
                    <!-- END SIGN IN CONTENT -->
                    <!-- SIGN UP CONTENT -->
                    <div class="col align-items-center flex-col">
                            <div class="img sign-up">

                            </div>
                            <div class="text sign-up">
                                    <h2>
                                            Join with us
                                    </h2>

                            </div>
                    </div>
                    <!-- END SIGN UP CONTENT -->
            </div>
            <!-- END CONTENT SECTION -->
	</div>
        <!-- JAVASCRIPT FILES -->
        <script src="${pageContext.request.contextPath}/src/js/signin-signup.js"></script>
    </body>
</html>
