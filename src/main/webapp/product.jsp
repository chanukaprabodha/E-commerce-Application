<%@ page import="lk.ijse.DTO.ProductDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="lk.ijse.DTO.CategoryDTO" %><%--
  Created by IntelliJ IDEA.
  User: Chanuka Prabodha
  Date: 2025-01-25
  Time: 09:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product Management</title>
  <link rel="stylesheet" href="css/admin-category.css">
  <style>
    .form-group {
      margin-bottom: 15px;
    }
    .form-group label {
      display: block;
      margin-bottom: 5px;
    }
    .form-group input[type="text"],
    .form-group input[type="number"],
    .form-group input[type="file"],
    .form-group select {
      width: 100%;
      padding: 8px;
      box-sizing: border-box;
    }
    .form-group input[type="file"] {
      padding: 3px;
    }
    .form-group select {
      height: 38px;
    }
    .form-group button {
      padding: 10px 15px;
      background-color: #4CAF50;
      color: white;
      border: none;
      cursor: pointer;
    }
    .form-group button:hover {
      background-color: #45a049;
    }
    .form-row {
      display: flex;
      gap: 10px;
    }
    .form-row .form-group {
      flex: 1;
    }
    .form-group.image {
      margin-left: 75px;
    }
    .form-group.image input[type="file"] {
      margin-top: 10px;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Product Management</h1>
  <%
    String message = request.getParameter("message");
    String error = request.getParameter("error");
  %>
  <%
    if (message != null) {
  %>
  <div style="color: green"><%=message%></div>
  <%
    }
  %>
  <%
    if (error != null) {
  %>
  <div style="color: red"><%=error%></div>
  <%
    }
  %>
  <form action="product" method="post" enctype="multipart/form-data">
    <input type="hidden" name="action" value="create">
    <div class="form-group">
      <label for="name">Product Name</label>
      <input type="text" name="name" id="name" placeholder="Product Name" required>
    </div>
    <div class="form-group">
      <label for="description">Description</label>
      <input type="text" name="description" id="description" placeholder="Description" required>
    </div>
    <div class="form-row">
      <div class="form-group">
        <label for="price">Price</label>
        <input type="number" name="price" id="price" placeholder="Price" required>
      </div>
      <div class="form-group image">
        <label for="image">Image</label>
        <input type="file" name="image" id="image" required>
      </div>
      <div class="form-group">
        <label for="categoryId">Category</label>
        <select name="categoryId" id="categoryId" required>
          <option value="">Select Category</option>
          <%
            List<CategoryDTO> categories = (List<CategoryDTO>) request.getAttribute("categories");
            if (categories != null) {
              for (CategoryDTO category : categories) {
          %>
          <option value="<%= category.getCategoryId() %>"><%= category.getName() %></option>
          <%
              }
            }
          %>
        </select>
      </div>
    </div>
    <div class="form-group">
      <button type="submit">Add Product</button>
    </div>
  </form>
  <form action="product" method="post">
    <input type="hidden" name="action" value="search">
    <input type="search" name="query" id="search" placeholder="Search Products" required>
    <button type="submit">Search</button>
  </form>
  <%
    List<ProductDTO> products = (List<ProductDTO>) request.getAttribute("products");
    if (products != null && !products.isEmpty()) {
  %>
  <table>
    <thead>
    <tr>
      <th>Product ID</th>
      <th>Name</th>
      <th>Description</th>
      <th>Price</th>
      <th>Image</th>
      <th>Category</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <% for (ProductDTO product : products) { %>
    <tr>
      <td><%= product.getProductId() %></td>
      <td><%= product.getName() %></td>
      <td><%= product.getDescription() %></td>
      <td><%= product.getPrice() %></td>
      <td><img src="<%= product.getImageUrl() %>" alt="Product Image" width="100"></td>
      <td><%= product.getCategoryId() %></td>
      <td>
        <form action="product" method="post" style="display:inline;">
          <input type="hidden" name="action" value="delete">
          <input type="hidden" name="productId" value="<%= product.getProductId() %>">
          <button type="submit">Delete</button>
        </form>
        <form action="product" method="post" enctype="multipart/form-data" style="display:inline;">
          <input type="hidden" name="action" value="update">
          <input type="hidden" name="id" value="<%= product.getProductId() %>">
          <input type="text" name="name" value="<%= product.getName() %>" required>
          <input type="text" name="description" value="<%= product.getDescription() %>" required>
          <input type="number" name="price" value="<%= product.getPrice() %>" required>
          <input type="file" name="image">
          <select name="categoryId" required>
            <option value="">Select Category</option>
            <%
              if (categories != null) {
                for (CategoryDTO category : categories) {
            %>
            <option value="<%= category.getCategoryId() %>" <%= category.getCategoryId().equals(product.getCategoryId()) ? "selected" : "" %>><%= category.getName() %></option>
            <%
                }
              }
            %>
          </select>
          <button type="submit">Update</button>
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
