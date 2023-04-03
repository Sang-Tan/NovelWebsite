package core.database;

import core.DatabaseObject;

import javax.persistence.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public abstract class BaseRepository<T extends DatabaseObject> {
    private final Class<?> ENTITY_CLASS;
    private final DBFieldMapping PRIMARY_KEY_FIELDS;
    private final DBFieldMapping COLUMNS;
    private final DBFieldMapping AUTO_GENERATED_KEY_FIELDS;
    private final String TABLE_NAME;
    private final String INSERT_STRING;


    //region Initialization
    protected BaseRepository() {
        Class<?> clazz = createEmpty().getClass();
        this.ENTITY_CLASS = clazz;
        this.TABLE_NAME = getTableName(clazz);
        this.PRIMARY_KEY_FIELDS = getPrimaryKey(clazz);
        this.COLUMNS = getColumns(clazz);
        this.AUTO_GENERATED_KEY_FIELDS = getAutoGeneratedKeyFields(clazz);
        this.INSERT_STRING = generateInsertString(COLUMNS);
    }

    private String getTableName(Class<?> clazz) {
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        return tableAnnotation.name();
    }

    private DBFieldMapping getPrimaryKey(Class<?> clazz) {
        DBFieldMapping primaryKeys = new DBFieldMapping();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                primaryKeys.put(columnAnnotation.name(), field);
                return primaryKeys;
            } else if (field.isAnnotationPresent(EmbeddedId.class)) {
                Field[] idFields = field.getType().getDeclaredFields();
                for (Field idField : idFields) {
                    Column columnAnnotation = idField.getType().getAnnotation(Column.class);
                    if (columnAnnotation != null) {
                        primaryKeys.put(columnAnnotation.name(), idField);
                    }
                }
                return primaryKeys;
            }
        }
        return primaryKeys;
    }

    private DBFieldMapping getColumns(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        DBFieldMapping columns = new DBFieldMapping();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                columns.put(columnAnnotation.name(), field);
            } else if (field.isAnnotationPresent(EmbeddedId.class)) {
                Field[] idFields = field.getType().getDeclaredFields();
                for (Field idField : idFields) {
                    Column columnAnnotation = idField.getType().getAnnotation(Column.class);
                    if (columnAnnotation != null) {
                        columns.put(columnAnnotation.name(), idField);
                    }
                }
            }
        }
        return columns;
    }

    private DBFieldMapping getAutoGeneratedKeyFields(Class<?> clazz) {
        DBFieldMapping autoGeneratedKeyFields = new DBFieldMapping();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(GeneratedValue.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                autoGeneratedKeyFields.put(columnAnnotation.name(), field);
                return autoGeneratedKeyFields;
            }
        }
        if (autoGeneratedKeyFields.size() > 1) {
            throw new RuntimeException("Multiple auto generated key fields are not supported, please check the entity class :" + clazz.getName());
        }
        return autoGeneratedKeyFields;
    }

    private final String generateInsertString(DBFieldMapping fieldToMap) {
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        for (String columnName : fieldToMap.keySet()) {
            columns.add(columnName);
            values.add("?");
        }
        String columnString = String.join(", ", columns);
        String valueString = String.join(", ", values);
        return String.format("INSERT INTO %s (%s) VALUES (%s)", getTableName(), columnString, valueString);
    }

    protected abstract T createEmpty();

    protected String getTableName() {
        return TABLE_NAME;
    }
    //endregion

    /**
     * Get the primary key(in database)  string separated by comma, e.g. "id, name"
     *
     * @return the primary key string
     */
    protected final String getPrimaryKeyString() {
        ArrayList<String> primaryKeysColumnsName = new ArrayList<>();
        for (Field field : PRIMARY_KEY_FIELDS.values()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            primaryKeysColumnsName.add(columnAnnotation.name());
        }
        return String.join(", ", primaryKeysColumnsName);
    }

    public abstract T createDefault();

    protected final SqlRecord mapRecordByField(DBFieldMapping fields, T object) {
        SqlRecord record = new SqlRecord();
        for (String columnName : fields.keySet()) {
            Field field = fields.get(columnName);
            try {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), object.getClass());
                record.put(columnName, propertyDescriptor.getReadMethod().invoke(object));
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return record;
    }

    protected final SqlRecord mapRecord(T object) {
        return mapRecordByField(COLUMNS, object);
    }

    protected final SqlRecord getPrimaryKeyMap(T object) {
        return mapRecordByField(PRIMARY_KEY_FIELDS, object);
    }

    protected final T mapObject(ResultSet resultSet) throws SQLException {
        T object = createEmpty();
        ResultSetMetaData metaData = resultSet.getMetaData();
        try {
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                if (COLUMNS.containsKey(columnName)) {
                    Field field = COLUMNS.get(columnName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), object.getClass());
                    propertyDescriptor.getWriteMethod().invoke(object, resultSet.getObject(i, field.getType()));
                } else {
                    throw new SQLException(String.format("Column %s not found in %s", columnName, object.getClass().getName()));
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    protected final List<T> mapObjects(ResultSet resultSet) throws SQLException {
        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(mapObject(resultSet));
        }
        return list;
    }

    public final List<T> getAll() throws SQLException {
        String sql = String.format("SELECT * FROM %s", getTableName());
        ResultSet resultSet = MySQLdb.getInstance().select(sql);
        return mapObjects(resultSet);
    }

    public List<T> getRange(int start, int end) throws SQLException {
        String sql = String.format("SELECT * FROM %s LIMIT %d, %d", getTableName(), start, end);
        ResultSet resultSet = MySQLdb.getInstance().select(sql);
        return mapObjects(resultSet);
    }

    /**
     * Get the object by auto generated key
     *
     * @param autoGeneratedKeySet the result set of auto generated key
     * @param object              the object to be modified with the auto generated key
     * @return the object with auto generated key, this is the same object as the input object,
     * but it is returned for convenience
     * @throws SQLException
     */
    public T getObjectByAutoGeneratedKey(ResultSet autoGeneratedKeySet, T object) throws SQLException {
        if (autoGeneratedKeySet == null) {
            throw new IllegalArgumentException("autoGeneratedKeySet cannot be null");
        }

        //assume that there is only one auto generated key
        Field autoGeneratedField = AUTO_GENERATED_KEY_FIELDS.values().iterator().next();
        try {
            PropertyDescriptor propertyDescriptor =
                    new PropertyDescriptor(autoGeneratedField.getName(), ENTITY_CLASS);

            Object value = autoGeneratedKeySet.getObject(1, autoGeneratedField.getType());

            propertyDescriptor.getWriteMethod().invoke(object, value);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return object;
    }

    /**
     * Insert a object into the database, note that the input object will be
     * modified to include the auto generated key if there is any
     *
     * @param object the object to insert
     * @return the object used to insert after modification, this is the same object as the input,
     * this implementation is just for convenience
     * @throws SQLException if there is any error when inserting
     */
    public T insert(T object) throws SQLException {
        SqlRecord record = mapRecord(object);
        ResultSet autoGeneratedKeySet = insert(record);
        if (AUTO_GENERATED_KEY_FIELDS.size() > 0) {
            //assume that there is only one auto generated key
            if (autoGeneratedKeySet.next()) {
                getObjectByAutoGeneratedKey(autoGeneratedKeySet, object);
            }
        }
        return object;
    }

    public final Collection<T> insertBatch(Collection<T> objects) throws SQLException {
        for (T object : objects) {
            insert(object);
        }
        return objects;
    }

    /**
     * Insert a record into the database
     *
     * @param record the record to insert
     * @return the auto generated key if there is any, if there is no auto generated key, ResultSet will be empty
     * @throws SQLException if a database access error occurs
     */
    protected final ResultSet insert(SqlRecord record) throws SQLException {
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
        return MySQLdb.getInstance().insert(sql, valuesArray);
    }


    public void update(T object) throws SQLException {
        SqlRecord record = mapRecord(object);
        excludePrimaryKey(record);
        SqlRecord primaryKeyRecord = getPrimaryKeyMap(object);
        update(record, primaryKeyRecord);
    }

    protected void excludePrimaryKey(SqlRecord record) {
        for (String key : PRIMARY_KEY_FIELDS.keySet()) {
            record.remove(key);
        }
    }

    protected final void update(SqlRecord record, SqlRecord primaryKeyRecord) throws SQLException {
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

    public void delete(T object) throws SQLException {
        SqlRecord primaryKeyRecord = getPrimaryKeyMap(object);
        delete(primaryKeyRecord);
    }

    protected final void delete(SqlRecord primaryKeyRecord) throws SQLException {
        String[] pkColumns = new String[primaryKeyRecord.size()];
        String[] pkColumnExpression = new String[primaryKeyRecord.size()];
        primaryKeyRecord.getColumns(pkColumns);
        for (int i = 0; i < pkColumns.length; i++) {
            pkColumnExpression[i] = pkColumns[i] + " = ?";
        }
        String pkSelect = String.join(" AND ", pkColumnExpression);

        String sql = String.format("DELETE FROM %s WHERE %s", getTableName(), pkSelect);

        MySQLdb.getInstance().execute(sql, primaryKeyRecord.getValues(pkColumns));
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
