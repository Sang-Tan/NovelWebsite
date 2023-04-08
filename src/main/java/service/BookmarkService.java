package service;

import model.Chapter;
import model.Volume;
import model.intermediate.ChapterMark;
import repository.ChapterRepository;
import repository.VolumeRepository;
import repository.intermediate.ChapterMarkRepository;

import java.sql.SQLException;

public class BookmarkService {

    private static ChapterMark getChapterMark(Integer userId, Integer chapterId) throws SQLException {
        if (chapterId == null || userId == null) {
            throw new IllegalArgumentException("chapterId or userId is null");
        }

        ChapterMark chapterMark = new ChapterMark();
        chapterMark.setChapterId(chapterId);
        chapterMark.setUserId(userId);

        return ChapterMarkRepository.getInstance().getByPrimaryKey(chapterMark);
    }

    public static String addBookmark(Integer userId, Integer chapterId) throws SQLException {
        ChapterMark chapterMark = getChapterMark(userId, chapterId);

        Chapter chapter = ChapterRepository.getInstance().getById(chapterId);
        Volume belongingVolume = VolumeRepository.getInstance().getById(chapter.getVolumeId());
        if (belongingVolume.getOrderIndex() == 1) {
            throw new IllegalArgumentException("Virtual chapter cannot be bookmarked");
        }

        if (chapterMark != null) {
            return "Đã đánh dấu chương này";
        }

        chapterMark = new ChapterMark();
        chapterMark.setChapterId(chapterId);
        chapterMark.setUserId(userId);

        ChapterMarkRepository.getInstance().insert(chapterMark);

        return null;
    }

    public static String removeBookmark(Integer userId, Integer chapterId) throws SQLException {
        ChapterMark chapterMark = getChapterMark(userId, chapterId);

        if (chapterMark == null) {
            return "Chương này chưa được đánh dấu";
        }

        ChapterMarkRepository.getInstance().delete(chapterMark);

        return null;
    }
}
