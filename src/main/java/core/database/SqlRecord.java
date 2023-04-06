package core.database;

import java.util.HashMap;

public class SqlRecord extends HashMap<String, Object> {

    @Override
    public Object put(String column, Object value) {
        return super.put(column, value);
    }

    @Override
    public Object get(Object column) {
        return super.get(column);
    }

    public void getColumns(String[] columns) {
        this.keySet().toArray(columns);
    }

    public Object[] getValues(String[] columns) {
        Object[] values = new Object[columns.length];
        for (int i = 0; i < columns.length; i++) {
            values[i] = this.get(columns[i]);
        }
        return values;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return super.remove(key, value);
    }
}
