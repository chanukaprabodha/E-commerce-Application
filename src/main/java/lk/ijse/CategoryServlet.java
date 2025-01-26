package lk.ijse;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.DTO.CategoryDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Author: Chanuka Prabodha
 * Date: 2025-01-24
 * Time: 03:39 PM
 * Description:
 */
@WebServlet(name = "CategoryServlet", urlPatterns = "/category")
public class CategoryServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null && action.equals("search")) {
            performSearch(req, resp);
        } else {
            listCategories(req, resp);
        }
    }

    private void performSearch(HttpServletRequest req, HttpServletResponse resp) {
        // Search for categories
        ArrayList<CategoryDTO> categoryList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM categories WHERE name LIKE ?");
            pstm.setString(1, "%" + req.getParameter("query") + "%");
            var resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                categoryList.add(new CategoryDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));
            }
            req.setAttribute("categories", categoryList);
            req.getRequestDispatcher("category.jsp").forward(req, resp);
            pstm.close();
            connection.close();
        } catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        System.out.println(action);

        switch (action) {
            case "create":
                createCategory(req, resp);
                break;
            case "update":
                updateCategory(req, resp);
                break;
            case "delete":
                deleteCategory(req, resp);
                break;
            case "search":
                searchCategories(req, resp);
                break;
        }
    }

    private void searchCategories(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Search for categories
        String query = req.getParameter("query");
        resp.sendRedirect("category?action=search&query=" + URLEncoder.encode(query, "UTF-8"));
    }

    private void listCategories(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // List all the categories from the database
        ArrayList<CategoryDTO> categoryList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM categories");
            var resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                categoryList.add(new CategoryDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));
            }
            req.setAttribute("categories", categoryList);
            req.getRequestDispatcher("category.jsp").forward(req, resp);
            pstm.close();
            connection.close();
        } catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("category.jsp");
    }

    private void deleteCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("categoryId");
        // Delete the category from the database
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM categories WHERE category_id = ?");
            pstm.setString(1, id);
            int i = pstm.executeUpdate();
            if (i > 0) {
                resp.sendRedirect("category?message=Category Deleted");
            } else {
                resp.sendRedirect("category?error=Failed to delete category");
            }
            pstm.close();
            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            resp.sendRedirect("category?error=Failed to delete category");
        }
    }

    private void updateCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        // Update the category in the database
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE categories SET name = ?, description = ? WHERE category_id = ?");
            pstm.setString(1, name);
            pstm.setString(2, description);
            pstm.setString(3, id);
            int i = pstm.executeUpdate();
            if (i > 0) {
                resp.sendRedirect("category?message=Category Updated");
            } else {
                resp.sendRedirect("category?error=Failed to update category");
            }
            pstm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("category?error=Failed to update category");
        }
    }

    private void createCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id;
        boolean idExists;
        do {
            id = IdGenerator.generateUserId("CATE");
            idExists = checkIfIdExists(id);
        } while (idExists);
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        // Save the category to the database
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO categories VALUES (?,?,?)");
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, description);
            int i = pstm.executeUpdate();
            if (i > 0) {
                resp.sendRedirect("category?message=Category Created");
            } else {
                resp.sendRedirect("category?error=Failed to create category");
            }
            pstm.close();
            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            resp.sendRedirect("category?error=Failed to create category");
        }
    }

    private boolean checkIfIdExists(String id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT category_id FROM categories WHERE category_id = ?");
            pstm.setString(1, id);
            boolean exists = pstm.executeQuery().next();
            connection.close();
            return exists;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
