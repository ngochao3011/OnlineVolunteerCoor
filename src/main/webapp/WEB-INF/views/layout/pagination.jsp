<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
            <c:url var="prevUrl" value="">
                <c:param name="page" value="${currentPage - 1}"/>
                <c:if test="${not empty keyword and keyword != ''}"><c:param name="keyword" value="${keyword}"/></c:if>
                <c:if test="${not empty location and location != ''}"><c:param name="location" value="${location}"/></c:if>
                <c:if test="${not empty trangThai and trangThai != ''}"><c:param name="trangThai" value="${trangThai}"/></c:if>
            </c:url>
            <a class="page-link" href="${prevUrl}">Trước</a> <!-- Sửa "Tr??c" thành "Trước" -->
        </li>
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:url var="pageUrl" value="">
                <c:param name="page" value="${i}"/>
                <c:if test="${not empty keyword and keyword != ''}"><c:param name="keyword" value="${keyword}"/></c:if>
                <c:if test="${not empty location and location != ''}"><c:param name="location" value="${location}"/></c:if>
                <c:if test="${not empty trangThai and trangThai != ''}"><c:param name="trangThai" value="${trangThai}"/></c:if>
            </c:url>
            <li class="page-item ${currentPage == i ? 'active' : ''}">
                <a class="page-link" href="${pageUrl}">${i}</a>
            </li>
        </c:forEach>
        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
            <c:url var="nextUrl" value="">
                <c:param name="page" value="${currentPage + 1}"/>
                <c:if test="${not empty keyword and keyword != ''}"><c:param name="keyword" value="${keyword}"/></c:if>
                <c:if test="${not empty location and location != ''}"><c:param name="location" value="${location}"/></c:if>
                <c:if test="${not empty trangThai and trangThai != ''}"><c:param name="trangThai" value="${trangThai}"/></c:if>
            </c:url>
            <a class="page-link" href="${nextUrl}">Sau</a>
        </li>
    </ul>
</nav>
