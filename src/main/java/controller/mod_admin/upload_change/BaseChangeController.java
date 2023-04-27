package controller.mod_admin.upload_change;

import core.Pair;
import core.logging.BasicLogger;
import core.media.MediaObject;
import core.media.MediaType;
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

}
