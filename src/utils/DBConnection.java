package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        try {
            // Load the MariaDB JDBC driver

            Class.forName("org.mariadb.jdbc.Driver");

            return DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/coinbooking", "tom", "your_password");
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Driver not found", e);
        }
    }
}