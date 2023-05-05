package controller;

import core.logging.BasicLogger;
import model.Comment;
import model.User;
import model.intermediate.Restriction;
import org.json.JSONException;
import service.CommentService;
import service.RestrictionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/comments")
public class CommentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String getType = req.getParameter("type");
        try {
            switch (getType) {
                case "in_chapter" -> getCommentsInChapter(req, resp);
                case "by_id" -> getCommentsById(req, resp);
                case "count_in_chapter" -> getCommentCountInChapter(req, resp);
                case "comment_offset" -> getCommentOffsetOfRoot(req, resp);
                default -> resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException | JSONException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User reqUser = (User) req.getAttribute("user");
        if (reqUser == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        String postType = req.getParameter("type");
        if (postType == null || postType.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            // Not allow to post comment if user is restricted
            if (RestrictionService.getUnexpiredRestriction(reqUser.getId(), Restriction.TYPE_COMMENT) != null) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            if (postType.equals("comment_chapter")) {
                postRootComment(req, resp);
            } else if (postType.equals("reply_comment")) {
                postReplyComment(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
        }
    }

    private void getCommentsInChapter(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, JSONException, ServletException {
        Integer chapterId;
        Integer limit;
        Integer offset;
        try {
            chapterId = Integer.parseInt(req.getParameter("chapter-id"));
            limit = Integer.parseInt(req.getParameter("limit"));
            offset = Integer.parseInt(req.getParameter("offset"));
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            BasicLogger.getInstance().printStackTrace(e);
            return;
        }
        List<Comment> comments = CommentService.getCommentsInChapter(chapterId, limit, offset);
        req.setAttribute("reqRootComments", comments);
        req.getRequestDispatcher("/WEB-INF/view/layout/comment_section_content.jsp").forward(req, resp);
    }

    private void getCommentsById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String commentId;
        try {
            commentId = req.getParameter("comment_id");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            BasicLogger.getInstance().printStackTrace(e);
        }
    }

    private void getCommentCountInChapter(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int chapterId;
        try {
            chapterId = Integer.parseInt(req.getParameter("chapter-id"));
            int count = CommentService.getRootCommentCountInChapter(chapterId);
            resp.getWriter().write(String.valueOf(count));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            BasicLogger.getInstance().printStackTrace(e);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
        }
    }

    private void postRootComment(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String content = req.getParameter("content");
        if (content == null || content.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Integer chapterId;
        try {
            chapterId = Integer.parseInt(req.getParameter("chapter-id"));
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            BasicLogger.getInstance().printStackTrace(e);
            return;
        }
        User reqUser = (User) req.getAttribute("user");

        Comment commentInfo = new Comment();
        commentInfo.setContent(content);
        commentInfo.setChapterId(chapterId);
        commentInfo.setUserId(reqUser.getId());
        CommentService.postRootComment(commentInfo);

        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    private void postReplyComment(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String content = req.getParameter("content");
        if (content == null || content.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Integer parentId;
        try {
            parentId = Integer.parseInt(req.getParameter("comment-id"));
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            BasicLogger.getInstance().printStackTrace(e);
            return;
        }
        User reqUser = (User) req.getAttribute("user");

        Comment commentInfo = new Comment();
        commentInfo.setContent(content);
        commentInfo.setParentId(parentId);
        commentInfo.setUserId(reqUser.getId());

        CommentService.postReplyComment(commentInfo);

        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    private void getCommentOffsetOfRoot(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        int commentId;
        try {
            commentId = Integer.parseInt(req.getParameter("comment-id"));
            int offset = CommentService.getCommentOffset(commentId);
            resp.getWriter().write(String.valueOf(offset));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }
}
