<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
            <a class="page-link" href="?keyword=${keyword}&location=${location}&trangThai=${trangThai}&page=${currentPage - 1}">Trước</a>
        </li>
        <c:forEach begin="1" end="${totalPages}" var="i">
            <li class="page-item ${currentPage == i ? 'active' : ''}">
                <a class="page-link" href="?keyword=${keyword}&location=${location}&trangThai=${trangThai}&page=${i}">${i}</a>
            </li>
        </c:forEach>
        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
            <a class="page-link" href="?keyword=${keyword}&location=${location}&trangThai=${trangThai}&page=${currentPage + 1}">Sau</a>
        </li>
    </ul>
</nav>