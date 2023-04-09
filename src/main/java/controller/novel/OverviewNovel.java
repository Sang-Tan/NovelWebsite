package controller.novel;

import model.Comment;
import model.Genre;
import model.Novel;
import model.Volume;
import repository.NovelRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig
@WebServlet(name = "OverviewNovelServlet", value = "/truyen/*")
public class OverviewNovel extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(OverviewNovel.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        LOGGER.setLevel(Level.ALL);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
    }

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {


            // exp : pathInfo = /truyen/1-ten-truyen

            String pathInfo = request.getPathInfo();
            String part = pathInfo.split("/")[1];
            int novelId = Integer.parseInt(part.substring(0, part.indexOf("-")));
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

            request.getRequestDispatcher("/WEB-INF/view/novel_detail.jsp").forward(request, response);
        } catch (Exception e) {
            response.setStatus(500);
            OverviewNovel.LOGGER.warning(e.getMessage());
        }
    }


}


