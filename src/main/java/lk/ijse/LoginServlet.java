package lk.ijse;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: Chanuka Prabodha
 * Date: 2025-01-23
 * Time: 08:00 PM
 * Description:
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getParameter("action")) {
            case "login":
                log(req, resp);
                break;
            case "register":
                reg(req, resp);
                break;
        }
    }

    private void reg(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id;
        boolean idExists;
        do {
            id = IdGenerator.generateUserId("USR");
            idExists = checkIfIdExists(id);
        } while (idExists);
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String role = req.getParameter("role");

        String hashpw = BCrypt.hashpw(password, BCrypt.gensalt());

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO users VALUES (?,?,?,?,?,?)");
            pstm.setString(1, id);
            pstm.setString(2, userName);
            pstm.setString(3, hashpw);
            pstm.setString(4, email);
            pstm.setString(5, role);
            pstm.setBoolean(6, true);
            int i = pstm.executeUpdate();
            if (i > 0) {
                resp.sendRedirect("login.jsp?message=Registration Successful");
            } else {
                resp.sendRedirect("login.jsp?error=Registration Failed");
            }
            pstm.close();
            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            resp.sendRedirect("login.jsp?error=Registration Failed");
        }
    }

    private boolean checkIfIdExists(String id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT user_id FROM users WHERE user_id = ?");
            pstm.setString(1, id);
            boolean exists = pstm.executeQuery().next();
            connection.close();
            return exists;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void log(HttpServletRequest req, HttpServletResponse resp) {
        String userName = req.getParameter("un");
        String password = req.getParameter("pw");

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pstm = connection.prepareStatement("SELECT username, password, role, active FROM users WHERE username = ?");
            pstm.setString(1, userName);
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                boolean isPasswordMatch = BCrypt.checkpw(password, hashedPassword); // Compare hashed password
                boolean isActive = resultSet.getBoolean("active");

                if (!isPasswordMatch) {
                    resp.sendRedirect("login.jsp?error=Invalid Credentials");
                } else if (!isActive) {
                    resp.sendRedirect("login.jsp?error=Your account was suspended");
                } else {
                    String role = resultSet.getString("role");
                    String name = resultSet.getString("username");
                    req.getSession().setAttribute("userName", name);

                    if ("admin".equals(role)) {
                        resp.sendRedirect("admin-panel.jsp");
                    } else {
                        resp.sendRedirect("index.jsp");
                    }
                }
            } else {
                resp.sendRedirect("login.jsp?error=Invalid Credentials");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            try {
                resp.sendRedirect("login.jsp?error=Failed to login");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
