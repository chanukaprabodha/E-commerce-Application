<%@ include file="header.jsp" %>

<div class="container mt-4">
    <h2>Shop All Products</h2>
    <div class="row">
        <!-- Dynamic Product List -->
        <c:forEach items="${products}" var="product">
            <div class="col-md-3">
                <div class="card">
                    <img src="${product.image}" class="card-img-top" alt="${product.name}">
                    <div class="card-body">
                        <h5 class="card-title">${product.name}</h5>
                        <p class="card-text">Rs. ${product.price}</p>
                        <a href="product-details.jsp?id=${product.id}" class="btn btn-primary">View Details</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="footer.jsp" %>
