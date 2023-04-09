package controller.novel;

import model.Genre;
import model.Novel;
import repository.GenreRepository;
import service.Pagination.Paginator;
import service.validator.NovelSearchService;
import core.JSON;
import model.Novel;
import repository.GenreRepository;
import repository.NovelRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig
@WebServlet(name = "SearchNovelsServlet", value = "/tim-kiem-truyen")
public class SearchNovels extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SearchNovels.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        LOGGER.setLevel(Level.ALL);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
    }

    private void setGenresCheckBoxData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Genre> genres = null;
        try {
            genres = GenreRepository.getInstance().getAll();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        request.setAttribute("genres", genres);
    }

    private int[] getGenresIDQuery(String genresIDString) throws ServletException, IOException {

        int[] genresIDList = null;
        if (!(genresIDString == null) && !genresIDString.isEmpty()) {
            String[] arrGenresIDString = genresIDString.split(",");
            genresIDList = new int[arrGenresIDString.length];
            for (int i = 0; i < arrGenresIDString.length; i++) {
                genresIDList[i] = Integer.parseInt(arrGenresIDString[i]);
            }
        }
        return genresIDList;

    }

    private HashMap<String, String> getInputError(String partialNovelName, String genresIDString, String author, String status, String sort) {
        HashMap<String, String> errors = new HashMap<>();
        String genresIDStringRegex = "^[0-9,]+$";

        if (genresIDString == null || genresIDString.isEmpty()) {
            //
        } else if (!genresIDString.matches(genresIDStringRegex)) {
            errors.put("genres", "Genres không hợp lệ");
        }
        if (sort != null && !sort.isEmpty() && !sort.equals("name") && !sort.equals("name") && !sort.equals("comment")) {
            errors.put("sort", "Sort không hợp lệ");
        }
        if (status != null && !status.isEmpty() && !status.equals("on going") && !status.equals("finished") && !status.equals("paused") && !status.equals("all")) {
            errors.put("status", "Status không hợp lệ");
        }
        return errors;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            setGenresCheckBoxData(request, response);
            String partialNovelName = request.getParameter("novel");
            String genresIDString = request.getParameter("genres");
            String author = request.getParameter("author");
            String status = request.getParameter("status");
            String sort = request.getParameter("sort");
            HashMap<String, String> errors = getInputError(partialNovelName, genresIDString, author, status, sort);
            if (!errors.isEmpty()) {
                response.getWriter().println(JSON.getResponseJson("error", errors));
                return;
            }
            int[] genresIDList = getGenresIDQuery(genresIDString);

            List<Novel> novelsSearched = null;
            try {
                novelsSearched = NovelRepository.getInstance().search(partialNovelName, author, status, genresIDList, sort);
            } catch (SQLException e) {
                response.setStatus(500);
                SearchNovels.LOGGER.warning(e.getMessage());
            }

            // set input data to request attribute
            request.setAttribute("partialNovelName", partialNovelName);
            request.setAttribute("genresIDString", genresIDString);
            request.setAttribute("author", author);
            request.setAttribute("status", status);
            request.setAttribute("sort", sort);
            request.setAttribute("genresIDList", genresIDList);

            request.setAttribute("novelsSearched", novelsSearched);
            request.getRequestDispatcher("/WEB-INF/view/search_novel.jsp").forward(request, response);

        } catch (Exception e) {
            response.setStatus(500);
            SearchNovels.LOGGER.warning(e.getMessage());
        }
    }
}
