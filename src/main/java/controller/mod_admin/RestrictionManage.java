package controller.mod_admin;

import core.logging.BasicLogger;
import core.string_process.TimeConverter;
import model.User;
import model.intermediate.Restriction;
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
import java.sql.Timestamp;

@WebServlet("/mod/restrictions")
public class RestrictionManage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        if (type == null) {
            type = "";
        }
        try {
            switch (type) {
                case "get_one":
                    getOneRestriction(req, resp);
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

    private void getOneRestriction(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, JSONException {
        int userId = Integer.parseInt(req.getParameter("user-id"));
        String restrictionType = req.getParameter("restriction-type");
        Restriction restriction = RestrictionService.getUnexpiredRestriction(userId, restrictionType);
        if (restriction == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        resp.setContentType("application/json");
        JSONObject restrictionJson = restriction.toJSON();
        restrictionJson.put("dueTime", TimeConverter.convertToVietnameseTime((Timestamp) restrictionJson.get("dueTime")));
        resp.getWriter().write(restrictionJson.toString());
    }

    private JSONObject getErrorJson(String error) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("error", error);
        return json;
    }
}
