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

    protected UserRepository() {
        super("users", new String[]{"id"});
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    @Override
    protected User createDefault() {
        User user = new User();
        user.setDisplayName("Anonymous");
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setRole(User.ROLE_MEMBER);
        user.setActive(true);
        return user;
    }

    @Override
    protected User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setDisplayName(resultSet.getString("display_name"));
        user.setActive(resultSet.getBoolean("is_active"));

        // if avatar is null, set default avatar
        if (resultSet.getString("avatar") == null) {
            user.setAvatar(User.DEFAULT_AVATAR);
        } else {
            user.setAvatar(resultSet.getString("avatar"));
        }
        user.setRole(resultSet.getString("role"));
        return user;
    }

    @Override
    protected SqlRecord mapObject(User user) {
        SqlRecord record = new SqlRecord();
        record.put("id", user.getId());
        record.put("username", user.getUsername());
        record.put("password", user.getPassword());
        record.put("display_name", user.getDisplayName());
        record.put("is_active", user.isActive());
        record.put("avatar", user.getAvatar());
        record.put("role", user.getRole());

        return record;
    }

    @Override
    protected SqlRecord getPrimaryKeyMap(User object) {
        SqlRecord record = new SqlRecord();
        record.put("id", object.getId());
        return record;
    }

    public User createNewUser(String username, String hashedPassword) {
        User user = createDefault();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        return user;
    }

    public User getById(Integer ID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().query(sql, new Object[]{ID});
        if (result.next()) {
            return mapRow(result);
        }
        return null;
    }

    public User getByUsername(String username) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE username = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().query(sql, new Object[]{username});
        if (result.next()) {
            return mapRow(result);
        }
        return null;
    }

    public User getByToken(String hashedToken) throws SQLException {
        String query = String.format("SELECT user_id FROM %s WHERE token = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().query(query, new Object[]{hashedToken});
        if (result.next()) {
            return getById(result.getInt("user_id"));
        }
        return null;
    }


}
