package controller.personal_interest;

import core.logging.BasicLogger;
import core.metadata.PersonalInterest;
import core.pagination.Paginator;
import model.Notification;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;
import repository.NotificationRepository;
import service.NotificationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import static service.NotificationService.extractNotificationId;

@WebServlet("/thong-bao")
public class NotificationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User userInRequest = (User) req.getAttribute("user");
        try {
            Paginator paginator = new Paginator();

            // notifications in this page
            List<Notification> notifications = null;
            try {
                notifications = NotificationRepository.getNotificationsByPage(userInRequest);
            } catch (SQLException e) {
                resp.setStatus(500);
                BasicLogger.getInstance().getLogger().warning(e.getMessage());
            }



            req.setAttribute("notifications", notifications);
            req.setAttribute("interest", PersonalInterest.NOTIFICATION);
            req.getRequestDispatcher("/WEB-INF/view/personal_interest/main_page.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        User userInRequest = (User) req.getAttribute("user");
        resp.setContentType("application/json");
        try {
            if (action == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } else if (action.equals("add-notification")) {
                String content = req.getParameter("content");
                String link = req.getParameter("link");
                addNotification(req, resp, userInRequest.getId(), content, link);
            } else if (action.equals("delete-notification")) {
                String notificationsIdStr = req.getParameter("notificationsId");
                HashSet<Integer> notificationsId = extractNotificationId(notificationsIdStr);
                removeNotification(req, resp, userInRequest.getId(), notificationsId);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
        }
    }
    private void addNotification(HttpServletRequest req, HttpServletResponse resp, Integer userId, String content, String link) throws IOException, SQLException, SQLException, JSONException {
        String error = NotificationService.addNotification(userId, content, link);
        if (error != null) {
            resp.getWriter().write(getErrorJsonString(error));
        } else {
            resp.getWriter().write(getSuccessJsonString());
        }
    }

    private void removeNotification(HttpServletRequest req, HttpServletResponse resp, Integer userId, HashSet<Integer> notificationsId) throws IOException, SQLException, JSONException {
        String error = NotificationService.removeNotification(userId, notificationsId);
        if (error != null) {
            resp.getWriter().write(getErrorJsonString(error));
        } else {
            resp.getWriter().write(getSuccessJsonString());
        }
    }

    private String getErrorJsonString(String error) throws JSONException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", error);
        return jsonObject.toString();
    }

    private String getSuccessJsonString() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "success");
        return jsonObject.toString();
    }
}
