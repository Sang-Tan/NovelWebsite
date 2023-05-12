package service;

import model.Chapter;
import model.Novel;
import model.Volume;
import repository.ChapterRepository;
import repository.NovelRepository;
import repository.VolumeRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NovelModeratingService {
    private volatile static NovelModeratingService instance;

    public static NovelModeratingService getInstance() {
        if (instance == null) {
            synchronized (NovelModeratingService.class) {
                if (instance == null) {
                    instance = new NovelModeratingService();
                }
            }
        }
        return instance;
    }

    private NovelModeratingService() {
    }

    public List<Novel> getNovelQueueToBeModerated() throws SQLException {
        return NovelRepository.getInstance().getInModeratingNovelsQueue();
    }

    public List<Volume> getVolumeQueueToBeModerated() throws SQLException {
        List<Volume> volumes = VolumeRepository.getInstance().getInModeratingVolumeQueue();
        List<Volume> result = new ArrayList<>();
        for (Volume volume : volumes) {
            if (isBelongNovelApproved(volume)) {
                result.add(volume);
            }
        }
        return result;
    }

    public List<Chapter> getChapterQueueToBeModerated() throws SQLException {
        List<Chapter> chapters = ChapterRepository.getInstance().getInModeratingChapterQueue();
        List<Chapter> result = new ArrayList<>();
        for (Chapter chapter : chapters) {
            if (isBelongVolumeApproved(chapter)) {
                result.add(chapter);
            }
        }
        return result;
    }

    private boolean isBelongNovelApproved(Volume volume) throws SQLException {
        Novel novel = volume.getBelongNovel();
        return novel.getApprovalStatus().equals(Novel.APPROVE_STATUS_APPROVED);
    }

    private boolean isBelongVolumeApproved(Chapter chapter) throws SQLException {
        Volume volume = chapter.getBelongVolume();
        if (!volume.getApprovalStatus().equals(Volume.APPROVE_STATUS_APPROVED)) {
            return false;
        }
        return isBelongNovelApproved(volume);
    }
}
