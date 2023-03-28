package repository;

import core.database.BaseRepository;
import core.database.SqlRecord;
import model.CommentReport;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentReportRepository extends BaseRepository<CommentReport> {

    protected CommentReportRepository() {
        super("comment_report", new String[]{"id"});
    }

    @Override
    protected CommentReport createDefault() {
        return null;
    }

    @Override
    protected CommentReport mapRow(ResultSet resultSet) throws SQLException {
        CommentReport commentReport = new CommentReport();
        commentReport.setId(resultSet.getInt("id"));
        commentReport.setCommentId(resultSet.getInt("comment_id"));
        commentReport.setReporterId(resultSet.getInt("reporter_id"));
        commentReport.setReason(resultSet.getString("reason"));
        commentReport.setCheckTime(resultSet.getTimestamp("check_time"));
        return commentReport;
    }

    @Override
    protected SqlRecord mapObject(CommentReport commentReport) {
        SqlRecord record = new SqlRecord();
        record.put("id", commentReport.getId());
        record.put("comment_id", commentReport.getCommentId());
        record.put("reporter_id", commentReport.getReporterId());
        record.put("reason", commentReport.getReason());
        record.put("check_time", commentReport.getCheckTime());
        return record;
    }

    @Override
    protected SqlRecord getPrimaryKeyMap(CommentReport commentReport) {
        SqlRecord record = new SqlRecord();
        record.put("id", commentReport.getId());
        return record;
    }
}
