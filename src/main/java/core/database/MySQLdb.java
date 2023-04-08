package core.database;

import core.Pair;
import io.github.cdimascio.dotenv.Dotenv;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
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

    private List<SqlRecord> mapResultSet(ResultSet resultSet) throws SQLException {
        List<SqlRecord> list = new ArrayList<>();
        while (resultSet.next()) {
            SqlRecord record = new SqlRecord();
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = resultSet.getMetaData().getColumnName(i);
                Object value = resultSet.getObject(columnName);
                if (value instanceof BigInteger) {
                    value = ((BigInteger) value).intValue();
                }

                record.put(columnName, value);
            }
            list.add(record);
        }
        return list;
    }


    //region SELECT OPERATIONS
    public List<SqlRecord> select(String sql) throws SQLException {
        Connection connection = getConnectDB();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<SqlRecord> result = this.mapResultSet(resultSet);

        resultSet.close();
        statement.close();
        connection.close();
        return result;
    }

    public List<SqlRecord> select(String sql, List<Object> params) throws SQLException {
        Connection connection = getConnectDB();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.size(); i++) {
            preparedStatement.setObject(i + 1, params.get(i));
        }
        ResultSet resultSet = preparedStatement.executeQuery();

        List<SqlRecord> result = this.mapResultSet(resultSet);

        resultSet.close();
        preparedStatement.close();
        connection.close();
        return result;
    }
    //endregion


    //region UPDATE AND DELETE OPERATIONS
    public void executeOnce(Connection connection, String sql, List<Object> params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.size(); i++) {
            preparedStatement.setObject(i + 1, params.get(i));
        }
        preparedStatement.execute();
    }

    public void execute(String sql) throws SQLException {
        Connection connection = getConnectDB();
        Statement statement = connection.createStatement();
        statement.execute(sql);
        connection.close();
    }

    public void execute(String sql, List<Object> params) throws SQLException {
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
    public void executeBatchWithParam(List<Pair<String, List<Object>>> listExecute) throws SQLException {
        Connection connection = getConnectDB();
        connection.setAutoCommit(false);
        for (Pair<String, List<Object>> pair : listExecute) {
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

    public SqlRecord insert(String sql, List<Object> params) throws SQLException {
        Connection connection = getConnectDB();
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.size(); i++) {
            preparedStatement.setObject(i + 1, params.get(i));
        }
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        List<SqlRecord> list = this.mapResultSet(resultSet);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public List<SqlRecord> insertBatch(String sql, List<List<Object>> listParams) throws SQLException {
        Connection connection = getConnectDB();
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (List<Object> params : listParams) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        return this.mapResultSet(resultSet);
    }
    //endregion


}
