package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.Comment;

import java.sql.SQLException;
import java.util.List;

public class CommentRepository extends BaseRepository<Comment> {
    private static CommentRepository instance;

    public static CommentRepository getInstance() {
        if (instance == null) {
            instance = new CommentRepository();
        }
        return instance;
    }

    public Comment getById(Integer ID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(ID));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }

    @Override
    protected Comment createEmpty() {
        return new Comment();
    }

    /**
     * Get root comments in a chapter (comments that are not replies to other comments)
     *
     * @param chapterId
     * @param limit
     * @param offset
     * @return List of comments ordered by time_comment DESC
     * @throws SQLException
     */
    public List<Comment> getRootCommentsInChapter(Integer chapterId, Integer limit, Integer offset) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE chapter_id = ? " +
                "AND parent_id IS NULL " +
                "ORDER BY time_comment DESC " +
                "LIMIT ? " +
                "OFFSET ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(chapterId, limit, offset));
        return mapObjects(records);
    }

    /**
     * Get child comments of a comment (comments that are replies to other comments)
     *
     * @param parentId
     * @return List of comments ordered by time_comment ASC
     * @throws SQLException
     */
    public List<Comment> getRepliesComments(Integer parentId) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE parent_id = ? " +
                "ORDER BY time_comment ASC", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(parentId));
        return mapObjects(records);
    }

    public int getCommentCountInChapter(int chapterId) throws SQLException {
        String sql = String.format("SELECT COUNT(*) as count FROM %s WHERE chapter_id = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(chapterId));
        for (SqlRecord record : records) {
            return Integer.parseInt(record.get("count").toString());
        }
        return 0;
    }

    public int getRootCommentCountInChapter(int chapterId) throws SQLException {
        String sql = String.format("SELECT COUNT(*) as count FROM %s WHERE chapter_id = ? " +
                "AND parent_id IS NULL", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(chapterId));
        for (SqlRecord record : records) {
            return Integer.parseInt(record.get("count").toString());
        }
        return 0;
    }

    public List<Comment> getAllCommentReport(String condition, List<Object> params) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id IN (SELECT comment_id FROM comment_report GROUP BY comment_id ORDER BY min(report_time)) LIMIT ? OFFSET ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, params);
        return mapObjects(records);
    }

}
