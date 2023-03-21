package core.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SqlRecord {
    private HashMap<String, Object> record;

    public SqlRecord() {
        record = new HashMap<>();
    }

    public void setValue(String column, Object value) {
        record.put(column, value);
    }

    public Object getValue(String column) {
        return record.get(column);
    }

    public Set<String> getColumns() {
        return record.keySet();
    }
}
