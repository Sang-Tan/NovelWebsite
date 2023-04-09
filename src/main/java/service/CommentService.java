package service;

import model.Comment;
import org.json.JSONException;
import repository.CommentRepository;

import java.sql.SQLException;
import java.util.List;

public class CommentService {
    /**
     * @param chapterId
     * @param limit
     * @param offset
     * @return Root comments (comments reply no comment) in a chapter (newest first)
     */
    public static List<Comment> getCommentsInChapter(Integer chapterId, Integer limit, Integer offset) throws SQLException, JSONException {
        List<Comment> rootComments = CommentRepository.getInstance().
                getRootCommentsInChapter(chapterId, limit, offset);
        return rootComments;
    }
}
