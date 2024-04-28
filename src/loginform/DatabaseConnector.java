package loginform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
      private static final String DB_URL = "jdbc:mysql://localhost:3306/CafeDb";
    private static final String username = "root";
    private static final String password = "6586093620352016";
    
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, username, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("driver loading error");
        }
        return connection;
    }
}