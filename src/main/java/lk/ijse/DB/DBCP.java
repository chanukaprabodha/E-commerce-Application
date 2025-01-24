package lk.ijse.DB;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Author: Chanuka Prabodha
 * Date: 2025-01-16
 * Time: 03:25 PM
 * Description:
 */
@WebListener
public class DBCP implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/ecommerce");
        ds.setUsername("root");
        ds.setPassword("Chanu@acc2002");
        ds.setMaxTotal(5);
        ds.setInitialSize(5);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("dataSource", ds);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        BasicDataSource ds = (BasicDataSource) servletContext.getAttribute("dataSource");
        try {
            ds.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
