package controller.personal_interest;

import core.logging.BasicLogger;
import core.metadata.PersonalInterest;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;
import service.BookmarkService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/danh-dau")
public class BookmarkController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("interest", PersonalInterest.BOOKMARK);
        req.getRequestDispatcher("/WEB-INF/view/personal_interest/main_page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Integer chapterId = Integer.parseInt(req.getParameter("id"));
        User userInRequest = (User) req.getAttribute("user");
        resp.setContentType("application/json");
        try {
            if (action == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } else if (action.equals("add-bookmark")) {
                addBookmark(req, resp, userInRequest.getId(), chapterId);
            } else if (action.equals("remove-bookmark")) {
                removeBookmark(req, resp, userInRequest.getId(), chapterId);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
        }
    }

    private void addBookmark(HttpServletRequest req, HttpServletResponse resp, Integer userId, Integer chapterId) throws IOException, SQLException, SQLException, JSONException {
        String error = BookmarkService.addBookmark(userId, chapterId);
        if (error != null) {
            resp.getWriter().write(getErrorJsonString(error));
        } else {
            resp.getWriter().write(getSuccessJsonString());
        }
    }

    private void removeBookmark(HttpServletRequest req, HttpServletResponse resp, Integer userId, Integer chapterId) throws IOException, SQLException, JSONException {
        String error = BookmarkService.removeBookmark(userId, chapterId);
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
