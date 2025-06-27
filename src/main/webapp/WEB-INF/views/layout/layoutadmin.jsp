<%-- 
    Document   : adminlayout
    Created on : Jun 27, 2025, 11:25:54 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title><c:out value="${pageTitle}" /></title>
        <!-- plugins:css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/src/vendors/feather/feather.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/src/vendors/mdi/css/materialdesignicons.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/src/vendors/ti-icons/css/themify-icons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/src/vendors/typicons/typicons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/src/vendors/simple-line-icons/css/simple-line-icons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/src/vendors/css/vendor.bundle.base.css">
        <!-- endinject -->
        <!-- Plugin css for this page -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/src/vendors/datatables.net-bs4/dataTables.bootstrap4.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/src/js/select.dataTables.min.css">
        <!-- End plugin css for this page -->
        <!-- inject:css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/src/css/vertical-layout-light/style.css">
        <!-- endinject -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/src/images/favicon.png" />

        <!-- CSS riêng cho từng page nếu có -->
        <c:if test="${not empty customCss}">
            <link href="${pageContext.request.contextPath}${customCss}" rel="stylesheet" />
        </c:if>
        <c:if test="${includeChartJs}">
            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        </c:if>
        <script>
            .avatar {
            width: 64px;
            height: 64px;
            border - radius: 50 % ;
            object - fit: cover;
            }
        </script>
    </head>
    <body>
        <div class="container-scroller"> 

            <!-- Navbar -->
            <jsp:include page="/WEB-INF/views/layout/admin_navbar.jsp" />

            <!-- partial -->
            <div class="container-fluid page-body-wrapper">
                <!-- Sidebar -->
                <jsp:include page="/WEB-INF/views/layout/admin_sidebar.jsp" />

                <!-- Main -->
                <div class="main-panel">
                    <div class="content-wrapper">
                        <jsp:include page="${pageContent}" />
                    </div>
                </div>
                <!-- main-panel ends -->
            </div>
            <!-- page-body-wrapper ends -->
        </div>
        <!-- container-scroller -->

        <!-- plugins:js -->
        <script src="${pageContext.request.contextPath}/src/vendors/js/vendor.bundle.base.js"></script>
        <!-- endinject -->
        <!-- Plugin js for this page -->
        <script src="${pageContext.request.contextPath}/src/vendors/chart.js/Chart.min.js"></script>
        <script src="${pageContext.request.contextPath}/src/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
        <script src="${pageContext.request.contextPath}/src/vendors/progressbar.js/progressbar.min.js"></script>

        <!-- End plugin js for this page -->
        <!-- inject:js -->
        <script src="${pageContext.request.contextPath}/src/js/off-canvas.js"></script>
        <script src="${pageContext.request.contextPath}/src/js/hoverable-collapse.js"></script>
        <script src="${pageContext.request.contextPath}/src/js/template.js"></script>
        <script src="${pageContext.request.contextPath}/src/js/settings.js"></script>
        <script src="${pageContext.request.contextPath}/src/js/todolist.js"></script>
        <!-- endinject -->
        <!-- Custom js for this page-->
        <script src="${pageContext.request.contextPath}/src/js/dashboard.js"></script>
        <script src="${pageContext.request.contextPath}/src/js/Chart.roundedBarCharts.js"></script>
        <!-- End custom js for this page-->
    </body>

</html>
