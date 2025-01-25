<%@ page import="lk.ijse.DTO.UserDTO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Chanuka Prabodha
  Date: 2025-01-25
  Time: 12:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Management</title>
    <link rel="stylesheet" href="css/admin-category.css">
</head>
<body>
<div class="container">
    <h1>User Management</h1>
    <%
        String message = request.getParameter("message");
        String error = request.getParameter("error");
    %>
    <%
        if (message != null) {
    %>
    <div style="color: green"><%=message%>
    </div>
    <%
        }
    %>
    <%
        if (error != null) {
    %>
    <div style="color: red"><%=error%>
    </div>
    <%
        }
    %>
    <form action="user" method="post">
        <input type="hidden" name="action" value="search">
        <input type="search" name="query" id="search" placeholder="Search Users" required>
        <button type="submit">Search</button>
    </form>
    <%
        List<UserDTO> users = (List<UserDTO>) request.getAttribute("users");
        if (users != null && !users.isEmpty()) {
    %>
    <table>
        <thead>
        <tr>
            <th>User ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Role</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <% for (UserDTO user : users) { %>
        <tr>
            <td><%= user.getUserID() %>
            </td>
            <td><%= user.getUserName() %>
            </td>
            <td><%= user.getEmail() %>
            </td>
            <td><%= user.isActive() ? "Active" : "Inactive" %>
            </td>
            <td>
                <form action="user" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="<%= user.isActive() ? "deactivate" : "activate" %>">
                    <input type="hidden" name="userId" value="<%= user.getUserID() %>">
                    <button type="submit"><%= user.isActive() ? "Deactivate" : "Activate" %></button>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
</div>
</body>
</html>
