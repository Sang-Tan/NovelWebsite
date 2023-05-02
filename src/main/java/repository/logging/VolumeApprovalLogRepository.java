package repository.logging;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.logging.VolumeApprovalLog;

import java.sql.SQLException;
import java.util.List;

public class VolumeApprovalLogRepository extends BaseRepository<VolumeApprovalLog> {

    private static VolumeApprovalLogRepository instance;

    public static VolumeApprovalLogRepository getInstance() {
        if (instance == null) {
            synchronized (VolumeApprovalLogRepository.class) {
                if (instance == null) {
                    instance = new VolumeApprovalLogRepository();
                }
            }
        }
        return instance;
    }

    @Override
    protected VolumeApprovalLog createEmpty() {
        return null;
    }

    public List<VolumeApprovalLog> getOrderedLogsByVolumeId(Integer volumeId, boolean ascendingCreatedTime) throws SQLException {
        String query = "SELECT * FROM volume_approval_logs " +
                "WHERE volume_id = ? " +
                "ORDER BY created_at " + (ascendingCreatedTime ? "ASC" : "DESC");
        List<SqlRecord> records = MySQLdb.getInstance().select(query, List.of(volumeId));
        return mapObjects(records);
    }
}
