package service.admin;

import core.database.MySQLdb;
import core.database.SqlRecord;
import model.User;
import repository.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserSelector {
    private final List<Object> whereParams;
    private final List<String> conditionClauses;

    public UserSelector() {
        whereParams = new ArrayList<>();
        conditionClauses = new ArrayList<>();
    }

    private String generateWhereClause() {
        if (conditionClauses.size() == 0) {
            return "";
        }
        StringBuilder whereClause = new StringBuilder(" WHERE ");
        //join condition clauses with AND (use existing code)
        whereClause.append(String.join(" AND ", conditionClauses));
        whereClause.append(" ");

        return whereClause.toString();
    }

    private List<User> selectUsers(String whereClause, int limit, int offset) throws SQLException {
        String sql = "SELECT * FROM users " + whereClause + "LIMIT ? OFFSET ?";
        List<Object> params = new ArrayList<>(whereParams);
        params.add(limit);
        params.add(offset);
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, params);
        return UserRepository.getInstance().mapObjects(records);
    }

    private int countUsers(String whereClause) throws SQLException {
        String sql = "SELECT COUNT(*) as user_count FROM users " + whereClause;
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, whereParams);
        return Integer.parseInt(records.get(0).get("user_count").toString());
    }

    public UserSelector usernameContain(String username) {
        conditionClauses.add("username LIKE ?");
        whereParams.add("%" + username + "%");
        return this;
    }

    public UserSelector roleMatch(String role) {
        conditionClauses.add("role = ?");
        whereParams.add(role);
        return this;
    }

    public void reset() {
        whereParams.clear();
        conditionClauses.clear();
    }

    public UserSelectResult select(int limit, int offset) throws SQLException, IllegalArgumentException {
        if (limit < 0) {
            throw new IllegalArgumentException("Limit must be greater than 0");
        } else if (offset < 0) {
            throw new IllegalArgumentException("Offset must be greater than 0");
        }

        String whereClause = generateWhereClause();
        List<User> selectedUsers = selectUsers(whereClause, limit, offset);
        int totalUsers = countUsers(whereClause);
        return new UserSelectResult(selectedUsers, totalUsers);
    }

}
