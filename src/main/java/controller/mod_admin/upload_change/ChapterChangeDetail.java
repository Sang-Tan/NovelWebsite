package controller.mod_admin.upload_change;

import core.Pair;
import core.logging.BasicLogger;
import core.media.MediaObject;
import core.media.MediaType;
import model.Chapter;
import model.Novel;
import model.User;
import model.Volume;
import model.logging.ChapterApprovalLog;
import model.logging.info.ChapterApprovalLogInfo;
import model.temporary.ChapterChange;
import repository.ChapterRepository;
import repository.NovelRepository;
import repository.UserRepository;
import service.NotificationService;
import service.logging.ChapterApprovalLoggingService;
import service.upload_change.ChapterChangeService;
import service.upload_change.base.BaseChangeService;
import service.upload_change.metadata.ContentChangeType;
import service.upload_change.metadata.NovelRelatedContentType;
import service.validator.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/mod/thay-doi/chuong-truyen/*")
public class ChapterChangeDetail extends BaseChangeController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Chapter reqChapter = ChapterRepository.getInstance().getById(getResourceId(req));
            req.setAttribute("reqChapter", reqChapter);
            req.setAttribute("novelRelatedContentType", NovelRelatedContentType.CHAPTER);

            List<ChapterApprovalLog> chapterApprovalLogs = ChapterApprovalLoggingService.getInstance().getLogsByResourceId(reqChapter.getId());
            List<ChapterApprovalLogInfo> logInfos = new ArrayList<>();
            for (ChapterApprovalLog chapterApprovalLog : chapterApprovalLogs) {
                logInfos.add(new ChapterApprovalLogInfo(chapterApprovalLog));
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
        Chapter chapter = ChapterRepository.getInstance().getById(resourceId);

        return List.of(
                makeNewContentPair(MediaType.INLINE_TEXT, "Tên chương", chapter.getName()),
                makeNewContentPair(MediaType.MULTILINE_TEXT, "Nội dung", chapter.getContent())
        );
    }

    @Override
    protected List<Pair<String, List<MediaObject>>> getChangeContents(int resourceId) throws SQLException {
        Chapter chapter = ChapterRepository.getInstance().getById(resourceId);
        ChapterChange chapterChange = ChapterChangeService.getInstance().getChangeByResourceId(resourceId);

        List<Pair<String, List<MediaObject>>> changeContents = new ArrayList<>();

        if (chapterChange.getName() != null) {
            changeContents.add(makeChangePair(MediaType.INLINE_TEXT, "Tên chương", chapter.getName(), chapterChange.getName()));
        }

        if (chapterChange.getContent() != null) {
            changeContents.add(makeChangePair(MediaType.MULTILINE_TEXT, "Nội dung", chapter.getContent(), chapterChange.getContent()));
        }

        return changeContents;
    }

    @Override
    protected int getResourceId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        return Integer.parseInt(pathParts[1]);
    }

    @Override
    protected ContentChangeType getChangeType(int resourceId) throws SQLException {
        return ChapterChangeService.getInstance().getChangeType(resourceId);
    }

    @Override
    protected BaseChangeService<Chapter, ChapterChange> getChangeService() {
        return ChapterChangeService.getInstance();
    }

    @Override
    protected void addApproveLog(User moderator, int chapterId) throws SQLException {
        //TODO: add approve log (I think this is not necessary)
    }

    @Override
    protected void addRejectLog(User moderator, int resourceId, String reason) throws SQLException {
        ChapterApprovalLog chapterApprovalLog = new ChapterApprovalLog();
        chapterApprovalLog.setChapterId(resourceId);
        chapterApprovalLog.setModeratorId(moderator.getId());
        chapterApprovalLog.setContent(reason);

        ChapterApprovalLoggingService.getInstance().saveLog(chapterApprovalLog);
    }

    @Override
    protected void addApproveNotification(int chapterId) throws SQLException {
        Novel novel = NovelRepository.getInstance().getByChapterID(chapterId);
        Chapter chapter = ChapterRepository.getInstance().getById(chapterId);

        String content = String.format("Chúc mừng, chương truyện \"%s\" của bạn vừa được duyệt!", chapter.getName());
        String link = String.format("/ca-nhan/chuong-truyen/%d", chapterId);
        NotificationService.addNotification(novel.getOwnerID(), content, link);
    }

    @Override
    protected void addRejectNotification(int chapterId, String reason) throws SQLException {
        Novel novel = NovelRepository.getInstance().getByChapterID(chapterId);
        Chapter chapter = ChapterRepository.getInstance().getById(chapterId);

        String content = String.format("Chương truyện \"%s\" của bạn đã bị từ chối, lý do : %s", chapter.getName(), reason);
        String link = String.format("/ca-nhan/chuong-truyen/%d", chapterId);
        NotificationService.addNotification(novel.getOwnerID(), content, link);
    }
}
