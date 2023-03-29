package repository;

import core.database.BaseRepository;
import core.database.SqlRecord;
import model.Restriction;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RestrictionRepository extends BaseRepository<Restriction> {
    private static RestrictionRepository instance;

    public static RestrictionRepository getInstance() {
        if (instance == null) {
            instance = new RestrictionRepository();
        }
        return instance;
    }

    protected RestrictionRepository() {
        super("restrictions", new String[]{"id"});
    }

    @Override
    protected Restriction createDefault() {
        Restriction restriction = new Restriction();
        restriction.setRestrictedType(Restriction.TYPE_NOVEL);
        return restriction;
    }

    @Override
    protected Restriction mapRow(ResultSet resultSet) throws SQLException {
        Restriction restriction = new Restriction();
        restriction.setRestrictedUserId(resultSet.getInt("restricted_user_id"));
        restriction.setRestrictedType(resultSet.getString("restricted_type"));
        restriction.setExecutorId(resultSet.getInt("executor_id"));
        restriction.setReason(resultSet.getString("reason"));
        restriction.setDueTime(resultSet.getTimestamp("due_time"));
        return restriction;
    }

    @Override
    protected SqlRecord mapObject(Restriction restriction) {
        SqlRecord record = new SqlRecord();
        record.put("restricted_user_id", restriction.getRestrictedUserId());
        record.put("restricted_type", restriction.getRestrictedType());
        record.put("executor_id", restriction.getExecutorId());
        record.put("reason", restriction.getReason());
        record.put("due_time", restriction.getDueTime());
        return record;
    }

    @Override
    protected SqlRecord getPrimaryKeyMap(Restriction restriction) {
        SqlRecord record = new SqlRecord();
        record.put("restricted_user_id", restriction.getRestrictedUserId());
        record.put("restricted_type", restriction.getRestrictedType());
        return record;
    }
}
