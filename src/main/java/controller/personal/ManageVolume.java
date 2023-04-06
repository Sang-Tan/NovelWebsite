package controller.personal;

import core.logging.BasicLogger;
import core.metadata.ManageNovelAction;
import model.Chapter;
import model.Novel;
import model.User;
import model.Volume;
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

@MultipartConfig(maxFileSize = 1024 * 1024 * 2) //2MB
@WebServlet("/ca-nhan/tap-truyen/*")
public class ManageVolume extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            setRequestAttributes(req, resp);
            if (!validateOwner(req, resp)) {
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            BasicLogger.getInstance().getLogger().warning(e.getMessage());
            return;
        }


        String action = req.getParameter("action");
        if (action == null || action.isEmpty()) {
            showVolumeModificationPage(req, resp);
        } else if (action.equals("add-chapter")) {
            showAddChapterPage(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            setRequestAttributes(req, resp);
            if (!validateOwner(req, resp)) {
                return;
            }

            String action = req.getParameter("action");
            if (action == null || action.isEmpty()) {
                updateVolume(req, resp);
            } else if (action.equals("add-chapter")) {
                addChapter(req, resp);
            } else if (action.equals("delete-volume")) {
                deleteVolume(req, resp);
                return;
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            BasicLogger.getInstance().getLogger().warning(e.getMessage());
            return;
        }
    }


    private int getVolumeId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();

        //substring(1) to remove the first slash
        return Integer.parseInt(pathInfo.substring(1));
    }


    private boolean validateOwner(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        Novel novelInfo = (Novel) req.getAttribute("reqNovel");
        User userInfo = (User) req.getAttribute("user");

        if (novelInfo == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Truyện không tồn tại");
            return false;
        }

        if (!NovelManageService.checkNovelOwnership(userInfo, novelInfo.getId())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;
    }

    private void setRequestAttributes(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int volumeId = getVolumeId(req);
        Volume volume = NovelManageService.getVolumeByID(volumeId);
        if (volume == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Tập truyện không tồn tại");
            throw new Exception("Volume not found");
        }
        Novel novel = NovelManageService.getNovelByID(volume.getNovelId());

        req.setAttribute("reqVolume", volume);
        req.setAttribute("reqNovel", novel);
    }

    private void showVolumeModificationPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("managingAction", ManageNovelAction.EDIT_VOLUME);
        req.getRequestDispatcher("/WEB-INF/view/personal/novel_manage.jsp").forward(req, resp);
    }

    private void updateVolume(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Volume newVolumeInfo = new Volume();
        newVolumeInfo.setName(req.getParameter("volume_name"));
        newVolumeInfo.setId(getVolumeId(req));

        Part uploadImg = req.getPart("image");

        NovelManageService.updateVolumeInfo(newVolumeInfo, uploadImg);

        resp.sendRedirect("/ca-nhan/tap-truyen/" + getVolumeId(req));
    }

    private void deleteVolume(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        NovelManageService.deleteVolume(getVolumeId(req));
        Novel novel = (Novel) req.getAttribute("reqNovel");
        resp.sendRedirect("/ca-nhan/tieu-thuyet/" + novel.getId());
    }

    private void showAddChapterPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("managingAction", ManageNovelAction.ADD_CHAPTER);
        req.getRequestDispatcher("/WEB-INF/view/personal/novel_manage.jsp").forward(req, resp);
    }

    private void addChapter(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String chapterName = req.getParameter("chapter_name");
        String chapterContent = req.getParameter("chapter_content");
        int volumeId = getVolumeId(req);

        Chapter newChapterInfo = new Chapter();
        newChapterInfo.setName(chapterName);
        newChapterInfo.setContent(chapterContent);
        newChapterInfo.setVolumeId(volumeId);

        NovelManageService.uploadNewChapter(newChapterInfo);

        resp.sendRedirect("/ca-nhan/tap-truyen/" + volumeId);
    }
}
