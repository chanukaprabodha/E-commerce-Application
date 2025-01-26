package lk.ijse;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.DTO.OrderDTO;

import javax.sql.DataSource;
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
 * Date: 2025-01-26
 * Time: 09:51 AM
 * Description:
 */
@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String query = req.getParameter("query");

        if (action != null && action.equals("search") && query != null && !query.isEmpty()) {
            performSearch(req, resp, query);
        } else {
            listOrders(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) action = "list"; // Default action

        switch (action) {
            case "updateStatus":
                updateOrderStatus(req, resp);
                break;
            case "search":
                searchOrders(req, resp);
                break;
        }
    }

    private void searchOrders(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String query = req.getParameter("query");
        resp.sendRedirect("order?action=search&query=" + URLEncoder.encode(query, "UTF-8"));
    }

    private void listOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<OrderDTO> orderList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM orders");
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                orderList.add(new OrderDTO(
                        resultSet.getString("order_id"),
                        resultSet.getString("customer_id"),
                        resultSet.getString("order_date"),
                        resultSet.getDouble("total_amount"),
                        resultSet.getString("status") // Fetch status
                ));
            }
            req.setAttribute("orders", orderList);
            req.getRequestDispatcher("order.jsp").forward(req, resp);
            pstm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateOrderStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String orderId = req.getParameter("orderId");
        String status = req.getParameter("status");

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE orders SET status = ? WHERE order_id = ?");
            pstm.setString(1, status);
            pstm.setString(2, orderId);
            int i = pstm.executeUpdate();
            if (i > 0) {
                resp.sendRedirect("order?message=Order Status Updated");
            } else {
                resp.sendRedirect("order?error=Failed to update order status");
            }
            pstm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("order?error=Failed to update order status");
        }
    }

    private void performSearch(HttpServletRequest req, HttpServletResponse resp, String query) throws ServletException, IOException {
        List<OrderDTO> orderList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM orders WHERE order_id LIKE ? OR customer_id LIKE ?");
            pstm.setString(1, "%" + query + "%");
            pstm.setString(2, "%" + query + "%");
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                orderList.add(new OrderDTO(
                        resultSet.getString("order_id"),
                        resultSet.getString("customer_id"),
                        resultSet.getString("order_date"),
                        resultSet.getDouble("total_amount"),
                        resultSet.getString("status") // Fetch status
                ));
            }
            req.setAttribute("orders", orderList);
            req.getRequestDispatcher("order.jsp").forward(req, resp);
            pstm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
