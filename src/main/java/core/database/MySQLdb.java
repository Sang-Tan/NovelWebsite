package core.database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.List;

public class MySQLdb {
    private String URL; // sửa lại tên của csdl
    private String USER;// mặc định của mysql
    private String PASS;// do cài đặt khi cài đặt mysql

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

    public Connection getConnectDB() throws SQLException {
        Connection connection;
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(URL, USER, PASS);
        return connection;
    }

    public ResultSet query(String sql) throws SQLException {
        Connection connection = getConnectDB();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    public ResultSet query(String sql, Object[] params) throws SQLException {
        Connection connection = getConnectDB();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    public void execute(String sql) throws SQLException {
        Connection connection = getConnectDB();
        Statement statement = connection.createStatement();
        statement.execute(sql);
        connection.close();
    }

    public void execute(String sql, Object[] params) throws SQLException {
        Connection connection = getConnectDB();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        preparedStatement.execute();
        connection.close();
    }
}
