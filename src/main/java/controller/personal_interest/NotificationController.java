package controller.personal_interest;

import core.logging.BasicLogger;
import core.metadata.PersonalInterest;
import core.pagination.Paginator;
import model.Notification;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;
import repository.NotificationRepository;
import service.BookmarkService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/thong-bao")
public class NotificationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User userInRequest = (User) req.getAttribute("user");
        try {
            int page = Integer.parseInt(req.getParameter("page") == null ? "0" : req.getParameter("page"));
            Paginator paginator = new Paginator();

            // notifications in this page
            List<Notification> notifications = null;
            try {
                notifications = NotificationRepository.getNotificationsByPage(page);
                paginator = new Paginator(NotificationRepository.getInstance().countNotifications(), page);
            } catch (SQLException e) {
                resp.setStatus(500);
                BasicLogger.getInstance().getLogger().warning(e.getMessage());
            }



            req.setAttribute("notifications", notifications);
            req.setAttribute("interest", PersonalInterest.NOTIFICATION);
            req.setAttribute("paginator", paginator);
            req.getRequestDispatcher("/WEB-INF/view/personal_interest/main_page.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
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
