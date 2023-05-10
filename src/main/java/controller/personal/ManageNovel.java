package controller.personal;

import core.logging.BasicLogger;
import core.metadata.ManageNovelAction;
import model.Genre;
import model.Novel;
import model.User;
import model.Volume;
import model.intermediate.Restriction;
import repository.GenreRepository;
import service.RestrictionService;
import service.upload.NovelManageService;
import service.upload_change.NovelChangeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

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
        try {
            setRequestAttributes(req, resp);

            User owner = (User) req.getAttribute("user");
            int novelId = getNovelId(req);
            if (!NovelManageService.checkNovelOwnership(owner, novelId)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            if (RestrictionService.getUnexpiredRestriction(owner.getId(), Restriction.TYPE_NOVEL) != null) {
                addMessages(req, "warnings", List.of("Bạn đang bị cấm đăng truyện nên không thể đăng cũng như chỉnh sửa truyện"));
            }

            String action = req.getParameter("action");

            if (action == null || action.isEmpty()) {
                showNovelModificationPage(req, resp);
            } else if (action.equals("add-volume")) {
                showAddVolumePage(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().getLogger().warning(e.getMessage());
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            setRequestAttributes(req, resp);
            User owner = (User) req.getAttribute("user");
            int novelId = getNovelId(req);
            if (!NovelManageService.checkNovelOwnership(owner, novelId)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            if (RestrictionService.getUnexpiredRestriction(owner.getId(), Restriction.TYPE_NOVEL) != null) {
                req.setAttribute("errorMessage", "Bạn đã bị cấm đăng truyện nên không thể thực hiện thao tác này");
                req.getRequestDispatcher("/WEB-INF/view/personal/error_page.jsp").forward(req, resp);
                return;
            }

            String action = req.getParameter("action");
            if (action == null || action.isEmpty()) {
                updateNovel(req, resp);
            } else if (action.equals("add-volume")) {
                addVolume(req, resp);
            } else if (action.equals("delete-novel")) {
                deleteNovel(req, resp);
                return;
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
            return;
        }
    }

    private void showNovelModificationPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int novelId = getNovelId(req);
        try {
            Novel novel = (Novel) req.getAttribute("reqNovel");
            Collection<Genre> genres = GenreRepository.getInstance().getAll();
            Set<Integer> novelGenreIds = new HashSet<>();
            for (Genre novelGenre : novel.getGenres()) {
                novelGenreIds.add(novelGenre.getId());
            }
            if (novel == null) {
                resp.sendError(404);
                return;
            }

            if (NovelChangeService.getInstance().waitingForModeration(novelId)) {
                addMessages(req, "informations", List.of("Truyện của bạn đang chờ được duyệt"));
                Boolean submitAllowed = false;
                req.setAttribute("submitAllowed", submitAllowed);
            } else if (novel.getApprovalStatus().equals(Novel.APPROVE_STATUS_REJECTED)) {
                addMessages(req, "warnings", List.of("Truyện của bạn đã bị từ chối, vui lòng chỉnh sửa lại nội dung"));
            }

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
            if (NovelManageService.checkNovelOwnership(performer, novelId) == false) {
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

        List<String> errors = new ArrayList<>();
        String error = NovelManageService.validateUploadNovel(novelUpdateInfo, genres, uploadedImage);
        if (error != null)
            errors.add(error);

        if (!errors.isEmpty()) {
            addMessages(req, "errors", errors);
            req.setAttribute("managingAction", ManageNovelAction.EDIT_NOVEL);
            doGet(req, resp);
            return;
        }

        try {
            NovelManageService.updateNovelInfo(novelUpdateInfo, genres, uploadedImage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/ca-nhan/tieu-thuyet/" + novelId);
    }

    private void deleteNovel(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        NovelManageService.deleteNovel(getNovelId(req));
        Novel novel = (Novel) req.getAttribute("reqNovel");
        resp.sendRedirect("/ca-nhan/truyen-da-dang");
    }

    private void showAddVolumePage(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int novelId = getNovelId(req);
        Novel novel = (Novel) req.getAttribute("reqNovel");
        if (novel == null) {
            resp.setStatus(404);
            return;
        }

        req.setAttribute("managingAction", ManageNovelAction.ADD_VOLUME);
        req.getRequestDispatcher("/WEB-INF/view/personal/novel_manage.jsp").forward(req, resp);
    }

    private void addVolume(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int novelId = getNovelId(req);
        Volume newVolume = new Volume();
        newVolume.setNovelId(novelId);
        newVolume.setName(req.getParameter("volume_name"));
        Part uploadedImage = req.getPart("image");

        List<String> errors = new ArrayList<>();
        String error = NovelManageService.validateUploadVolume(newVolume, uploadedImage);
        if (error != null) {
            errors.add(error);
        }
        if (!errors.isEmpty()) {
            addMessages(req, "errors", errors);
            doGet(req, resp);
            return;
        }

        try {
            NovelManageService.uploadNewVolume(newVolume, uploadedImage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/ca-nhan/tieu-thuyet/" + novelId);

    }

    private void setRequestAttributes(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        Novel novel = NovelManageService.getNovelByID(getNovelId(req));
        req.setAttribute("reqNovel", novel);
    }

    private void addMessages(HttpServletRequest req, String msgListName, List<String> messages) {
        List<String> msgList = ((List<String>) req.getAttribute(msgListName));
        if (msgList == null) {
            msgList = new ArrayList<>();
            req.setAttribute(msgListName, msgList);
        }
        msgList.addAll(messages);
    }
}
