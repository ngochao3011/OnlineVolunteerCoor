<%-- 
    Document   : add
    Created on : Jun 9, 2025, 8:13:29 PM
    Author     : HP
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add Event</title>
    </head>
    <body>
        <h2>Add New Event</h2>
        <form action="${pageContext.request.contextPath}/sukien/add" method="post">
            <label>Event Name:</label><input type="text" name="tenSuKien" required><br>
            <label>Description:</label><textarea name="moTa"></textarea><br>
            <label>Start Time:</label><input type="datetime-local" name="thoiGianBatDau" required><br>
            <label>End Time:</label><input type="datetime-local" name="thoiGianKetThuc" required><br>
            <label>Location:</label><input type="text" name="diaDiem" required><br>
            <label>Status:</label>
            <select name="trangThai">
                <option value="Sắp tới">Upcoming</option>
                <option value="Đang diễn ra">In Progress</option>
                <option value="Đã hoàn thành">Completed</option>
            </select><br>
            <input type="submit" value="Add">
        </form>
        <a href="${pageContext.request.contextPath}/sukien/list">Back</a>
    </body>
</html>
