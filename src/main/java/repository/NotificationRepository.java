package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import core.pagination.Paginator;
import model.Notification;
import model.User;
import service.PagingService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotificationRepository extends BaseRepository<Notification> {
    private static NotificationRepository instance;

    public static NotificationRepository getInstance() {
        if (instance == null) {
            instance = new NotificationRepository();
        }
        return instance;
    }

    public static List<Notification> getNotificationsByPage(User user) throws SQLException {
        List<Object> params = new ArrayList<>();
        String conditionsSQL = "user_id = ? ORDER BY time_push_notif DESC ";
        params.add(user.getId());
        return getInstance().getByConditionString(conditionsSQL, params);
    }

    @Override
    protected Notification createEmpty() {
        return new Notification();
    }

    public Notification getById(Integer ID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(ID));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }

    public List<Notification> getByConditionString(String condition, List<Object> params) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE %s", getTableName(), condition);
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, params);
        return mapObjects(records);
    }



    public void deleteById(Integer notificationId) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE id = ?", getTableName());
        MySQLdb.getInstance().execute(sql, List.of(notificationId));
    }
}
