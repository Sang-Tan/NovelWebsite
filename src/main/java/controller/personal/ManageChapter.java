package controller.personal;

import core.logging.BasicLogger;
import core.metadata.ManageNovelAction;
import model.Chapter;
import model.Novel;
import model.User;
import model.Volume;
import service.upload.NovelManageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/ca-nhan/chuong-truyen/*")
public class ManageChapter extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            setRequestAttributes(req, resp);
            if (!checkValidOwner(req, resp)) {
                return;
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().getLogger().warning(e.getMessage());
            return;
        }

        showChapterModificationPage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            setRequestAttributes(req, resp);
            if (!checkValidOwner(req, resp)) {
                return;
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().getLogger().warning(e.getMessage());
            return;
        }

        try {
            updateChapter(req, resp);
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().getLogger().warning(e.getMessage());
            return;
        }

        resp.sendRedirect("/ca-nhan/chuong-truyen/" + getChapterId(req));
    }

    private int getChapterId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();

        //substring(1) to remove the first slash
        return Integer.parseInt(pathInfo.substring(1));
    }

    private void showChapterModificationPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    private void setRequestAttributes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int chapterId = getChapterId(req);
        Chapter chapter = NovelManageService.getChapterByID(chapterId);
        Volume volume = NovelManageService.getVolumeByID(chapter.getVolumeId());
        Novel novel = NovelManageService.getNovelByID(volume.getNovelId());
        req.setAttribute("reqChapter", chapter);
        req.setAttribute("reqVolume", volume);
        req.setAttribute("reqNovel", novel);
    }

    /**
     * @param req
     * @param resp
     * @return true if the user is the owner of the novel
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
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
