package controller.personal_interest;

import core.Pair;
import core.logging.BasicLogger;
import core.metadata.PersonalInterest;
import model.Chapter;
import model.Novel;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;
import repository.ChapterRepository;
import repository.NovelRepository;
import service.ReadingListService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/theo-doi")
public class ReadingListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User userInRequest = (User) req.getAttribute("user");
        try {
            List<Pair<Novel, Chapter>> novelChapterPairs = new ArrayList<>();
            for (Novel novel : NovelRepository.getInstance().getFavoriteNovelsByUserID(userInRequest.getId())) {
                Pair<Novel, Chapter> novelChapterPair = new Pair<>(novel,
                        ChapterRepository.getInstance().getLastChapterOfNovel(novel.getId()));
                novelChapterPairs.add(novelChapterPair);
            }
            req.setAttribute("novelChapterPairs", novelChapterPairs);
            req.setAttribute("interest", PersonalInterest.READING_LIST);
            req.getRequestDispatcher("/WEB-INF/view/personal_interest/main_page.jsp").forward(req, resp);
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().getLogger().warning(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Integer novelId = Integer.parseInt(req.getParameter("id"));
        User userInRequest = (User) req.getAttribute("user");
        resp.setContentType("application/json");
        try {
            if (action == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } else if (action.equals("follow-novel")) {
                followNovel(req, resp, novelId, userInRequest);
            } else if (action.equals("unfollow-novel")) {
                unfollowNovel(req, resp, novelId, userInRequest);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().getLogger().warning(e.getMessage());
        }
    }

    private void followNovel(HttpServletRequest req, HttpServletResponse resp, Integer novelId, User userInRequest) throws IOException, JSONException, SQLException {
        String error = ReadingListService.followNovel(novelId, userInRequest.getId());
        if (error != null) {
            resp.getWriter().write(getErrorJsonString(error));
        } else {
            resp.getWriter().write(getSuccessJsonString());
        }
    }

    private void unfollowNovel(HttpServletRequest req, HttpServletResponse resp, Integer novelId, User userInRequest) throws IOException, JSONException, SQLException {
        String error = ReadingListService.unfollowNovel(novelId, userInRequest.getId());
        if (error != null) {
            resp.getWriter().write(getErrorJsonString(error));
        } else {
            resp.getWriter().write(getSuccessJsonString());
        }
    }


    private String getErrorJsonString(String message) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "error");
        jsonObject.put("message", message);
        return jsonObject.toString();
    }

    private String getSuccessJsonString() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "success");
        return jsonObject.toString();
    }

}
