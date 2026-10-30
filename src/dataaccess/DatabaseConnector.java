package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/classmanagement";
    private static final String USER = "root";
    private static final String PASSWORD = "Work-Harder01012k5";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL Connector is not found!");
            throw new SQLException("Driver MySQL is not available!", e);
        } catch (SQLException e) {
            System.err.println("Error when connecting CSDL: " + e.getMessage());
            throw e;
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error when closing connection: " + e.getMessage());
            }
        }
    }
}