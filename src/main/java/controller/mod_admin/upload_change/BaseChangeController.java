package controller.mod_admin.upload_change;

import core.Pair;
import core.logging.BasicLogger;
import core.media.MediaObject;
import core.media.MediaType;
import model.User;
import service.logging.ApprovalLoggingService;
import service.upload_change.base.BaseChangeService;
import service.upload_change.metadata.ContentChangeType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseChangeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int resourceId = getResourceId(req);
        try {
            ContentChangeType changeType = getChangeType(resourceId);
            if (changeType.equals(ContentChangeType.NEW)) {
                setAttributesOnNewContent(req);
            } else if (changeType.equals(ContentChangeType.UPDATE)) {
                setAttributesOnUpdateContent(req);
            }
            req.setAttribute("changeType", changeType);
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
            return;
        }
        req.getRequestDispatcher("/WEB-INF/view/mod_admin/content_change_detail.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action") == null ? "" : req.getParameter("action");
        try {
            switch (action) {

                case "approve":
                    approveChange(req, resp);
                    break;

                case "reject":
                    rejectChange(req, resp);
                    break;

                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            BasicLogger.getInstance().printStackTrace(e);
        }
        if (resp.getStatus() == HttpServletResponse.SC_OK) {
            resp.sendRedirect("/mod/duyet-truyen");
        }

    }

    private void approveChange(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        BaseChangeService changeService = getChangeService();
        int resourceId = getResourceId(req);
        changeService.approveChange(resourceId);

        User moderator = (User) req.getAttribute("user");
        addApproveLog(moderator, resourceId);
        addApproveNotification(resourceId);
    }

    private void rejectChange(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String reason = req.getParameter("reason");
        if (reason == null || reason.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        BaseChangeService changeService = getChangeService();
        int resourceId = getResourceId(req);
        changeService.rejectChange(resourceId);

        User moderator = (User) req.getAttribute("user");
        addRejectLog(moderator, resourceId, reason);
        addRejectNotification(resourceId, reason);
    }

    protected Pair<String, MediaObject> makeNewContentPair(MediaType mediaType, String name, Object content) {
        return new Pair<>(name, new MediaObject(mediaType, content));
    }

    protected Pair<String, List<MediaObject>> makeChangePair(MediaType mediaType, String name, Object oldContent, Object newContent) {
        List<MediaObject> mediaObjects = new ArrayList<>();
        mediaObjects.add(new MediaObject(mediaType, oldContent));
        mediaObjects.add(new MediaObject(mediaType, newContent));
        return new Pair<>(name, mediaObjects);
    }

    protected void setAttributesOnNewContent(HttpServletRequest req) throws SQLException {
        List<Pair<String, MediaObject>> newContents = getNewContents(getResourceId(req));
        req.setAttribute("newContents", newContents);
    }

    protected void setAttributesOnUpdateContent(HttpServletRequest req) throws SQLException {
        List<Pair<String, List<MediaObject>>> changeContents = getChangeContents(getResourceId(req));
        req.setAttribute("changedContents", changeContents);
    }

    protected abstract List<Pair<String, MediaObject>> getNewContents(int resourceId) throws SQLException;

    protected abstract List<Pair<String, List<MediaObject>>> getChangeContents(int resourceId) throws SQLException;

    protected abstract int getResourceId(HttpServletRequest req);

    protected abstract ContentChangeType getChangeType(int resourceId) throws SQLException;

    protected abstract BaseChangeService getChangeService();

    protected abstract void addApproveLog(User moderator, int resourceId) throws SQLException;

    protected abstract void addRejectLog(User moderator, int resourceId, String reason) throws SQLException;

    protected abstract void addApproveNotification(int resourceId) throws SQLException;

    protected abstract void addRejectNotification(int resourceId, String reason) throws SQLException;
}
