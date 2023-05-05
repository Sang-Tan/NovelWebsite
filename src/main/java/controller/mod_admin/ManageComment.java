package controller.mod_admin;

import core.logging.BasicLogger;
import model.User;
import service.mod.ModerateCommentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/mod/moderate-comment")
public class ManageComment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "";
        try {
            if (action.equals("deactivate")) {
                deactivateComment(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
            return;
        }
    }

    private void deactivateComment(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        try {
            int commentId = Integer.parseInt(req.getParameter("comment-id"));
            User moderator = (User) req.getAttribute("user");
            ModerateCommentService.deactivateComment(commentId, moderator.getId());
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }

}
