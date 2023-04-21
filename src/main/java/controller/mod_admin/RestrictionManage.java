package controller.mod_admin;

import core.logging.BasicLogger;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;
import service.RestrictionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/mod/restrictions")
public class RestrictionManage extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "add_restriction":
                    addRestriction(req, resp);
                    break;
                case "remove_restriction":
                    removeRestriction(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
        } catch (JSONException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
        }
    }

    private void addRestriction(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, JSONException {
        int userId = Integer.parseInt(req.getParameter("user-id"));
        String restrictionType = req.getParameter("restriction-type");
        String reason = req.getParameter("reason");
        long restrictTime = Long.parseLong(req.getParameter("restrict-time"));

        int executorId = ((User) req.getAttribute("user")).getId();

        String error = RestrictionService.addRestriction(userId, restrictionType, reason, restrictTime, executorId);

        if (error == null) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(getErrorJson(error).toString());
        }
    }

    private void removeRestriction(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, JSONException {
        int userId = Integer.parseInt(req.getParameter("user-id"));
        String restrictionType = req.getParameter("restriction-type");

        String error = RestrictionService.removeRestriction(userId, restrictionType);

        if (error == null) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(getErrorJson(error).toString());
        }
    }

    private JSONObject getErrorJson(String error) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("error", error);
        return json;
    }
}
