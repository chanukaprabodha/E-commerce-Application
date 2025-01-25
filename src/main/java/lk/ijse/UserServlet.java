package lk.ijse;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.DTO.UserDTO;
import lk.ijse.Entity.User;

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
 * Date: 2025-01-23
 * Time: 07:58 PM
 * Description:
 */
@WebServlet(name = "UserServlet", urlPatterns = "/user")
public class UserServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String query = req.getParameter("query");

        if (action != null && action.equals("search") && query != null && !query.isEmpty()) {
            performSearch(req, resp, query);
        } else {
            listUsers(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) action = "list"; // Default action

        switch (action) {
            case "activate":
                activateUser(req, resp);
                break;
            case "deactivate":
                deactivateUser(req, resp);
                break;
            case "search":
                searchUsers(req, resp);
                break;
        }
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDTO> userList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                userList.add(new UserDTO(
                        resultSet.getString("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("active")
                ));
            }
            req.setAttribute("users", userList);
            req.getRequestDispatcher("user.jsp").forward(req, resp);
            pstm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void activateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("userId");

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE users SET active = TRUE WHERE user_id = ?");
            pstm.setString(1, id);
            int i = pstm.executeUpdate();
            if (i > 0) {
                resp.sendRedirect("user?message=User Activated");
            } else {
                resp.sendRedirect("user?error=Failed to activate user");
            }
            pstm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("user?error=Failed to activate user");
        }
    }

    private void deactivateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("userId");

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE users SET active = FALSE WHERE user_id = ?");
            pstm.setString(1, id);
            int i = pstm.executeUpdate();
            if (i > 0) {
                resp.sendRedirect("user?message=User Deactivated");
            } else {
                resp.sendRedirect("user?error=Failed to deactivate user");
            }
            pstm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("user?error=Failed to deactivate user");
        }
    }

    private void searchUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String query = req.getParameter("query");
        resp.sendRedirect("user?action=search&query=" + URLEncoder.encode(query, "UTF-8"));
    }

    private void performSearch(HttpServletRequest req, HttpServletResponse resp, String query) throws ServletException, IOException {
        List<UserDTO> userList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM users WHERE username LIKE ? OR email LIKE ?");
            pstm.setString(1, "%" + query + "%");
            pstm.setString(2, "%" + query + "%");
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                userList.add(new UserDTO(
                        resultSet.getString("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("active")
                ));
            }
            req.setAttribute("users", userList);
            req.getRequestDispatcher("user.jsp").forward(req, resp);
            pstm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
