package controller.mod_admin.upload_change;

import core.Pair;
import core.logging.BasicLogger;
import core.media.MediaObject;
import core.media.MediaType;
import model.User;
import model.Volume;
import model.logging.VolumeApprovalLog;
import model.logging.info.VolumeApprovalLogInfo;
import model.temporary.VolumeChange;
import repository.VolumeRepository;
import service.logging.VolumeApprovalLoggingService;
import service.upload_change.VolumeChangeService;
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

@WebServlet("/mod/thay-doi/tap-truyen/*")
public class VolumeChangeDetail extends BaseChangeController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Volume reqVolume = VolumeRepository.getInstance().getById(getResourceId(req));
            req.setAttribute("reqVolume", reqVolume);
            req.setAttribute("novelRelatedContentType", NovelRelatedContentType.VOLUME);

            List<VolumeApprovalLog> volumeApprovalLogs = VolumeApprovalLoggingService.getInstance().getLogsByResourceId(reqVolume.getId());
            List<VolumeApprovalLogInfo> logInfos = new ArrayList<>();
            for (VolumeApprovalLog volumeApprovalLog : volumeApprovalLogs) {
                logInfos.add(new VolumeApprovalLogInfo(volumeApprovalLog));
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
        Volume volume = VolumeRepository.getInstance().getById(resourceId);

        return List.of(
                makeNewContentPair(MediaType.INLINE_TEXT, "Tên tập truyện", volume.getName()),
                makeNewContentPair(MediaType.IMAGE_URL, "Ảnh bìa", volume.getImage())
        );
    }

    @Override
    protected List<Pair<String, List<MediaObject>>> getChangeContents(int resourceId) throws SQLException {
        Volume volume = VolumeRepository.getInstance().getById(resourceId);
        VolumeChange volumeChange = VolumeChangeService.getInstance().getChangeByResourceId(resourceId);

        List<Pair<String, List<MediaObject>>> changeContent = new ArrayList<>();

        if (volumeChange.getName() != null) {
            changeContent.add(makeChangePair(MediaType.INLINE_TEXT, "Tên tập truyện", volume.getName(), volumeChange.getName()));
        }

        if (volumeChange.getImage() != null) {
            changeContent.add(makeChangePair(MediaType.IMAGE_URL, "Ảnh bìa", volume.getImage(), volumeChange.getImage()));
        }

        return changeContent;
    }

    @Override
    protected int getResourceId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        return Integer.parseInt(pathParts[1]);
    }

    @Override
    protected ContentChangeType getChangeType(int resourceId) throws SQLException {
        return VolumeChangeService.getInstance().getChangeType(resourceId);
    }

    @Override
    protected BaseChangeService getChangeService() {
        return VolumeChangeService.getInstance();
    }

    @Override
    protected void addApproveLog(User moderator, int resourceId) throws SQLException {
        //TODO: implement change log(I think it's not necessary)
    }

    @Override
    protected void addRejectLog(User moderator, int resourceId, String reason) throws SQLException {
        VolumeApprovalLog log = new VolumeApprovalLog();
        log.setModeratorId(moderator.getId());
        log.setVolumeId(resourceId);
        log.setContent(reason);

        VolumeApprovalLoggingService.getInstance().saveLog(log);
    }
}
