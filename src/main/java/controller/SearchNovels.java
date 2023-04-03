package controller;

import controller.authentication.Register;
import core.JSON;
import model.Novel;
import model.Token;
import model.User;
import repository.GenreRepository;
import repository.NovelRepository;
import repository.TokenRepository;
import service.validator.TokenService;
import service.validator.UserValidator;
import org.json.JSONException;
import repository.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SearchNovelsServlet", value = "/search-novels")
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


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List genres = null;
        try {
            genres = GenreRepository.getInstance().getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("genres", genres);

        String partialNovelName = request.getParameter("novel");
        // exp : selectgenres=52,6,59
        String genresIDString = request.getParameter("genres");

        int[] genresIDList = null;

        if (!(genresIDString == null ) && !genresIDString.isEmpty()) {
            String[] arrGenresIDString = genresIDString.split(",");
            genresIDList = new int[arrGenresIDString.length];
            for (int i = 0; i < arrGenresIDString.length; i++) {
                genresIDList[i] = Integer.parseInt(arrGenresIDString[i]);
            }
        }
//
        String author = request.getParameter("author");
        String status = request.getParameter("status");
        String sort = request.getParameter("sort");

        List<Novel> novelsSearched = null;
        try {
            novelsSearched =  NovelRepository.getInstance().search(partialNovelName, author, status, genresIDList, sort);
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


    }

}
