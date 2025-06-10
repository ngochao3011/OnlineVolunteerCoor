<%-- 
    Document   : edit
    Created on : Jun 9, 2025, 8:13:40 PM
    Author     : HP
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Edit Event</title>
    </head>
    <body>
        <h2>Edit Event</h2>
        <form action="${pageContext.request.contextPath}/sukien/edit" method="post">
            <input type="hidden" name="maSuKien" value="${suKien.maSuKien}">
            <label>Event Name:</label><input type="text" name="tenSuKien" value="${suKien.tenSuKien}" required><br>
            <label>Description:</label><textarea name="moTa">${suKien.moTa}</textarea><br>
            <label>Start Time:</label><input type="datetime-local" name="thoiGianBatDau" value="${suKien.thoiGianBatDau}" required><br>
            <label>End Time:</label><input type="datetime-local" name="thoiGianKetThuc" value="${suKien.thoiGianKetThuc}" required><br>
            <label>Location:</label><input type="text" name="diaDiem" value="${suKien.diaDiem}" required><br>
            <label>Status:</label>
            <select name="trangThai">
                <option value="Sắp tới" ${suKien.trangThai == 'Sắp tới' ? 'selected' : ''}>Upcoming</option>
                <option value="Đang diễn ra" ${suKien.trangThai == 'Đang diễn ra' ? 'selected' : ''}>In Progress</option>
                <option value="Đã hoàn thành" ${suKien.trangThai == 'Đã hoàn thành' ? 'selected' : ''}>Completed</option>
            </select><br>
            <input type="submit" value="Update">
        </form>
        <a href="${pageContext.request.contextPath}/sukien/list">Back</a>
    </body>
</html>