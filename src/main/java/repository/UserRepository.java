package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import io.github.cdimascio.dotenv.Dotenv;
import model.User;

import java.sql.SQLException;
import java.util.List;


public class UserRepository extends BaseRepository<User> {
    private static UserRepository instance;

    private static final boolean ACTIVE = true;
    private static final String DEFAULT_AVATAR_PATH = Dotenv.load().get("DEFAULT_AVATAR_PATH");

    public User createNewUser(String username, String hashedPassword) {
        User user = createEmpty();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setAvatar(DEFAULT_AVATAR_PATH);
        user.setActive(ACTIVE);
        user.setRole(User.ROLE_MEMBER);
        user.setDisplayName(username);
        return user;
    }

    protected User createEmpty() {
        return new User();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }


    @Override
    public User createDefault() {
        User user = new User();
        user.setDisplayName("Anonymous");
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setRole(User.ROLE_MEMBER);
        user.setActive(true);
        return user;
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
