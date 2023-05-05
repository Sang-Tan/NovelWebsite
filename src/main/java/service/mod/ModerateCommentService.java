package service.mod;

import model.Comment;
import repository.CommentRepository;
import service.CommentService;

import java.sql.SQLException;

public class ModerateCommentService {
    public static void deactivateComment(int commentId, int moderatorId) throws SQLException {
        Comment commentToDeactivate = CommentRepository.getInstance().getById(commentId);
        commentToDeactivate.setDeactiveBy(moderatorId);
        CommentRepository.getInstance().update(commentToDeactivate);
    }
}
