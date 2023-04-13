package controller.novel;

import core.StringUtils;
import core.logging.BasicLogger;
import model.Genre;
import model.Novel;
import model.Volume;
import repository.ChapterRepository;
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
import java.util.List;

@MultipartConfig
@WebServlet(name = "OverviewNovelServlet", value = "/truyen/*")
public class OverviewNovel extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            String novelPathComponent = pathInfo.split("/")[1];
            int novelId = StringUtils.extractFirstInt(novelPathComponent);
            Novel novel = NovelRepository.getInstance().getById(novelId);

           String novelUri = novelId + "-" + URLSlugification.sluging(novel.getName());
            if(novelId == -1) {
                response.setStatus(404);
                return;
            }
            else if(!novelUri.equals(novelPathComponent)) {
                response.sendRedirect(novelUri);
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
            request.setAttribute("reqChapter", virtualChapter);
            request.setAttribute("commentLimit", COMMENT_LIMIT);
            request.getRequestDispatcher("/WEB-INF/view/novel_detail.jsp").forward(request, response);
        } catch (Exception e) {
            response.setStatus(500);
            BasicLogger.getInstance().getLogger().warning(e.getMessage());
        }
    }


}


