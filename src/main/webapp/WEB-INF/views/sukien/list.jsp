<%-- 
    Document   : list
    Created on : Jun 9, 2025, 8:13:20 PM
    Author     : HP
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Event List</title>
    </head>
    <body>
        <h2>Event List</h2>
        <a href="${pageContext.request.contextPath}/sukien/add">Add New Activity</a>
        <table border="1">
            <tr>
                <th>Event ID</th>
                <th>Event Name</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Location</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="suKien" items="${danhSachSuKien}">
                <tr>
                    <td>${suKien.maSuKien}</td>
                    <td>${suKien.tenSuKien}</td>
                    <td>${suKien.thoiGianBatDau}</td>
                    <td>${suKien.thoiGianKetThuc}</td>
                    <td>${suKien.diaDiem}</td>
                    <td>${suKien.trangThai}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/sukien/edit/${suKien.maSuKien}">Edit</a>
                        <a href="${pageContext.request.contextPath}/sukien/delete/${suKien.maSuKien}" onclick="return confirm('Are you sure?')">Delete</a>
                        <form action="${pageContext.request.contextPath}/sukien/update" method="post" style="display:inline;">
                            <input type="hidden" name="maSuKien" value="${suKien.maSuKien}">
                            <select name="trangThai" onchange="this.form.submit()">
                                <option value="Sắp tới" ${suKien.trangThai == 'Sắp tới' ? 'selected' : ''}>Upcoming</option>
                                <option value="Đang diễn ra" ${suKien.trangThai == 'Đang diễn ra' ? 'selected' : ''}>In Progress</option>
                                <option value="Đã hoàn thành" ${suKien.trangThai == 'Đã hoàn thành' ? 'selected' : ''}>Completed</option>
                            </select>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
