package controller.novel;

import core.StringUtils;
import core.logging.BasicLogger;
import model.Genre;
import model.Novel;
import repository.GenreRepository;
import core.pagination.Paginator;
import service.PagingService;
import service.validator.NovelSearchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@MultipartConfig
@WebServlet(name = "SearchNovelsServlet", value = "/tim-kiem-truyen")
public class SearchNovels extends HttpServlet {

    private void setGenresCheckBoxData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Genre> genres = null;
        try {
            genres = GenreRepository.getInstance().getAll();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        request.setAttribute("genres", genres);
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
            int page = Integer.parseInt(request.getParameter("page") == null ? "0" : request.getParameter("page"));
            Paginator paginator = new Paginator();
//            NovelService.getNovelHashSetBySearchCondition(partialNovelName, genresIDString, author, status, sort);
//            UserValidator.hashPassword("123456");
            List<Novel> novelsSearched = null;
            try {
                novelsSearched = NovelSearchService.getInstance().searchApprovedNovels(partialNovelName, author, status, genresIDString, sort, page);
                paginator = NovelSearchService.getInstance().getPaginator();
            } catch (SQLException e) {
                response.setStatus(500);
                BasicLogger.getInstance().getLogger().warning(e.getMessage());
            }
            // set input data to request attribute
            request.setAttribute("partialNovelName", partialNovelName);
            request.setAttribute("genresIDString", genresIDString);
            request.setAttribute("author", author);
            request.setAttribute("status", status);
            request.setAttribute("sort", sort);
            request.setAttribute("genresIDList", StringUtils.extractInt(genresIDString));
            request.setAttribute("novelsSearched", novelsSearched);
            request.setAttribute("paginator", paginator);

            String novelsUri = "/truyen/";
            request.setAttribute("novelsUri", novelsUri);

            // set paging url for pagination, remove page parameter
            String pagingUrl = "/tim-kiem-truyen?" + request.getQueryString();
            if (pagingUrl.contains("page=")) {
                pagingUrl = pagingUrl.substring(0, pagingUrl.indexOf("&page="));
            }
            request.setAttribute("pageItems", PagingService.getActivePageItems( paginator));
            request.getRequestDispatcher("/WEB-INF/view/search_novel.jsp").forward(request, response);
        } catch (Exception e) {
            response.setStatus(500);
            BasicLogger.getInstance().getLogger().warning(e.getMessage());
        }

    }

}