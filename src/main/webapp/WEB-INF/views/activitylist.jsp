<%-- 
    Document   : activitylist
    Created on : Jun 8, 2025, 2:48:28 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Activity List</title>
    
    <!-- CSS FILES -->
    <link href="${pageContext.request.contextPath}/src/css/bootstrap.min.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/src/css/bootstrap-icons.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/src/css/template-homepage.css" rel="stylesheet">
</head>
<body>

    <jsp:include page="layout/header.jsp" />
    
    <jsp:include page="layout/navbar.jsp" />

    <section class="blog-section spad">
        <div class="container">
            <div class="section-title text-center">
                <h2>Danh sách Hoạt động Tình nguyện</h2>
                <p>Cùng tham gia các hoạt động ý nghĩa cho cộng đồng</p>
            </div>
            <div class="row">
                <c:forEach var="activity" items="${activities}">
                    <div class="col-lg-4 col-md-6 mb-4">
                        <div class="blog-item">
                            <div class="bi-thumb">
                                <img src="${pageContext.request.contextPath}/images/${activity.image}" alt="${activity.title}" style="width:100%; height:200px; object-fit:cover;">
                            </div>
                            <div class="bi-content">
                                <h5><a href="${pageContext.request.contextPath}/activity/detail?id=${activity.id}">${activity.title}</a></h5>
                                <p>
                                    <strong>Thời gian:</strong> ${activity.startDate} - ${activity.endDate}<br/>
                                    <strong>Địa điểm:</strong> ${activity.location}
                                </p>
                                <a href="${pageContext.request.contextPath}/activity/detail?id=${activity.id}" class="readmore">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <c:if test="${empty activities}">
                <p class="text-center mt-4">Hiện chưa có hoạt động nào.</p>
            </c:if>
        </div>
    </section>

    <jsp:include page="layout/footer.jsp" />
</body>
</html>

