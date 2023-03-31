package core.database;

import core.DatabaseObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class BaseRepository<T extends DatabaseObject> {
    private final String TABLE_NAME;
    private final String[] PRIMARY_KEY;

    protected BaseRepository(String tableName, String[] primaryKey) {
        this.TABLE_NAME = tableName;
        this.PRIMARY_KEY = primaryKey;
    }

    protected String getTableName() {
        return this.TABLE_NAME;
    }

    protected String[] getPrimaryKey() {
        return this.PRIMARY_KEY;
    }

    /**
     * Get the primary key string separated by comma, e.g. "id, name"
     *
     * @return
     */
    protected String getPrimaryKeyString() {
        return String.join(", ", this.PRIMARY_KEY);
    }

    protected abstract T createDefault();

    /**
     * Map a row in the result set to a bean
     *
     * @param resultSet the result set
     * @return the bean
     * @throws SQLException if a database access error occurs
     */
    protected abstract T mapRow(ResultSet resultSet) throws SQLException;

    protected abstract SqlRecord mapObject(T object);

    protected abstract SqlRecord getPrimaryKeyMap(T object);

    protected List<T> mapRows(ResultSet resultSet) throws SQLException {
        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(mapRow(resultSet));
        }
        return list;
    }

    public List<T> getAll() throws SQLException {
        String sql = String.format("SELECT * FROM %s", getTableName());
        ResultSet resultSet = MySQLdb.getInstance().select(sql);
        return mapRows(resultSet);
    }

    public List<T> getRange(int start, int end) throws SQLException {
        String sql = String.format("SELECT * FROM %s LIMIT %d, %d", getTableName(), start, end);
        ResultSet resultSet = MySQLdb.getInstance().select(sql);
        return mapRows(resultSet);
    }


    public void insert(T object) throws SQLException {
        SqlRecord record = mapObject(object);
        insert(record);
    }

    /**
     * Insert a record into the database
     *
     * @param record the record to insert
     * @throws SQLException if a database access error occurs
     */
    protected void insert(SqlRecord record) throws SQLException {
        int size = record.size();

        //example columnsString = "col1, col2, col3,..."
        String[] columns = new String[size];
        record.getColumns(columns);
        String columnsString = String.join(", ", columns);

        //example valuesString = "?, ?, ?,..."
        String[] values = new String[size];
        Arrays.fill(values, "?");
        String valuesString = String.join(", ", values);


        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", getTableName(), columnsString, valuesString);

        Object[] valuesArray = record.getValues(columns);
        MySQLdb.getInstance().execute(sql, valuesArray);
    }

    public void update(T object) throws SQLException {
        SqlRecord record = mapObject(object);
        excludePrimaryKey(record);
        SqlRecord primaryKeyRecord = getPrimaryKeyMap(object);
        update(record, primaryKeyRecord);
    }

    protected void excludePrimaryKey(SqlRecord record) {
        for (String key : getPrimaryKey()) {
            record.remove(key);
        }
    }

    protected void update(SqlRecord record, SqlRecord primaryKeyRecord) throws SQLException {
        int size = record.size();

        //=====================UPDATE COLUMNS=====================
        //parameters for the sql statement
        ArrayList<Object> parameters = new ArrayList<>(size);

        String[] upColumns = new String[size];
        record.getColumns(upColumns);

        //IMPORTANT : DON'T MOVE THE FOLLOWING LINE
        Collections.addAll(parameters, record.getValues(upColumns));

        record.getColumns(upColumns);
        for (int i = 0; i < size; i++) {
            upColumns[i] += " = ?";
        }
        String setsString = String.join(", ", upColumns); //example : "col1 = ?, col2 = ?, col3 = ?,..."

        //=====================PRIMARY KEY SELECT=====================
        String[] pkColumns = new String[primaryKeyRecord.size()];
        primaryKeyRecord.getColumns(pkColumns);
        //IMPORTANT : DON'T MOVE THE FOLLOWING LINE
        Collections.addAll(parameters, primaryKeyRecord.getValues(pkColumns));
        for (int i = 0; i < pkColumns.length; i++) {
            pkColumns[i] += " = ?";
        }
        String pkSelect = String.join(" AND ", pkColumns);

        String sql = String.format("UPDATE %s SET %s WHERE %s", getTableName(), setsString, pkSelect);

        MySQLdb.getInstance().execute(sql, parameters.toArray());
    }


    public Integer count() throws SQLException {
        String sql = String.format("SELECT COUNT(%s) FROM %s", getPrimaryKeyString(), getTableName());
        ResultSet resultSet = MySQLdb.getInstance().select(sql);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }


}
