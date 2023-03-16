package database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseRepository {
    private static String URL; // sửa lại tên của csdl
    private static String USER;// mặc định của mysql
    private static String PASS;// do cài đặt khi cài đặt mysql

    public static BaseRepository instance;

    public static BaseRepository getInstance() {
        if (instance == null) {
            instance = new BaseRepository();
        }
        return instance;
    }

    public BaseRepository() {
        Dotenv dotenv = Dotenv.load();
        URL = dotenv.get("DB_URL");
        USER = dotenv.get("DB_USER");
        PASS = dotenv.get("DB_PASSWORD");
    }

    public static Connection getConnectDB() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
