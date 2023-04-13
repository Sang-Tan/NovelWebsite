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
        return CommentRepository.getInstance().
                getRootCommentsInChapter(chapterId, limit, offset);
    }

    public static void postRootComment(Comment commentInfo) throws SQLException {
        if (commentInfo.getChapterId() == 0) {
            throw new IllegalArgumentException("chapterId is not set");
        }
        if (commentInfo.getContent() == null || commentInfo.getContent().isEmpty()) {
            throw new IllegalArgumentException("content is not set");
        }
        if (commentInfo.getUserId() == 0) {
            throw new IllegalArgumentException("userId is not set");
        }

        CommentRepository.getInstance().insert(commentInfo);
    }

    public static int getCommentCountInChapter(int chapterId) throws SQLException {
        return CommentRepository.getInstance().getCommentCountInChapter(chapterId);
    }

    public static int getRootCommentCountInChapter(int chapterId) throws SQLException {
        return CommentRepository.getInstance().getRootCommentCountInChapter(chapterId);
    }

    public static void postReplyComment(Comment commentInfo) throws SQLException {
        if (commentInfo.getContent() == null || commentInfo.getContent().isEmpty()) {
            throw new IllegalArgumentException("content is not set");
        }
        if (commentInfo.getUserId() == 0) {
            throw new IllegalArgumentException("userId is not set");
        }
        if (commentInfo.getParentId() == 0) {
            throw new IllegalArgumentException("parentId is not set");
        }

        int rootCommentId = getRootCommentId(commentInfo.getParentId());
        Comment rootComment = CommentRepository.getInstance().getById(rootCommentId);

        commentInfo.setParentId(rootComment.getId());
        commentInfo.setChapterId(rootComment.getChapterId());

        CommentRepository.getInstance().insert(commentInfo);
    }

    /**
     * Get root comment id of a comment (recursive)
     *
     * @param commentId id of a comment in database
     * @return root comment id
     * @throws SQLException
     */
    private static int getRootCommentId(int commentId) throws SQLException {
        Comment comment = CommentRepository.getInstance().getById(commentId);
        if (comment == null) {
            throw new IllegalArgumentException("commentId doesn't exist");
        }
        if (comment.getParentId() == null) {
            return commentId;
        } else {
            return getRootCommentId(comment.getParentId());
        }
    }
}
