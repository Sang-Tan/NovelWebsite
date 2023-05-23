package controller.mod_admin.admin;

import model.Genre;
import service.genre.GenreService;
import service.genre.IGenreService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GenreServlet", value = "/admin/genre-manage")
public class GenreController extends HttpServlet {
    private final IGenreService genreService = new GenreService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showList(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                createGenre(req, resp);
                break;

            case "delete":
                deleteGenre(req, resp);
                break;

            case "update":
                updateGenre(req, resp);
                break;

            default:
                showList(req, resp);
                break;
        }
    }

    private void createGenre(HttpServletRequest req, HttpServletResponse resp) {
        String genreName = req.getParameter("genreName");
        Genre genre = new Genre(genreName);
        try {
            genreService.insert(genre);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showList(req, resp);
    }

    private void updateGenre(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("idUpdate"));
        String genreName = req.getParameter("genreName");
        Genre genre = new Genre(id, genreName);
        try {
            genreService.update(genre);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showList(req, resp);
    }

    private void deleteGenre(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Genre genre = genreService.findById(id);
            genreService.delete(genre);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        showList(req, resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) {
        List<Genre> genreList = null;
        try {
            genreList = genreService.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("genreList", genreList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/mod_admin/genre_manage.jsp");
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
