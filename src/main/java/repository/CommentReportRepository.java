package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.Chapter;
import model.CommentReport;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<CommentReport> getAllCommentReport() throws SQLException {
        String sql = String.format("SELECT * FROM %s " +
                "GROUP BY comment_id ORDER BY report_time DESC", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql);
        return mapObjects(records);
    }

    public List<CommentReport> getAllReportContentByCommentId(int commentId) throws SQLException{
        String sql = String.format("SELECT * FROM %s " + "WHERE comment_id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(commentId));
        return mapObjects(records);
    }


}
