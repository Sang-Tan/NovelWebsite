package controller.personal;

import core.logging.BasicLogger;
import core.metadata.ManageNovelAction;
import model.Chapter;
import model.Novel;
import model.User;
import model.Volume;
import model.intermediate.Restriction;
import service.RestrictionService;
import service.upload.NovelManageService;
import service.upload_change.ChapterChangeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/ca-nhan/chuong-truyen/*")
public class ManageChapter extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            setRequestAttributes(req, resp);
            User owner = (User) req.getAttribute("user");
            List<String> warnings = new ArrayList<>();
            if (!checkValidOwner(req, resp)) {
                return;
            }

            if (RestrictionService.getUnexpiredRestriction(owner.getId(), Restriction.TYPE_NOVEL) != null) {
                warnings.add("Bạn đang bị cấm đăng truyện nên không thể đăng cũng như chỉnh sửa truyện");
            }

            req.setAttribute("warnings", warnings);


            showChapterModificationPage(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            setRequestAttributes(req, resp);

            User owner = (User) req.getAttribute("user");

            if (!checkValidOwner(req, resp)) {
                return;
            }

            if (RestrictionService.getUnexpiredRestriction(owner.getId(), Restriction.TYPE_NOVEL) != null) {
                req.setAttribute("errorMessage", "Bạn đã bị cấm đăng truyện nên không thể thực hiện thao tác này");
                req.getRequestDispatcher("/WEB-INF/view/personal/error_page.jsp").forward(req, resp);
                return;
            }


            String action = req.getParameter("action");
            if (action == null || action.isEmpty()) {
                updateChapter(req, resp);
            } else if (action.equals("delete-chapter")) {
                deleteChapter(req, resp);
                return;
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
            return;
        } catch (Exception e) {
            BasicLogger.getInstance().printStackTrace(e);
            return;
        }

        resp.sendRedirect("/ca-nhan/chuong-truyen/" + getChapterId(req));
    }


    private int getChapterId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();

        //substring(1) to remove the first slash
        return Integer.parseInt(pathInfo.substring(1));
    }

    private void showChapterModificationPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        List<String> informations = (List<String>) req.getAttribute("informations");
        if (informations == null) {
            informations = new ArrayList<>();
        }

        if (ChapterChangeService.getInstance().waitingForModeration(getChapterId(req))) {
            informations.add("Tập truyện của bạn đang chờ duyệt, bạn không thể chỉnh sửa");
            Boolean submitAllowed = false;
            req.setAttribute("submitAllowed", submitAllowed);
        }

        req.setAttribute("informations", informations);
        req.setAttribute("managingAction", ManageNovelAction.EDIT_CHAPTER);
        req.getRequestDispatcher("/WEB-INF/view/personal/novel_manage.jsp").forward(req, resp);
    }

    private void updateChapter(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        Chapter newChapterInfo = new Chapter();
        newChapterInfo.setId(getChapterId(req));
        newChapterInfo.setName(req.getParameter("chapter_name"));
        newChapterInfo.setContent(req.getParameter("chapter_content"));

        String errorMessage = NovelManageService.validateUploadChapter(newChapterInfo);
        if (errorMessage != null) {
            req.setAttribute("error", errorMessage);
            return;
        }

        NovelManageService.updateChapterInfo(newChapterInfo);
    }

    private void deleteChapter(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        NovelManageService.deleteChapter(getChapterId(req));
        Novel reqNovel = (Novel) req.getAttribute("reqNovel");
        resp.sendRedirect("/ca-nhan/tieu-thuyet/" + reqNovel.getId());
    }

    private void setRequestAttributes(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int chapterId = getChapterId(req);
        Chapter chapter = NovelManageService.getChapterByID(chapterId);
        if (chapter == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            throw new Exception("Chapter not found");
        }
        Volume volume = NovelManageService.getVolumeByID(chapter.getVolumeId());
        Novel novel = NovelManageService.getNovelByID(volume.getNovelId());
        req.setAttribute("reqChapter", chapter);
        req.setAttribute("reqVolume", volume);
        req.setAttribute("reqNovel", novel);
    }

    /**
     * @return true if the user is the owner of the novel
     */
    private boolean checkValidOwner(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int chapterId = getChapterId(req);
        Novel reqNovel = (Novel) req.getAttribute("reqNovel");
        User reqUser = (User) req.getAttribute("user");

        if (reqNovel == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }

        if (!NovelManageService.checkNovelOwnership(reqUser, reqNovel.getId())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }
}
