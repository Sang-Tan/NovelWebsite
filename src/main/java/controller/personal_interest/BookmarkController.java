package controller.personal_interest;

import core.Pair;
import core.logging.BasicLogger;
import core.metadata.PersonalInterest;
import model.Chapter;
import model.Novel;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/danh-dau")
public class BookmarkController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User userInRequest = (User) req.getAttribute("user");
        try {
            List<Chapter> markedChapters = BookmarkService.getMarkedChapters(userInRequest.getId());
            Map<Novel, List<Chapter>> novelChapterMap = new HashMap<>();
            for (Chapter chapter : markedChapters) {
                Novel novel = chapter.getBelongVolume().getBelongNovel();
                if (novelChapterMap.containsKey(novel)) {
                    novelChapterMap.get(novel).add(chapter);
                } else {
                    List<Chapter> chapters = new ArrayList<>();
                    chapters.add(chapter);
                    novelChapterMap.put(novel, chapters);
                }
            }

            List<Pair<Novel, List<Chapter>>> novelChapterList = new ArrayList<>();
            for (Map.Entry<Novel, List<Chapter>> entry : novelChapterMap.entrySet()) {
                novelChapterList.add(new Pair<>(entry.getKey(), entry.getValue()));
            }

            req.setAttribute("novelChapterList", novelChapterList);
            req.setAttribute("interest", PersonalInterest.BOOKMARK);
            req.getRequestDispatcher("/WEB-INF/view/personal_interest/main_page.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
        }
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
