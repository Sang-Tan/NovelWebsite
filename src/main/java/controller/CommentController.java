package controller;

import core.logging.BasicLogger;
import model.Comment;
import org.json.JSONException;
import service.CommentService;

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
            if (getType.equals("in_chapter")) {
                getCommentsInChapter(req, resp);
            } else if (getType.equals("by_id")) {
                getCommentsById(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException | JSONException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
        }

    }

    private void getCommentsInChapter(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, JSONException, ServletException {
        Integer chapterId;
        Integer limit;
        Integer offset;
        try {
            chapterId = Integer.parseInt(req.getParameter("chapter_id"));
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
}
