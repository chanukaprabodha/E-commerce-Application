<%@ page import="lk.ijse.DTO.ProductDTO" %>
<%@ page import="java.util.List" %>
<%@ include file="header.jsp" %>

<div id="homepageCarousel" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner">
        <div class="carousel-item active">
            <img src="assets/images/banner_2.jpg" class="d-block" alt="Banner 1">
            <div class="carousel-caption">
                Welcome to TechSphere
            </div>
        </div>
        <div class="carousel-item">
            <img src="assets/images/banner_1.jpg" class="d-block" alt="Banner 2">
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

<%@ include file="footer.jsp" %>
