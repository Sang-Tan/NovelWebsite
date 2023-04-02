package repository;

import core.SHA256Hashing;
import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import io.github.cdimascio.dotenv.Dotenv;
import model.User;
import service.upload.AvatarUploadService;
import service.validator.UserValidator;


import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRepository extends BaseRepository<User> {
    private static UserRepository instance;

    private static final boolean ACTIVE = true;
    private static final String DEFAULT_AVATAR_PATH = Dotenv.load().get("DEFAULT_AVATAR_PATH");
    public  User createNewUser(String username, String hashedPassword) {
        User user = createEmpty();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setAvatar(DEFAULT_AVATAR_PATH);
        user.setActive(ACTIVE);
        user.setRole(User.ROLE_MEMBER);
        user.setDisplayName(username);
        return user;
    }
    protected  User createEmpty() {
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
