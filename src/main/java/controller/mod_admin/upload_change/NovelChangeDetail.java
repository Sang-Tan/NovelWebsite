package controller.mod_admin.upload_change;


import core.Pair;
import core.logging.BasicLogger;
import core.media.MediaObject;
import core.media.MediaType;
import model.Novel;
import model.User;
import model.logging.NovelApprovalLog;
import model.logging.info.NovelApprovalLogInfo;
import model.temporary.NovelChange;
import repository.NovelRepository;
import service.NotificationService;
import service.logging.NovelApprovalLoggingService;
import service.upload_change.NovelChangeService;
import service.upload_change.base.BaseChangeService;
import service.upload_change.metadata.ContentChangeType;
import service.upload_change.metadata.NovelRelatedContentType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/mod/thay-doi/tieu-thuyet/*")
public class NovelChangeDetail extends BaseChangeController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Novel reqNovel = NovelRepository.getInstance().getById(getResourceId(req));
            req.setAttribute("reqNovel", reqNovel);
            req.setAttribute("novelRelatedContentType", NovelRelatedContentType.NOVEL);

            List<NovelApprovalLog> logs = NovelApprovalLoggingService.getInstance().getLogsByResourceId(reqNovel.getId());
            List<NovelApprovalLogInfo> logInfos = new ArrayList<>();
            for (NovelApprovalLog log : logs) {
                logInfos.add(new NovelApprovalLogInfo(log));
            }
            req.setAttribute("approvalLogInfoList", logInfos);
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
            return;
        }

        super.doGet(req, resp);
    }

    @Override
    protected List<Pair<String, MediaObject>> getNewContents(int resourceId) throws SQLException {
        Novel novel = NovelRepository.getInstance().getById(resourceId);

        List<Pair<String, MediaObject>> newContents = new ArrayList<>();
        newContents.add(makeNewContentPair(MediaType.INLINE_TEXT, "Tên tiểu thuyết", novel.getName()));
        newContents.add(makeNewContentPair(MediaType.IMAGE_URL, "Ảnh bìa", novel.getImage()));
        newContents.add(makeNewContentPair(MediaType.MULTILINE_TEXT, "Tóm tắt", novel.getSummary()));
        return newContents;
    }

    @Override
    protected List<Pair<String, List<MediaObject>>> getChangeContents(int resourceId) throws SQLException {
        Novel novel = NovelRepository.getInstance().getById(resourceId);
        NovelChange novelChange = NovelChangeService.getInstance().getChangeByResourceId(resourceId);

        List<Pair<String, List<MediaObject>>> changedContents = new ArrayList<>();

        if (novelChange.getImage() != null) {
            changedContents.add(makeChangePair(MediaType.IMAGE_URL, "Ảnh bìa", novel.getImage(), novelChange.getImage()));
        }

        if (novelChange.getName() != null) {
            changedContents.add(makeChangePair(MediaType.INLINE_TEXT, "Tên tiểu thuyết", novel.getName(), novelChange.getName()));
        }

        if (novelChange.getSummary() != null) {
            changedContents.add(makeChangePair(MediaType.MULTILINE_TEXT, "Tóm tắt", novel.getSummary(), novelChange.getSummary()));
        }

        return changedContents;
    }

    @Override
    protected int getResourceId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        return Integer.parseInt(pathParts[1]);
    }

    @Override
    protected ContentChangeType getChangeType(int resourceId) throws SQLException {
        return NovelChangeService.getInstance().getChangeType(resourceId);
    }

    @Override
    protected BaseChangeService<Novel, NovelChange> getChangeService() {
        return NovelChangeService.getInstance();
    }

    @Override
    protected void addApproveLog(User moderator, int resourceId) throws SQLException {
        //TODO: add approve log (I don't think we need this)
    }

    @Override
    protected void addRejectLog(User moderator, int resourceId, String reason) throws SQLException {
        NovelApprovalLog log = new NovelApprovalLog();
        log.setNovelId(resourceId);
        log.setModeratorId(moderator.getId());
        log.setContent(reason);

        NovelApprovalLoggingService.getInstance().saveLog(log);
    }

    @Override
    protected void addApproveNotification(int novelId) throws SQLException {
        Novel novel = NovelRepository.getInstance().getById(novelId);

        String content = String.format("Chúc mừng, tiểu thuyết \"%s\" của bạn đã được duyệt!", novel.getName());
        String link = String.format("/ca-nhan/tieu-thuyet/%d", novelId);
        NotificationService.addNotification(novel.getOwnerID(), content, link);

    }

    @Override
    protected void addRejectNotification(int novelId, String reason) throws SQLException {
        Novel novel = NovelRepository.getInstance().getById(novelId);

        String content = String.format("Tiểu thuyết \"%s\" của bạn đã bị từ chối, lý do : %s", novel.getName(), reason);
        String link = "/ca-nhan/tieu-thuyet/" + novelId;
        NotificationService.addNotification(novel.getOwnerID(), content, link);
    }

}
