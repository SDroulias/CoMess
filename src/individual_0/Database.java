package individual_0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/messaging?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "pantera666";
    
    private static Connection connection = null;
//    public static Connection getConnection() {
//        if (connection == null) {
//            try {
//                Class.forName(DRIVER);
//                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            } catch (SQLException | ClassNotFoundException ex) {
//                System.out.println(ex.getMessage());
//            }            
//        }
//        return connection;
//    }
    
    //creates a connection with the DB
    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
