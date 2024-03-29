package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.Comment;
import model.CommentReport;

import java.sql.SQLException;
import java.util.List;

public class CommentReportRepository extends BaseRepository<CommentReport> {
    private static CommentReportRepository instance;

    public static CommentReportRepository getInstance() {
        if (instance == null) {
            instance = new CommentReportRepository();
        }
        return instance;
    }

    @Override
    protected CommentReport createEmpty() {
        return new CommentReport();
    }

    public List<CommentReport> getAllReportContentByCommentId(int commentId) throws SQLException {
        String sql = String.format("SELECT * FROM %s " + "WHERE comment_id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(commentId));
        return mapObjects(records);
    }

    public long countCommentReports(String condition, List<Object> params) throws SQLException {
        String sql = String.format("SELECT COUNT(id) FROM %s WHERE %s", getTableName(), condition);
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, params);
        for (SqlRecord record : records) {
            return (long) record.get("COUNT(id)");
        }
        return 0;
    }

    public void setCheckTime(int commentId) throws SQLException {
        String sql = String.format("UPDATE %s SET check_time = CURRENT_TIMESTAMP WHERE comment_id = ?", getTableName());
        MySQLdb.getInstance().execute(sql, List.of(commentId));
    }

    public void deleteReport(int commentId) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE comment_id = ?", getTableName());
        MySQLdb.getInstance().execute(sql, List.of(commentId));
    }

    public boolean isReportExist(int commentId, int reporterId) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE comment_id = ? AND reporter_id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(commentId, reporterId));
        return records.size() > 0;
    }
}
