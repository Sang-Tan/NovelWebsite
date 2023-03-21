package database;
import model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IRepository<T> {
    List<User> getByProperties(HashMap<String, Object> userProps);

    List<T> getAll();
    List<T> getByProperties(Map<String, Object> properties);

    T getById(Integer ID);

    void add(T entity);
    void update(T entity);

    void remove(Integer ID);
}

