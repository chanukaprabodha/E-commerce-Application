package lk.ijse;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lk.ijse.DTO.CategoryDTO;
import lk.ijse.DTO.ProductDTO;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Chanuka Prabodha
 * Date: 2025-01-25
 * Time: 09:37 PM
 * Description:
 */
@WebServlet(name = "ProductServlet", urlPatterns = "/product")
@MultipartConfig
public class ProductServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null && action.equals("search")) {
            performSearch(req, resp);
        } else if (action != null && action.equals("listFeatured")) {
            listFeaturedProducts(req, resp);
        } else {
            listProducts(req, resp);
        }
    }

    private void performSearch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        if (query != null) {
            query = query.trim();
            ArrayList<ProductDTO> productList = new ArrayList<>();
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement pstm = connection.prepareStatement("SELECT * FROM products WHERE name LIKE ? OR description LIKE ?");
                pstm.setString(1, "%" + query + "%");
                pstm.setString(2, "%" + query + "%");
                ResultSet resultSet = pstm.executeQuery();
                while (resultSet.next()) {
                    productList.add(new ProductDTO(
                            resultSet.getString("product_id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getDouble("price"),
                            resultSet.getString("image_url"),
                            resultSet.getString("category_id")
                    ));
                }
                req.setAttribute("products", productList);
                req.getRequestDispatcher("product.jsp").forward(req, resp);
                resultSet.close();
                pstm.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                resp.sendRedirect("product?error=Failed to search products");
            }
        } else {
            resp.sendRedirect("product?error=Search query cannot be null");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "create":
                createProduct(req, resp);
                break;
            case "update":
                updateProduct(req, resp);
                break;
            case "delete":
                deleteProduct(req, resp);
                break;
            case "search":
                searchProducts(req, resp);
                break;
            case "listFeatured":
                listFeaturedProducts(req, resp);
                break;
        }
    }

    private void searchProducts(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String query = req.getParameter("query");
        if (query != null) {
            query = query.trim();
            resp.sendRedirect("product?action=search&query=" + URLEncoder.encode(query, "UTF-8"));
        } else {
            resp.sendRedirect("product?error=Search query cannot be null");
        }
    }

    private void listProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<ProductDTO> productList = new ArrayList<>();
        ArrayList<CategoryDTO> categoryList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM products");
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                productList.add(new ProductDTO(
                        resultSet.getString("product_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image_url"),
                        resultSet.getString("category_id")
                ));
            }
            pstm = connection.prepareStatement("SELECT * FROM categories");
            resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                categoryList.add(new CategoryDTO(
                        resultSet.getString("category_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description")
                ));
            }
            req.setAttribute("products", productList);
            req.setAttribute("categories", categoryList);
            req.getRequestDispatcher("product.jsp").forward(req, resp);
            pstm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("productId");
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM products WHERE product_id = ?");
            pstm.setString(1, id);
            int i = pstm.executeUpdate();
            if (i > 0) {
                resp.sendRedirect("product?message=Product Deleted");
            } else {
                resp.sendRedirect("product?error=Failed to delete product");
            }
            pstm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("product?error=Failed to delete product");
        }
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        double price = Double.parseDouble(req.getParameter("price"));
        String categoryId = req.getParameter("categoryId");
        Part imagePart = req.getPart("image");

        String imageUrl;
        if (imagePart != null && imagePart.getSize() > 0) {
            imageUrl = uploadImage(imagePart);
        } else {
            // Retain the existing image URL if no new image is uploaded
            imageUrl = getCurrentImageUrl(id);
        }

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE products SET name = ?, description = ?, price = ?, image_url = ?, category_id = ? WHERE product_id = ?");
            pstm.setString(1, name);
            pstm.setString(2, description);
            pstm.setDouble(3, price);
            pstm.setString(4, imageUrl);
            pstm.setString(5, categoryId);
            pstm.setString(6, id);
            int i = pstm.executeUpdate();
            if (i > 0) {
                resp.sendRedirect("product?message=Product Updated");
            } else {
                resp.sendRedirect("product?error=Failed to update product");
            }
            pstm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("product?error=Failed to update product");
        }
    }

    private String getCurrentImageUrl(String productId) {
        String imageUrl = null;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT image_url FROM products WHERE product_id = ?");
            pstm.setString(1, productId);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                imageUrl = resultSet.getString("image_url");
            }
            resultSet.close();
            pstm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }

    private void createProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id = IdGenerator.generateUserId("PROD");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        double price = Double.parseDouble(req.getParameter("price"));
        String categoryId = req.getParameter("categoryId");
        Part imagePart = req.getPart("image");
        String imageUrl = uploadImage(imagePart);

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO products (product_id, name, description, price, image_url, category_id) VALUES (?,?,?,?,?,?)");
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, description);
            pstm.setDouble(4, price);
            pstm.setString(5, imageUrl);
            pstm.setString(6, categoryId);
            int i = pstm.executeUpdate();
            if (i > 0) {
                resp.sendRedirect("product?message=Product Created");
            } else {
                resp.sendRedirect("product?error=Failed to create product");
            }
            pstm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("product?error=Failed to create product");
        }
    }

    private String uploadImage(Part imagePart) throws IOException {
        String fileName = imagePart.getSubmittedFileName();
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        String filePath = uploadPath + File.separator + fileName;
        imagePart.write(filePath);
        return "uploads/" + fileName;
    }

    private void listFeaturedProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProductDTO> productList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM products");
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                productList.add(new ProductDTO(
                        resultSet.getString("product_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image_url")
                ));
            }
            req.setAttribute("featuredProducts", productList);
            pstm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
