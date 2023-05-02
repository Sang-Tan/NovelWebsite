package repository.logging;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.logging.ChapterApprovalLog;

import java.sql.SQLException;
import java.util.List;

public class ChapterApprovalLogRepository extends BaseRepository<ChapterApprovalLog> {

    private static ChapterApprovalLogRepository instance;

    public static ChapterApprovalLogRepository getInstance() {
        if (instance == null) {
            synchronized (ChapterApprovalLogRepository.class) {
                if (instance == null) {
                    instance = new ChapterApprovalLogRepository();
                }
            }
        }
        return instance;
    }

    @Override
    protected ChapterApprovalLog createEmpty() {
        return new ChapterApprovalLog();
    }

    public List<ChapterApprovalLog> getOrderedLogsByChapterId(Integer chapterId, boolean ascendingCreatedTime) throws SQLException {
        String query = "SELECT * FROM chapter_approval_logs " +
                "WHERE chapter_id = ? " +
                "ORDER BY created_at " + (ascendingCreatedTime ? "ASC" : "DESC");
        List<SqlRecord> records = MySQLdb.getInstance().select(query, List.of(chapterId));
        return mapObjects(records);
    }
}
