<%@ page import="lk.ijse.DTO.ProductDTO" %>
<%@ page import="java.util.List" %>
<%@ include file="header.jsp" %>

<div class="container mt-4">
    <div class="row mt-4">
        <h2 class="text-center" style="margin: 2.5rem 0 1rem 0">Shop All Products</h2>
        <%
            List<ProductDTO> allProducts = (List<ProductDTO>) request.getAttribute("featuredProducts");
            if (allProducts != null && !allProducts.isEmpty()) {
                for (ProductDTO product : allProducts) {
        %>
        <div class="col-md-3">
            <div class="card">
                <img src="<%= product.getImageUrl() %>" class="card-img-top" alt="<%= product.getName() %>">
                <div class="card-body">
                    <h5 class="card-title"><%= product.getName() %></h5>
                    <p class="card-text">Rs. <%= product.getPrice() %></p>
                    <a href="<%= request.getContextPath() %>/product-details.jsp?id=<%= product.getProductId() %>" class="btn btn-primary">View Details</a>
                </div>
            </div>
        </div>
        <%
            }
        } else {
        %>
        <p class="text-center text-danger">No products available at the moment.</p>
        <%
            }
        %>
    </div>
</div>

<%@ include file="footer.jsp" %>
