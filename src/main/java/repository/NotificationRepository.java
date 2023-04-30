package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import core.pagination.Paginator;
import model.Notification;
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

    public static List<Notification> getNotificationsByPage(int page) throws SQLException {
        List<Object> params = new ArrayList<>();
        String conditionsSQL = "ORDER BY time_push_notif DESC ";
        Paginator paginator = new Paginator(getInstance().countNotifications(), page);
        conditionsSQL += PagingService.generatePaginationCondition(params, paginator);
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
        String sql = String.format("SELECT * FROM %s %s", getTableName(), condition);
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, params);
        return mapObjects(records);
    }
    public long countNotifications() throws SQLException {

        List<Object> params = new ArrayList<>();
        String sql = String.format("SELECT COUNT(id) FROM %s ", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, params);
        for (SqlRecord record : records) {
            return (long) record.get("COUNT(id)");
        }
        return 0;
    }




    public Collection<Notification> getNovelsByOwnerID(int ownerID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE owner = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(ownerID));
        return mapObjects(records);
    }


}
