<%@ include file="header.jsp" %>

<div id="homepageCarousel" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner">
        <div class="carousel-item active">
            <img src="assets/images/banner_2.jpg" class="d-block w-100" alt="Banner 1">
            <div class="carousel-caption">
                Welcome to TechSphere
            </div>
        </div>
        <div class="carousel-item">
            <img src="assets/images/banner_1.jpg" class="d-block w-100" alt="Banner 2">
            <div class="carousel-caption">
                Discover Our Products
            </div>
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#homepageCarousel" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#homepageCarousel" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>

<div class="container mt-4">
    <!-- Featured Products Section -->
    <div class="row mt-4">
        <h2 class="text-center">Featured Products</h2>
        <div class="col-md-3">
            <div class="card">
                <img src="images/product1.jpg" class="card-img-top" alt="Product 1">
                <div class="card-body">
                    <h5 class="card-title">Product Name</h5>
                    <p class="card-text">Rs. 10,000.00</p>
                    <a href="product-details.jsp?id=1" class="btn btn-primary">View Details</a>
                </div>
            </div>
        </div>
        <!-- Repeat for more products -->
    </div>
</div>

<%@ include file="footer.jsp" %>
