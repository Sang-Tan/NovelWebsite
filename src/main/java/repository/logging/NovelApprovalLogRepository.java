package repository.logging;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.logging.NovelApprovalLog;

import java.sql.SQLException;
import java.util.List;

public class NovelApprovalLogRepository extends BaseRepository<NovelApprovalLog> {

    private static NovelApprovalLogRepository instance;

    public static NovelApprovalLogRepository getInstance() {
        if (instance == null) {
            synchronized (NovelApprovalLogRepository.class) {
                if (instance == null) {
                    instance = new NovelApprovalLogRepository();
                }
            }
        }
        return instance;
    }

    private NovelApprovalLogRepository() {
        super();
    }

    @Override
    protected NovelApprovalLog createEmpty() {
        return new NovelApprovalLog();
    }

    /**
     * Get all logs of a novel, latest first
     */
    public List<NovelApprovalLog> getOrderedLogsByNovelId(Integer novelId, boolean ascendingCreatedTime) throws SQLException {
        String query = "SELECT * FROM novel_approval_logs " +
                "WHERE novel_id = ? " +
                "ORDER BY created_at " + (ascendingCreatedTime ? "ASC" : "DESC");
        List<SqlRecord> records = MySQLdb.getInstance().select(query, List.of(novelId));
        return mapObjects(records);
    }
}
