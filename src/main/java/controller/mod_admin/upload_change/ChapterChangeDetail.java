package controller.mod_admin.upload_change;

import core.Pair;
import core.media.MediaObject;
import core.media.MediaType;
import model.Chapter;
import model.temporary.ChapterChange;
import repository.ChapterRepository;
import service.upload_change.ChapterChangeService;
import service.upload_change.base.BaseChangeService;
import service.upload_change.metadata.ContentChangeType;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/mod/thay-doi/chuong-truyen/*")
public class ChapterChangeDetail extends BaseChangeController {

    @Override
    protected List<Pair<String, MediaObject>> getNewContents(int resourceId) throws SQLException {
        Chapter chapter = ChapterRepository.getInstance().getById(resourceId);

        List<Pair<String, MediaObject>> newContents = List.of(
                makeNewContentPair(MediaType.INLINE_TEXT, "Tên chương", chapter.getName()),
                makeNewContentPair(MediaType.MULTILINE_TEXT, "Nội dung", chapter.getContent())
        );
        return newContents;
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
    protected BaseChangeService getChangeService() {
        return ChapterChangeService.getInstance();
    }
}
