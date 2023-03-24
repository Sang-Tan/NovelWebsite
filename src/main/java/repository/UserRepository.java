package repository;

import core.SHA256Hashing;
import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.User;


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
        record.setValue("id", user.getId());
        record.setValue("username", user.getUsername());
        record.setValue("password", user.getPassword());
        record.setValue("display_name", user.getDisplayName());
        record.setValue("is_active", user.isActive());
        record.setValue("avatar", user.getAvatar());
        record.setValue("role", user.getRole());

        return record;
    }

    public User createNewUser(String username, String plainPassword) {
        User user = createDefault();
        user.setUsername(username);
        user.setPassword(SHA256Hashing.computeHash(plainPassword));
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
}
