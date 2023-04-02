package repository;

import core.SHA256Hashing;
import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.User;
import service.validator.UserValidator;


import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRepository extends BaseRepository<User> {
    private static UserRepository instance;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    @Override
    protected User createEmpty() {
        return new User();
    }


    public User createNewUser(String username, String hashedPassword) {
        User user = createEmpty();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        return user;
    }

    public User getById(Integer ID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{ID});
        if (result.next()) {
            return mapObject(result);
        }
        return null;
    }

    public User getByUsername(String username) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE username = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{username});
        if (result.next()) {
            return mapObject(result);
        }
        return null;
    }

    public User getByToken(String hashedToken) throws SQLException {
        String query = String.format("SELECT user_id FROM %s WHERE token = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().select(query, new Object[]{hashedToken});
        if (result.next()) {
            return getById(result.getInt("user_id"));
        }
        return null;
    }
}
