package controller.novel;

import core.StringUtils;
import core.logging.BasicLogger;
import model.Chapter;
import model.Genre;
import model.Novel;
import model.User;
import repository.ChapterRepository;
import repository.NovelFavouriteRepository;
import repository.NovelRepository;
import service.URLSlugification;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

@MultipartConfig
@WebServlet(name = "OverviewNovelServlet", value = "/truyen/*")
public class OverviewNovel extends HttpServlet {
    private static final Integer COMMENT_LIMIT = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            User user = (User) request.getAttribute("user");
            String pathInfo = request.getPathInfo();
            if (pathInfo.split("/").length < 2) {
                response.setStatus(404);
                request.getRequestDispatcher("/WEB-INF/view/notfound.jsp").forward(request, response);
                return;
            }
            String novelPathComponent = pathInfo.split("/")[1];
            int novelId = StringUtils.extractFirstInt(novelPathComponent);
            Novel novel = NovelRepository.getInstance().getById(novelId);

            if(novel == null){
                response.setStatus(404);
                request.getRequestDispatcher("/WEB-INF/view/notfound.jsp").forward(request, response);
                return;
            }

            String novelUri = novelId + "-" + URLSlugification.sluging(novel.getName());
            if (novelId == -1) {
                response.setStatus(404);
                return;
            } else if (!novelUri.equals(novelPathComponent) || pathInfo.split("/").length != 2) {
                response.sendRedirect("/truyen/"+novelUri);
                return;
            }

            request.setAttribute("novel", novel);

            HashMap<String, String> statusMap = new HashMap<>();
            statusMap.put("on going", "Đang tiến hành");
            statusMap.put("finished", "Đã hoàn thành");
            statusMap.put("paused", "Tạm ngưng");
            request.setAttribute("statusMap", statusMap);

            Collection<Genre> genres = novel.getGenres();

            request.setAttribute("genres", genres);

            Chapter virtualChapter = ChapterRepository.getInstance().getVirtualChapter(novelId);
            boolean isFavourite = false;
            if (user != null) {
                isFavourite = NovelFavouriteRepository.getInstance().isFavourite(user.getId(), novelId);

            }
            request.setAttribute("reqChapter", virtualChapter);
            request.setAttribute("commentLimit", COMMENT_LIMIT);
            request.setAttribute("isFavourite", isFavourite);
            request.getRequestDispatcher("/WEB-INF/view/novel_detail.jsp").forward(request, response);
        } catch (Exception e) {
            response.setStatus(500);
            BasicLogger.getInstance().getLogger().warning(e.getMessage());
        }
    }


}


