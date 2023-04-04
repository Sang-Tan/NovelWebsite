package controller.personal;

import core.logging.BasicLogger;
import core.metadata.ManageNovelAction;
import model.Genre;
import model.Novel;
import model.User;
import model.Volume;
import repository.GenreRepository;
import repository.NovelRepository;
import service.upload.NovelManageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@MultipartConfig(maxFileSize = 1024 * 1024 * 2) //2MB
@WebServlet("/ca-nhan/tieu-thuyet/*")
public class ManageNovel extends HttpServlet {
    private int getNovelId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();

        //substring(1) to remove the first slash
        return Integer.parseInt(pathInfo.substring(1));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("tap-moi") != null) {
            try {
                showAddVolumePage(req, resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return;
        } else {
            showNovelModificationPage(req, resp);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("tap-moi") != null) {
            addVolume(req, resp);
        } else {
            updateNovel(req, resp);

        }
    }

    private void showNovelModificationPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int novelId = getNovelId(req);
        try {
            Novel novel = NovelRepository.getInstance().getById(novelId);
            List<Genre> genres = GenreRepository.getInstance().getAll();
            Set<Integer> novelGenreIds = new HashSet<>();
            for (Genre novelGenre : novel.getGenres()) {
                novelGenreIds.add(novelGenre.getId());
            }
            if (novel == null) {
                resp.sendError(404);
                return;
            }
            req.setAttribute("novel", novel);
            req.setAttribute("genres", genres);
            req.setAttribute("novelGenreIds", novelGenreIds);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("managingAction", ManageNovelAction.EDIT_NOVEL);
        req.getRequestDispatcher("/WEB-INF/view/personal/novel_manage.jsp").forward(req, resp);
    }

    private void updateNovel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User performer = (User) req.getAttribute("user");
        int novelId = getNovelId(req);
        try {
            if (NovelManageService.checkOwnership(performer, novelId) == false) {
                resp.sendError(403);
                return;
            }
        } catch (SQLException e) {
            resp.sendError(500);
            BasicLogger.getInstance().getLogger().warning(e.getMessage());
            return;
        }

        Novel novelUpdateInfo = new Novel();
        novelUpdateInfo.setId(novelId);
        novelUpdateInfo.setName(req.getParameter("novel_name"));
        novelUpdateInfo.setSummary(req.getParameter("summary"));
        novelUpdateInfo.setStatus(req.getParameter("status"));
        Part uploadedImage = req.getPart("image");
        String genresString = req.getParameter("genres");

        //split by comma into an array of integers
        String[] genresStringArray = genresString.split(",");
        int[] genres = new int[genresStringArray.length];
        for (int i = 0; i < genresStringArray.length; i++) {
            genres[i] = Integer.parseInt(genresStringArray[i]);
        }

        String error = NovelManageService.validateUploadNovel(novelUpdateInfo, genres, uploadedImage);
        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("managingAction", ManageNovelAction.EDIT_NOVEL);
            doGet(req, resp);
        }

        try {
            NovelManageService.updateNovelInfo(novelUpdateInfo, genres, uploadedImage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/ca-nhan/tieu-thuyet/" + novelId);
    }

    private void showAddVolumePage(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int novelId = getNovelId(req);
        Novel novel = NovelRepository.getInstance().getById(novelId);
        if (novel == null) {
            resp.setStatus(404);
            return;
        }
        req.setAttribute("novel", novel);
        req.setAttribute("managingAction", ManageNovelAction.ADD_VOLUME);
        req.getRequestDispatcher("/WEB-INF/view/personal/novel_manage.jsp").forward(req, resp);
    }

    private void addVolume(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int novelId = getNovelId(req);
        Volume newVolume = new Volume();
        newVolume.setNovelId(novelId);
        newVolume.setName(req.getParameter("volume_name"));
        Part uploadedImage = req.getPart("image");

        String error = NovelManageService.validateUploadVolume(newVolume, uploadedImage);
        if (error != null) {
            req.setAttribute("error", error);
            doGet(req, resp);
        }

        try {
            NovelManageService.uploadNewVolume(newVolume, uploadedImage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/ca-nhan/tieu-thuyet/" + novelId);

    }
}
