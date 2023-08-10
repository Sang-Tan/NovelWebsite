package controller.mod_admin;

import core.logging.BasicLogger;
import org.json.JSONException;
import org.json.JSONObject;
import service.admin.ModeratorPromotionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/moderator-promotion")
public class ModerationPromotion extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "promote":
                    promoteModerator(req, resp);
                    break;
                case "demote":
                    demoteModerator(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
        }

    }

    private void promoteModerator(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, JSONException {
        try {
            int userId = Integer.parseInt(req.getParameter("user-id"));
            String error = ModeratorPromotionService.promoteModerator(userId);
            JSONObject result = getResponseErrorMessage(error);
            resp.getWriter().print(result.toString());
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }

    private void demoteModerator(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, JSONException {
        try {
            int userId = Integer.parseInt(req.getParameter("user-id"));
            String error = ModeratorPromotionService.demoteModerator(userId);
            JSONObject result = getResponseErrorMessage(error);
            resp.getWriter().print(result.toString());
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }

    private JSONObject getResponseErrorMessage(String error) throws JSONException {
        JSONObject result = new JSONObject();
        if (error == null) {
            result.put("status", "success");
        } else {
            result.put("status", "error");
            result.put("message", error);
        }
        return result;
    }

}
