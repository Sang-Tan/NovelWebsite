package controller.mod_admin;

import core.logging.BasicLogger;
import model.Novel;
import org.json.JSONException;
import org.json.JSONObject;
import service.NotificationService;
import service.mod.DeleteNovelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/mod/xoa-tieu-thuyet")
public class NovelDeleteController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int novelId = Integer.parseInt(req.getParameter("novel-id"));
            String reason = req.getParameter("reason");
            String error = DeleteNovelService.deleteNovel(novelId, reason);
            if (error != null) {
                resp.getWriter().print(getResponseJSON("error", error));
            } else {
                resp.getWriter().print(getResponseJSON("success", null));
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
        }
    }

    private JSONObject getResponseJSON(String status, String message) throws JSONException {
        JSONObject responseJSON = new JSONObject();

        responseJSON.put("status", status);
        if (message != null) {
            responseJSON.put("message", message);
        }

        return responseJSON;
    }
}
