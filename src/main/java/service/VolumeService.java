package service;

import model.Chapter;
import model.Volume;
import repository.VolumeRepository;

import java.sql.SQLException;
import java.util.List;

public class VolumeService {
    public static Chapter getFirstApprovedChapter(int volumeId) {
        try {

            Volume volume = VolumeRepository.getInstance().getById(volumeId);
            if (volume == null) {
                return null;
            }
            List<Chapter> chapters = volume.getChapters();
            for (Chapter chapter : chapters) {
                if (chapter.getApprovalStatus().equals(Chapter.APPROVE_STATUS_APPROVED)) {
                    return chapter;
                }
            }
            return null;
        }
        catch (SQLException e) {
            return null;
        }

    }
    public static boolean isVolumeHaveApprovedChapter(int volumeId) {
        return getFirstApprovedChapter(volumeId) != null;
    }
}
