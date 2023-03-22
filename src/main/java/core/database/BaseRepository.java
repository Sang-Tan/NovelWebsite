package core.database;

import core.database.SqlRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

public abstract class BaseRepository {
    private String TABLE_NAME;

    protected abstract void setTableName();

    private BaseRepository() {
        setTableName();
    }


    public void insert(SqlRecord record) throws SQLException {
        Object[] columns = record.getColumns().toArray();
        String columnsString = "";
        String valuesString = "";

        //columnsString col1, col2, col3,...
        //valuesString ?, ?, ?,...
        for (int i = 0; i < columns.length; i++) {
            columnsString += columns[i];
            valuesString += "?";
            if (i < columns.length - 1) {
                columnsString += ", ";
                valuesString += ", ";
            }
        }
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", getTableName(), columnsString, valuesString);

        Connection conn = MySQLdb.getInstance().getConnectDB();

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        for (int i = 0; i < columns.length; i++) {
            preparedStatement.setObject(i + 1, record.getValue(columns[i].toString()));
        }
        preparedStatement.executeUpdate();

    }
}
