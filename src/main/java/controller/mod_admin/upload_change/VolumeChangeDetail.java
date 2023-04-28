package controller.mod_admin.upload_change;

import core.Pair;
import core.media.MediaObject;
import core.media.MediaType;
import model.Volume;
import model.temporary.VolumeChange;
import repository.VolumeRepository;
import service.upload_change.base.BaseChangeService;
import service.upload_change.metadata.ContentChangeType;
import service.upload_change.VolumeChangeService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/mod/thay-doi/tap-truyen/*")
public class VolumeChangeDetail extends BaseChangeController {

    @Override
    protected List<Pair<String, MediaObject>> getNewContents(int resourceId) throws SQLException {
        Volume volume = VolumeRepository.getInstance().getById(resourceId);

        List<Pair<String, MediaObject>> newContents = List.of(
                makeNewContentPair(MediaType.INLINE_TEXT, "Tên tập truyện", volume.getName()),
                makeNewContentPair(MediaType.IMAGE_URL, "Ảnh bìa", volume.getImage())
        );
        return newContents;
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
}
