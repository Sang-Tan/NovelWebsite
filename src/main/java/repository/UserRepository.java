package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.User;

import java.sql.SQLException;
import java.util.List;


public class UserRepository extends BaseRepository<User> {
    private static UserRepository instance;


    protected User createEmpty() {
        return new User();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }


    public User getById(Integer ID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(ID));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }

    public User getByUsername(String username) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE username = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(username));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }

}
