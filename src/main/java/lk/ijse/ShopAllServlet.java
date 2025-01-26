package lk.ijse;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.DTO.ProductDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Chanuka Prabodha
 * Date: 2025-01-26
 * Time: 07:39 PM
 * Description:
 */

@WebServlet(name = "ShopAllServlet", urlPatterns = "/shopAll")
public class ShopAllServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProductDTO> productList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT product_id, name, description, price, image_url FROM products";
            try (PreparedStatement pstm = connection.prepareStatement(query);
                 ResultSet resultSet = pstm.executeQuery()) {

                while (resultSet.next()) {
                    productList.add(new ProductDTO(
                            resultSet.getString("product_id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getDouble("price"),
                            resultSet.getString("image_url")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Failed to load products. Please try again later.");
        }
        req.setAttribute("featuredProducts", productList);
        req.getRequestDispatcher("/shopAll.jsp").forward(req, resp);
    }
}
