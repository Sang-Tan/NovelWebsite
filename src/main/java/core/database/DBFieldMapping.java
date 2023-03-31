package core.database;

import java.lang.reflect.Field;
import java.util.HashMap;

public class DBFieldMapping extends HashMap<String, Field> {
    @Override
    public Field put(String dbColumn, Field objectField) {
        return super.put(dbColumn, objectField);
    }

    @Override
    public Field get(Object dbColumn) {
        return super.get(dbColumn);
    }
}
