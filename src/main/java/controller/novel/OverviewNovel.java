package controller.novel;

import core.StringUtils;
import core.logging.BasicLogger;
import controller.URIHandler;
import core.StringCoverter;
import model.Chapter;
import model.Genre;
import model.Novel;
import model.Volume;
import repository.ChapterRepository;
import repository.NovelRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig
@WebServlet(name = "OverviewNovelServlet", value = "/truyen/*")
public class OverviewNovel extends HttpServlet {
    private static final Integer COMMENT_LIMIT = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // exp : pathInfo = /truyen/1-ten-truyen

            String pathInfo = request.getPathInfo();
            String part = pathInfo.split("/")[1];
            int novelId = URIHandler.getIdFromPathComponent(part);

           String novelUri = NovelRepository.getInstance().generatePathComponent(novelId);
            if(novelId == -1) {
                response.setStatus(404);
                return;
            }
            else if(!novelUri.equals(part)) {
                response.sendRedirect(novelUri);
                return;
            }
            Novel novel = NovelRepository.getInstance().getById(novelId);
            List<Volume> volumes = novel.getVolumes();

            request.setAttribute("novel", novel);

            HashMap<String, String> statusMap = new HashMap<>();
            statusMap.put("on going", "Đang tiến hành");
            statusMap.put("finished", "Đã hoàn thành");
            statusMap.put("paused", "Tạm ngưng");
            request.setAttribute("statusMap", statusMap);

            Collection<Genre> genres = novel.getGenres();

            request.setAttribute("genres", genres);
            String searchNovelUri = "/tim-kiem-truyen";
            request.setAttribute("searchNovelUri", searchNovelUri);

            Chapter virtualChapter = ChapterRepository.getInstance().getVirtualChapter(novelId);
            request.setAttribute("reqChapter", virtualChapter);
            request.setAttribute("commentLimit", COMMENT_LIMIT);
            request.getRequestDispatcher("/WEB-INF/view/novel_detail.jsp").forward(request, response);
        } catch (Exception e) {
            response.setStatus(500);
            OverviewNovel.LOGGER.warning(e.getMessage());
        }
    }


}


