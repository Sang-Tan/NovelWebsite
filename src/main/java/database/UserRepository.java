package database;

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

//    public List<User> getByProperties(HashMap<String, Object> userProps) {
//        StringBuilder query = new StringBuilder("SELECT * FROM user WHERE ");
//        List<User> users = new ArrayList<>();
//        List<String> conditions = new ArrayList<>();
//        List<Object> values = new ArrayList<>();
//
//        for (Map.Entry<String, Object> entry : userProps.entrySet()) {
//            query.append(entry.getKey()).append(" = ").append(entry.getValue()).append(" AND ");
//            conditions.add(entry.getKey());
//            values.add(entry.getValue());
//        }
//
//        query.append(String.join(" AND ", conditions));
//
//        try (Connection conn = MySQLdb.getInstance().getConnectDB();
//             PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
//            for (int i = 0; i < values.size(); i++) {
//                pstmt.setObject(i + 1, values.get(i));
//            }
//            try (ResultSet rs = pstmt.executeQuery()) {
//                while (rs.next()) {
//                    users.add(mapUser(rs));
//                }
//            }
//            return users;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }


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



//    public void add(User) {
//        try (Connection conn = (Connection) MySQLdb.getInstance();
//             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user (username, password, displayName, active, avatar, role) VALUES (?, ?, ?, ?, ?, ?)")) {
//            pstmt.setString(1, (String) userProps.get("username"));
//            pstmt.setString(2, (String) userProps.get("password"));
//            pstmt.setString(3, (String) userProps.get("displayName"));
//            pstmt.setBoolean(4, (Boolean) userProps.get("active"));
//            pstmt.setString(5, (String) userProps.get("avatar"));
//            pstmt.setString(6, (String) userProps.get("role"));
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }


//    @Override
//    public void update(User NewUser) {
//        if (NewUser.getAvatar().equals(defaultAvatar)) {
//            NewUser.setAvatar(null);
//        }
//
//        //NewUser.setPassword(SHA256Hashing.computeSHA256Hash(NewUser.getPassword()));
//
//        String query = "UPDATE user SET username = ?, password = ?, displayName = ?, active = ?, avatar = ?, role = ? WHERE ID = ?";
//        try (Connection conn = (Connection) MySQLdb.getInstance();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//            // check if user exists
//            if (getById(NewUser.getId()) == null) {
//                throw new RuntimeException("Không tìm thấy người dùng");
//            }
//            // set the parameters of the prepared statement
//            pstmt.setString(1, NewUser.getUsername());
//            pstmt.setString(2, NewUser.getPassword());
//            pstmt.setString(3, NewUser.getDisplayName());
//            pstmt.setBoolean(4, NewUser.isActive());
//            pstmt.setString(5, NewUser.getAvatar());
//            pstmt.setString(6, NewUser.getRole());
//            pstmt.setInt(7, NewUser.getId());
//
//            // execute the update
//            pstmt.executeUpdate();
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


//    @Override
//    public void remove(Integer ID) {
//        HashMap<String, Object> idMap = new HashMap<>();
//        idMap.put("id", ID);
//        remove(idMap);
//    }


//    public void remove(HashMap<String, Object> userProps) {
//        String query = "DELETE FROM user WHERE ";
//        List<String> conditions = new ArrayList<>();
//        List<Object> values = new ArrayList<>();
//
//        for (Map.Entry<String, Object> entry : userProps.entrySet()) {
//            conditions.add(entry.getKey() + " = ?");
//            values.add(entry.getValue());
//        }
//
//        query += String.join(" AND ", conditions);
//
//        try (Connection conn = (Connection) MySQLdb.getInstance();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//            for (int i = 0; i < values.size(); i++) {
//                pstmt.setObject(i + 1, values.get(i));
//            }
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
}
