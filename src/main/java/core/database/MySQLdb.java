package core.database;

import core.Pair;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.Collection;
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


    //region SELECT OPERATIONS
    public ResultSet select(String sql) throws SQLException {
        Connection connection = getConnectDB();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    public ResultSet select(String sql, Object[] params) throws SQLException {
        Connection connection = getConnectDB();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
    //endregion


    //region UPDATE AND DELETE OPERATIONS
    public void executeOnce(Connection connection, String sql, Object[] params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        preparedStatement.execute();
    }

    public void execute(String sql) throws SQLException {
        Connection connection = getConnectDB();
        Statement statement = connection.createStatement();
        statement.execute(sql);
        connection.close();
    }

    public void execute(String sql, Object[] params) throws SQLException {
        Connection connection = getConnectDB();
        executeOnce(connection, sql, params);
        connection.close();
    }

    public void executeBatch(List<String> listSql) throws SQLException {
        Connection connection = getConnectDB();
        Statement statement = connection.createStatement();
        for (String sql : listSql) {
            statement.addBatch(sql);
        }
        statement.executeBatch();
        connection.close();
    }

    /**
     * @param listExecute list of pair (sql, params)
     */
    public void executeBatchWithParam(List<Pair<String, Object[]>> listExecute) throws SQLException {
        Connection connection = getConnectDB();
        connection.setAutoCommit(false);
        for (Pair<String, Object[]> pair : listExecute) {
            executeOnce(connection, pair.getKey(), pair.getValue());
        }
        connection.commit();
        connection.close();
    }
    //endregion


    //region INSERT OPERATIONS
    public ResultSet insert(String sql) throws SQLException {
        Connection connection = getConnectDB();
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        return resultSet;
    }

    public ResultSet insert(String sql, Object[] params) throws SQLException {
        Connection connection = getConnectDB();
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        return resultSet;
    }

    public ResultSet insertBatch(String sql, Collection<Object[]> listParams) throws SQLException {
        Connection connection = getConnectDB();
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (Object[] params : listParams) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        return resultSet;
    }
    //endregion


}
