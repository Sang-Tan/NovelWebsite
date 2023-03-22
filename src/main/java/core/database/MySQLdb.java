package core.database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class MySQLdb {
<<<<<<< Updated upstream
    private static String URL; // sửa lại tên của csdl
    private static String USER;// mặc định của mysql
    private static String PASS;// do cài đặt khi cài đặt mysql
=======
    private String URL; // sửa lại tên của csdl
    private String USER;// mặc định của mysql
    private String PASS;// do cài đặt khi cài đặt mysql
>>>>>>> Stashed changes

    public static MySQLdb instance;

    public static MySQLdb getInstance() {
        if (instance == null) {
            instance = new MySQLdb();
        }
        return instance;
    }

    public MySQLdb() {
        Dotenv dotenv = Dotenv.load();
        URL = dotenv.get("DB_URL");
        USER = dotenv.get("DB_USER");
        PASS = dotenv.get("DB_PASSWORD");

    }

    public Connection getConnectDB() {
        Connection connection = null;
        // Load the JDBC driver
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

//             Establish a connection to the database
            String url = "jdbc:mysql://localhost:3306/novelweb";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load JDBC driver");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Could not connect to database");
            e.printStackTrace();
        }
        return connection;
    }
}
