package core.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SqlRecord extends HashMap<String, Object> {

    @Override
    public Object put(String column, Object value) {
        return super.put(column, value);
    }

    @Override
    public Object get(Object column) {
        return super.get(column);
    }

    public List<String> getColumns() {
        return new ArrayList<>(this.keySet());
    }

    public List<Object> getValues(List<String> columns) {
        List<Object> values = new ArrayList<>();
        for (String column : columns) {
            values.add(this.get(column));
        }
        return values;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return super.remove(key, value);
    }
}
