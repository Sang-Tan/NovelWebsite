package database;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository extends BaseRepository {
    private static final String defaultAvatar = "/images/default-avatar.jfif";
    private static UserRepository instance;
    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public static List<User> getByProperties(HashMap<String, Object> userProps) {
        String query = "SELECT * FROM user WHERE ";
        List<User> users = new ArrayList<>();
        List<String> conditions = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        for (Map.Entry<String, Object> entry : userProps.entrySet()) {
            query += entry.getKey() + " = " + entry.getValue() + " AND ";
            conditions.add(entry.getKey());
            values.add(entry.getValue());
        }

        query += String.join(" AND ", conditions);

        try (Connection conn = (Connection) MySQLdb.getInstance().getConnectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int i = 0; i < values.size(); i++) {
                pstmt.setObject(i + 1, values.get(i));
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapUser(rs));
                }
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";
        try (Connection conn = (Connection) MySQLdb.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(query))
        {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


    public static List<User> getByProperties(Map<String, Object> properties) {
        return null;
    }

    public static User getById(Integer ID) {
        HashMap<String,Object> idMap = new HashMap<>();
        idMap.put("id",ID);
        return (User) getByProperties(idMap);
    }

    public static User getByUsername(String username)
    {
        HashMap<String,Object> userMap = new HashMap<>();
        userMap.put("username",username);
        return (User) getByProperties(userMap);
    }

    public static void add(HashMap<String, Object> userProps) {
        try (Connection conn = (Connection) MySQLdb.getInstance();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user (username, password, displayName, active, avatar, role) VALUES (?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, (String) userProps.get("username"));
            pstmt.setString(2, (String) userProps.get("password"));
            pstmt.setString(3, (String) userProps.get("displayName"));
            pstmt.setBoolean(4, (Boolean) userProps.get("active"));
            pstmt.setString(5, (String) userProps.get("avatar"));
            pstmt.setString(6, (String) userProps.get("role"));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void add(User user) throws SQLException
    {
        if(user.getAvatar().equals(defaultAvatar)) {
            user.setAvatar(null);
        }
        //user.setPassword(SHA256Hashing.computeSHA256Hash(user.getPassword()));

        SqlRecord userRecord = new SqlRecord();
        userRecord.setValue("username", user.getUsername());
        userRecord.setValue("password", user.getPassword());
        userRecord.setValue("displayName", user.getDisplayName());
        userRecord.setValue("active", user.isActive());
        userRecord.setValue("avatar", user.getAvatar());
        userRecord.setValue("role", user.getRole());
        insert(userRecord);
    }

    private static User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setDisplayName(rs.getString("display_name"));
        user.setActive(rs.getBoolean("is_active"));
        // if avatar is null, set default avatar
        if (rs.getString("avatar") == null) {
            user.setAvatar(defaultAvatar);
        } else {
            user.setAvatar(rs.getString("avatar"));
        }
        user.setRole(rs.getString("role"));
        return user;
    }
    public static User get(String username, String password)
    {
        HashMap<String,Object> userMap = new HashMap<>();
        userMap.put("username",username);
        userMap.put("password", password);
//        userMap.put("password", SHA256Hashing.hash(password));
        return (User) getByProperties(userMap);

    }









    @Override
    public void update(User NewUser) {
        if(NewUser.getAvatar().equals(defaultAvatar)) {
            NewUser.setAvatar(null);
        }

        //NewUser.setPassword(SHA256Hashing.computeSHA256Hash(NewUser.getPassword()));

        String query = "UPDATE user SET username = ?, password = ?, displayName = ?, active = ?, avatar = ?, role = ? WHERE ID = ?";
        try (Connection conn = (Connection) MySQLdb.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(query))
        {
            // check if user exists
            if (getById(NewUser.getId()) == null) {
                throw new RuntimeException("Không tìm thấy người dùng");
            }
            // set the parameters of the prepared statement
            pstmt.setString(1, NewUser.getUsername());
            pstmt.setString(2, NewUser.getPassword());
            pstmt.setString(3, NewUser.getDisplayName());
            pstmt.setBoolean(4, NewUser.isActive());
            pstmt.setString(5, NewUser.getAvatar());
            pstmt.setString(6, NewUser.getRole());
            pstmt.setInt(7, NewUser.getId());

            // execute the update
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void remove(Integer ID) {
        HashMap<String,Object> idMap = new HashMap<>();
        idMap.put("id",ID);
        remove(idMap);
    }


    public void remove(HashMap<String, Object> userProps) {
        String query = "DELETE FROM user WHERE ";
        List<String> conditions = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        for (Map.Entry<String, Object> entry : userProps.entrySet()) {
            conditions.add(entry.getKey() + " = ?");
            values.add(entry.getValue());
        }

        query += String.join(" AND ", conditions);

        try (Connection conn = (Connection) MySQLdb.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int i = 0; i < values.size(); i++) {
                pstmt.setObject(i + 1, values.get(i));
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected String getTableName() {
        return "users";
    }
}
