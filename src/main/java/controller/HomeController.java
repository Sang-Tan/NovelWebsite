package controller;

import model.Chapter;
import model.Novel;
import repository.ChapterRepository;
import service.ViewService;
import service.validator.NovelSearchService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "HomeController", value = "")
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Novel> latestUpdateNovels = null;
        List<Novel> topViewRecentWeekNovels = null;
        List<Novel> topViewRecentMonthNovels = null;
        List<Novel> topViewRecentDayNovels = null;
        Map<Integer, Chapter> latestUpdateChapters = new HashMap<>();

        try {
            latestUpdateNovels = NovelSearchService.getInstance().getLatestUpdateNovels(12);
            topViewRecentWeekNovels = ViewService.getInstance().getTopViewRecentWeekNovels(4);
            topViewRecentMonthNovels = ViewService.getInstance().getTopViewRecentMonthNovels(4);
            topViewRecentDayNovels = ViewService.getInstance().getTopViewRecentDayNovels(4);

            for (Novel novel : latestUpdateNovels) {
                int novelId = novel.getId();
                Chapter latestChapter = ChapterRepository.getInstance().getLastChapterOfNovel(novelId);
                latestUpdateChapters.put(novelId, latestChapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("latestUpdateNovels", latestUpdateNovels);
        request.setAttribute("topViewRecentWeekNovels", topViewRecentWeekNovels);
        request.setAttribute("topViewRecentMonthNovels", topViewRecentMonthNovels);
        request.setAttribute("topViewRecentDayNovels", topViewRecentDayNovels);
        request.setAttribute("latestUpdateChapters", latestUpdateChapters);

        showHomePage(request, response);
    }

    private void showHomePage(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/landing.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

}
