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
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        form {
            margin-bottom: 20px;
        }

        input[type="text"], input[type="search"], textarea {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        button {
            padding: 10px 20px;
            background-color: #28a745;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #218838;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table, th, td {
            border: 1px solid #ccc;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #f8f9fa;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Category Management</h1>
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
    <input type="search" id="search" placeholder="Search Categories">

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
