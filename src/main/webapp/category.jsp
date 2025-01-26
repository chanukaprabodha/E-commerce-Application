<%@ page import="java.util.List" %>
<%@ page import="lk.ijse.DTO.CategoryDTO" %><%--
  Created by IntelliJ IDEA.
  User: Chanuka Prabodha
  Date: 2025-01-24
  Time: 01:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Category</title>
    <link rel="stylesheet" href="css/admin-category.css">
    <style>
        .back-button {
            position: absolute;
            top: 35px;
            right: 150px;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
        }

        .back-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Category Management</h1>
    <a href="admin-panel.jsp" class="back-button">Admin Panel</a>
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
    <form action="category" method="post">
        <input type="hidden" name="action" value="create">
        <input type="text" name="name" placeholder="Category Name" required>
        <textarea name="description" placeholder="Category Description" required></textarea>
        <button type="submit">Add Category</button>
    </form>
    <form action="category" method="post">
        <input type="hidden" name="action" value="search">
        <input type="search" name="query" id="search" placeholder="Search Categories" required>
        <button type="submit">Search</button>
    </form>

    <%
        List<CategoryDTO> categories = (List<CategoryDTO>) request.getAttribute("categories");
        if (categories != null && !categories.isEmpty()) {
    %>
    <table>
        <thead>
        <tr>
            <th>Category ID</th>
            <th>Category Name</th>
            <th>Category Description</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (CategoryDTO category : categories) {%>
        <tr>
            <td><%= category.getCategoryId()%>
            </td>
            <td><%= category.getName()%>
            </td>
            <td><%= category.getDescription()%>
            </td>
            <td>
                <form action="category" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="<%= category.getCategoryId()%>">
                    <input type="text" name="name" value="<%= category.getName()%>" required>
                    <textarea name="description" required><%= category.getDescription()%></textarea>
                    <button type="submit">Edit</button>
                </form>
                <form action="category" method="post" style="display:inline;" onsubmit="return confirmDelete();">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="categoryId" value="<%= category.getCategoryId()%>">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
        <%}%>
        </tbody>
    </table>
    <%}%>
</div>
<script>
    function confirmDelete() {
        return confirm("Are you sure you want to delete this category?");
    }
</script>
</body>
</html>
