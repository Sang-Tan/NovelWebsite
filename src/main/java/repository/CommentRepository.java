package repository;

import core.database.BaseRepository;
import core.database.SqlRecord;
import model.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentRepository extends BaseRepository<Comment> {
    @Override
    protected Comment createDefault() {
        return null;
    }


    @Override
    protected Comment mapRow(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setId(resultSet.getInt("id"));
        comment.setUserId(resultSet.getInt("user_id"));
        comment.setCommentTime(resultSet.getTimestamp("time_comment"));
        comment.setDeactiveBy(resultSet.getInt("deactive_by"));
        comment.setContent(resultSet.getString("content"));
        comment.setParentId(resultSet.getInt("parent_id"));
        return comment;
    }

    @Override
    protected SqlRecord mapObject(Comment comment) {
        SqlRecord record = new SqlRecord();
        record.put("id", comment.getId());
        record.put("user_id", comment.getUserId());
        record.put("time_comment", comment.getContent());
        record.put("deactive_by", comment.getDeactiveBy());
        record.put("time_comment", comment.getCommentTime());
        record.put("parent_id", comment.getParentId());
        return record;
    }

    @Override
    protected SqlRecord getPrimaryKeyMap(Comment object) {
        SqlRecord record = new SqlRecord();
        record.put("id", object.getId());
        return record;
    }
    protected CommentRepository() {
        super("comments", new String[]{"id"});
    }
}

