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
import service.upload_change.VolumeChangeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

            User owner = (User) req.getAttribute("user");
            List<String> warnings = new ArrayList<>();
            if (RestrictionService.getUnexpiredRestriction(owner.getId(), Restriction.TYPE_NOVEL) != null) {
                warnings.add("Bạn đang bị cấm đăng truyện nên không thể đăng cũng như chỉnh sửa truyện");
            }
            addMessages(req, "warnings", warnings);


            String action = req.getParameter("action");
            if (action == null || action.isEmpty()) {
                showVolumeModificationPage(req, resp);
            } else if (action.equals("add-chapter")) {
                showAddChapterPage(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
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
            if (!validateOwner(req, resp)) {
                return;
            }

            if (RestrictionService.getUnexpiredRestriction(owner.getId(), Restriction.TYPE_NOVEL) != null) {
                req.setAttribute("errorMessage", "Bạn đã bị cấm đăng truyện nên không thể thực hiện thao tác này");
                req.getRequestDispatcher("/WEB-INF/view/personal/error_page.jsp").forward(req, resp);
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
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
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

    private void showVolumeModificationPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Volume volume = (Volume) req.getAttribute("reqVolume");

        if (VolumeChangeService.getInstance().waitingForModeration(getVolumeId(req))) {
            addMessages(req, "informations", List.of("Tập truyện của bạn đang chờ duyệt"));
            Boolean submitAllowed = false;
            req.setAttribute("submitAllowed", submitAllowed);
        } else if (volume.getApprovalStatus().equals(Volume.APPROVE_STATUS_REJECTED)) {
            addMessages(req, "warnings", List.of("Tập truyện của bạn đã bị từ chối, vui lòng chỉnh sửa lại nội dung"));
        }
        
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

    private void addMessages(HttpServletRequest req, String msgListName, List<String> messages) {
        List<String> msgList = ((List<String>) req.getAttribute(msgListName));
        if (msgList == null) {
            msgList = new ArrayList<>();
            req.setAttribute(msgListName, msgList);
        }
        msgList.addAll(messages);
    }
}
