<%@ page import="lk.ijse.DTO.OrderDTO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Chanuka Prabodha
  Date: 2025-01-26
  Time: 09:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order Management</title>
  <link rel="stylesheet" type="text/css" href="css/admin-category.css">
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
  <h1>Order Management</h1>
  <a href="admin-panel.jsp" class="back-button">Admin Panel</a>

  <form action="order" method="get" class="search-form">
    <input type="text" name="query" placeholder="Search orders..." class="search-input">
    <input type="hidden" name="action" value="search">
    <button type="submit" class="search-button">Search</button>
  </form>

  <table class="admin-table">
    <thead>
    <tr>
      <th>Order ID</th>
      <th>Customer ID</th>
      <th>Order Date</th>
      <th>Total Amount</th>
      <th>Status</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<OrderDTO> orders = (List<OrderDTO>) request.getAttribute("orders");
      if (orders != null) {
        for (OrderDTO order : orders) {
    %>
    <tr>
      <td><%= order.getOrderId() %></td>
      <td><%= order.getCustomerId() %></td>
      <td><%= order.getOrderDate() %></td>
      <td><%= order.getTotalAmount() %></td>
      <td><%= order.getStatus() %></td>
      <td>
        <form action="order" method="post" class="status-form">
          <input type="hidden" name="action" value="updateStatus">
          <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
          <select name="status" class="status-select">
            <option value="pending" <%= "pending".equals(order.getStatus()) ? "selected" : "" %>>Pending</option>
            <option value="processing" <%= "processing".equals(order.getStatus()) ? "selected" : "" %>>Processing</option>
            <option value="shipped" <%= "shipped".equals(order.getStatus()) ? "selected" : "" %>>Shipped</option>
            <option value="delivered" <%= "delivered".equals(order.getStatus()) ? "selected" : "" %>>Delivered</option>
            <option value="cancelled" <%= "cancelled".equals(order.getStatus()) ? "selected" : "" %>>Cancelled</option>
          </select>
          <button type="submit" class="update-button">Update</button>
        </form>
      </td>
    </tr>
    <%
        }
      }
    %>
    </tbody>
  </table>
</div>
</body>
</html>
