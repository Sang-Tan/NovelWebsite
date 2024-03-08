package service.upload;

import core.FileUtil;
import core.config.ApplicationPropertiesReader;
import core.logging.BasicLogger;
import model.Chapter;
import model.Novel;
import model.User;
import model.Volume;
import repository.ChapterRepository;
import repository.NovelRepository;
import repository.VolumeRepository;
import service.upload_change.ChapterChangeService;
import service.upload_change.NovelChangeService;
import service.upload_change.VolumeChangeService;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class NovelManageService {
    private static final String NOVEL_COVER_DIR =
            ApplicationPropertiesReader.getInstance().getProperty("upload.novel.cover.path");
    private static final String VOLUME_COVER_DIR =
            ApplicationPropertiesReader.getInstance().getProperty("upload.volume.cover.path");

    private static Novel createDefaultNovel() {
        Novel novel = new Novel();
        novel.setImage(Novel.DEFAULT_IMAGE);
        novel.setApprovalStatus(Novel.APPROVE_STATUS_PENDING);
        return novel;
    }

    private static Volume createDefaultVolume() {
        Volume volume = new Volume();
        volume.setImage(Volume.DEFAULT_IMAGE);
        return volume;
    }

    private static Chapter createDefaultChapter() {
        Chapter newChap = new Chapter();
        newChap.setApprovalStatus(Chapter.APPROVE_STATUS_PENDING);
        return newChap;
    }

    private static Volume createVirtualVolume(int novelID) {
        Volume volume = new Volume();
        volume.setName("Virtual");
        volume.setApprovalStatus(Volume.APPROVE_STATUS_APPROVED);
        volume.setNovelId(novelID);
        return volume;
    }

    private static Chapter createVirtualChapter(int volumeID) {
        Chapter chapter = new Chapter();
        chapter.setName("Virtual");
        chapter.setVolumeId(volumeID);
        chapter.setApprovalStatus(Chapter.APPROVE_STATUS_APPROVED);
        return chapter;
    }

    private static FileMapper uploadImage(String uploadDir, Part image) throws IOException {
        String extension = FileUtil.getExtension(image.getSubmittedFileName());
        FileMapper imageMapper = FileMapper.mapRandomFile(uploadDir, extension);
        imageMapper.uploadFile(image.getInputStream());
        return imageMapper;
    }

    public static void uploadNewNovel(Novel novelInfo, int[] genres, Part image, int ownerID) throws IOException, SQLException {
        Novel novel = createDefaultNovel();
        novel.setName(novelInfo.getName());
        novel.setSummary(novelInfo.getSummary());
        novel.setStatus(novelInfo.getStatus());
        novel.setOwnerID(ownerID);

        FileMapper imageMapper = null;
        //create image mapper and upload image if exist
        if (image != null && image.getSize() > 0) {
            imageMapper = uploadImage(NOVEL_COVER_DIR, image);
            novel.setImage(imageMapper.getURI());
        }

        try {
            //insert novel to database
            NovelRepository.getInstance().insert(novel);

            //insert genres to novel
            NovelRepository.getInstance().changeNovelGenres(novel.getId(), genres);

            //create a virtual volume
            Volume volume = createVirtualVolume(novel.getId());
            VolumeRepository.getInstance().insert(volume);

            //create a virtual chapter
            Chapter chapter = createVirtualChapter(volume.getId());
            ChapterRepository.getInstance().insert(chapter);

        } catch (SQLException e) {
            //if insert failed, delete the image
            if (imageMapper != null) {
                imageMapper.delete();
            }
            throw e;
        }
    }

    /**
     * Change the novel's information
     */
    public static void updateNovelInfo(Novel newNovelInfo, int[] genres, Part image) throws IOException, SQLException {
        if (newNovelInfo == null) {
            throw new IllegalArgumentException("Novel cannot be null");
        }

        if (newNovelInfo.getId() == 0) {
            throw new IllegalArgumentException("Novel ID invalid");
        }

        if (NovelChangeService.getInstance().waitingForModeration(newNovelInfo.getId())) {
            throw new IllegalArgumentException("This novel is not editable in current state");
        }

        Novel novelInDb = NovelRepository.getInstance().getById(newNovelInfo.getId());
        if (novelInDb == null) {
            throw new IllegalArgumentException("Novel not found");
        }

        novelInDb.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        if (novelInDb.getApprovalStatus().equals(Novel.APPROVE_STATUS_APPROVED)) {
            updateNovelInfoAndCreateChange(novelInDb, newNovelInfo, genres, image);
        } else {
            if (novelInDb.getApprovalStatus().equals(Novel.APPROVE_STATUS_REJECTED)) {
                novelInDb.setApprovalStatus(Novel.APPROVE_STATUS_PENDING);
            }
            updateNovelInfoOnly(novelInDb, newNovelInfo, genres, image);
        }
    }

    private static void updateNovelInfoAndCreateChange(Novel oldNovelInfo, Novel newNovelInfo, int[] genres, Part image) throws IOException, SQLException {
        if (image != null && image.getSize() > 0) {
            FileMapper imageMapper = FileMapper.mapRandomFile(NOVEL_COVER_DIR, FileUtil.getExtension(image.getSubmittedFileName()));
            imageMapper.uploadFile(image.getInputStream());
            newNovelInfo.setImage(imageMapper.getURI());
        }

        NovelChangeService.getInstance().createChange(oldNovelInfo, newNovelInfo);

        oldNovelInfo.setStatus(newNovelInfo.getStatus());

        NovelRepository.getInstance().update(oldNovelInfo);
        NovelRepository.getInstance().changeNovelGenres(oldNovelInfo.getId(), genres);
    }

    private static void updateNovelInfoOnly(Novel oldNovelInfo, Novel newNovelInfo, int[] genres, Part image) throws IOException, SQLException {
        oldNovelInfo.setName(newNovelInfo.getName());
        oldNovelInfo.setSummary(newNovelInfo.getSummary());
        oldNovelInfo.setStatus(newNovelInfo.getStatus());

        FileMapper imageMapper;
        //create image mapper and upload image if exist
        if (image != null && image.getSize() > 0) {
            if (oldNovelInfo.getImage() == null || oldNovelInfo.getImage().equals(Novel.DEFAULT_IMAGE)) {
                //if the novel has no image, create a new mapper
                imageMapper = uploadImage(NOVEL_COVER_DIR, image);
                oldNovelInfo.setImage(imageMapper.getURI());
            } else {
                //if the novel has an image, update the image
                imageMapper = FileMapper.mapURI(oldNovelInfo.getImage());
                imageMapper.uploadFile(image.getInputStream(), true);
            }
        }
        NovelRepository.getInstance().update(oldNovelInfo);
        NovelRepository.getInstance().changeNovelGenres(oldNovelInfo.getId(), genres);
    }

    public static void updateVolumeInfo(Volume newVolumeInfo, Part image) throws SQLException, IOException {
        if (newVolumeInfo == null) {
            throw new IllegalArgumentException("Volume cannot be null");
        }
        if (newVolumeInfo.getId() == 0) {
            throw new IllegalArgumentException("Volume ID invalid");
        }

        if (VolumeChangeService.getInstance().waitingForModeration(newVolumeInfo.getId())) {
            throw new IllegalArgumentException("This volume is not editable in current state");
        }
        Volume volumeInDb = VolumeRepository.getInstance().getById(newVolumeInfo.getId());
        if (volumeInDb == null) {
            throw new IllegalArgumentException("Volume not found");
        }

        volumeInDb.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        if (volumeInDb.getApprovalStatus().equals(Volume.APPROVE_STATUS_APPROVED)) {
            updateVolumeInfoAndCreateChange(volumeInDb, newVolumeInfo, image);
        } else {
            if (volumeInDb.getApprovalStatus().equals(Volume.APPROVE_STATUS_REJECTED)) {
                volumeInDb.setApprovalStatus(Volume.APPROVE_STATUS_PENDING);
            }
            updateVolumeInfoOnly(volumeInDb, newVolumeInfo, image);
        }
    }

    private static void updateVolumeInfoAndCreateChange(Volume oldVolumeInfo, Volume newVolumeInfo, Part image) throws IOException, SQLException {
        if (image != null && image.getSize() > 0) {
            FileMapper imageMapper = FileMapper.mapRandomFile(VOLUME_COVER_DIR, FileUtil.getExtension(image.getSubmittedFileName()));
            imageMapper.uploadFile(image.getInputStream());
            newVolumeInfo.setImage(imageMapper.getURI());
        }

        VolumeChangeService.getInstance().createChange(oldVolumeInfo, newVolumeInfo);
        VolumeRepository.getInstance().update(oldVolumeInfo);
    }

    private static void updateVolumeInfoOnly(Volume oldVolumeInfo, Volume newVolumeInfo, Part image) throws IOException, SQLException {
        oldVolumeInfo.setName(newVolumeInfo.getName());

        FileMapper imageMapper;
        //create image mapper and upload image if exist
        if (image != null && image.getSize() > 0) {
            if (oldVolumeInfo.getImage() == null || oldVolumeInfo.getImage().equals(Volume.DEFAULT_IMAGE)) {
                //if the volume has no image, create a new mapper
                imageMapper = uploadImage(VOLUME_COVER_DIR, image);
                oldVolumeInfo.setImage(imageMapper.getURI());
            } else {
                //if the volume has an image, update the image
                imageMapper = FileMapper.mapURI(oldVolumeInfo.getImage());
                imageMapper.uploadFile(image.getInputStream(), true);
            }
        }
        VolumeRepository.getInstance().update(oldVolumeInfo);
    }

    public static void updateChapterInfo(Chapter newChapterInfo) throws SQLException {
        if (newChapterInfo == null) {
            throw new IllegalArgumentException("Chapter cannot be null");
        }
        if (newChapterInfo.getId() == 0) {
            throw new IllegalArgumentException("Chapter ID invalid");
        }

        if (ChapterChangeService.getInstance().waitingForModeration(newChapterInfo.getId())) {
            throw new IllegalArgumentException("This chapter is not editable in current state");
        }

        Chapter chapterInDb = ChapterRepository.getInstance().getById(newChapterInfo.getId());
        if (chapterInDb == null) {
            throw new IllegalArgumentException("Chapter not found");
        }

        chapterInDb.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        if (chapterInDb.getApprovalStatus().equals(Chapter.APPROVE_STATUS_APPROVED)) {
            updateChapterInfoAndCreateChange(chapterInDb, newChapterInfo);
        } else {
            if (chapterInDb.getApprovalStatus().equals(Chapter.APPROVE_STATUS_REJECTED)) {
                chapterInDb.setApprovalStatus(Chapter.APPROVE_STATUS_PENDING);
            }
            updateChapterInfoOnly(chapterInDb, newChapterInfo);
        }
    }

    private static void updateChapterInfoAndCreateChange(Chapter oldChapterInfo, Chapter newChapterInfo) throws SQLException {
        ChapterChangeService.getInstance().createChange(oldChapterInfo, newChapterInfo);
    }

    private static void updateChapterInfoOnly(Chapter oldChapterInfo, Chapter newChapterInfo) throws SQLException {
        oldChapterInfo.setName(newChapterInfo.getName());
        oldChapterInfo.setContent(newChapterInfo.getContent());
        ChapterRepository.getInstance().update(oldChapterInfo);
    }

    /**
     * @return true if the user is the owner of the novel
     */
    public static boolean checkNovelOwnership(User performer, int novelID) throws SQLException {
        if (performer == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (novelID == 0) {
            throw new IllegalArgumentException("Novel ID invalid");
        }

        int novelOwnerId = NovelRepository.getInstance().getById(novelID).getOwnerID();
        return novelOwnerId == performer.getId();
    }

    private static boolean checkVolumeOwnership(User performer, int volumeID) throws SQLException {
        if (performer == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (volumeID == 0) {
            throw new IllegalArgumentException("Volume ID invalid");
        }

        int volumeOwnerId = VolumeRepository.getInstance().getById(volumeID).getNovelId();
        return volumeOwnerId == performer.getId();
    }

    public static void uploadNewVolume(Volume newVolumeInfo, Part image) throws IOException, SQLException {
        Volume volume = createDefaultVolume();
        volume.setName(newVolumeInfo.getName());
        volume.setNovelId(newVolumeInfo.getNovelId());

        FileMapper imageMapper = null;
        //create image mapper and upload image if exist
        if (image != null && image.getSize() > 0) {
            imageMapper = uploadImage(VOLUME_COVER_DIR, image);
            volume.setImage(imageMapper.getURI());
        }

        try {
            //insert volume to database
            VolumeRepository.getInstance().insert(volume);
        } catch (SQLException e) {
            //if insert failed, delete the image
            if (imageMapper != null) {
                imageMapper.delete();
            }
            throw e;
        }
    }

    public static void uploadNewChapter(Chapter newChapterInfo) throws SQLException {
        if (newChapterInfo == null) {
            throw new IllegalArgumentException("Chapter cannot be null");
        }
        if (newChapterInfo.getVolumeId() == 0) {
            throw new IllegalArgumentException("Volume ID invalid");
        }

        Chapter chapter = createDefaultChapter();
        chapter.setName(newChapterInfo.getName());
        chapter.setContent(newChapterInfo.getContent());
        chapter.setVolumeId(newChapterInfo.getVolumeId());

        //insert chapter to database
        ChapterRepository.getInstance().insert(chapter);

    }

    /**
     * Check if upload novel is valid or not
     *
     * @return null if novel is valid, an error string if there is an error
     */
    public static String validateUploadNovel(Novel novelInfo, int[] genres, Part image) throws IOException {
        String novelName = novelInfo.getName();
        String summary = novelInfo.getSummary();
        String status = novelInfo.getStatus();

        if (novelName == null || novelName.isEmpty()) {
            return "Tên truyện không được để trống";
        }
        if (summary == null || summary.isEmpty()) {
            return "Mô tả không được để trống";
        }
        if (status == null || status.isEmpty()) {
            return "Trạng thái không được để trống";
        }
        if (genres == null || genres.length == 0) {
            return "Thể loại không được để trống";
        }
        if (image != null && image.getSize() > 0) {
            if (!FileUtil.isImage(image.getInputStream())) {
                return "Ảnh bìa không hợp lệ";
            }
        }
        return null;
    }

    public static String validateUploadVolume(Volume newVolumeInfo, Part image) throws IOException {
        String volumeName = newVolumeInfo.getName();

        if (volumeName == null || volumeName.isEmpty()) {
            return "Tên volume không được để trống";
        }
        if (image != null && image.getSize() > 0) {
            if (!FileUtil.isImage(image.getInputStream())) {
                return "Ảnh bìa không hợp lệ";
            }
        }
        return null;
    }

    public static String validateUploadChapter(Chapter newChapterInfo) {
        String chapterName = newChapterInfo.getName();
        String content = newChapterInfo.getContent();

        if (chapterName == null || chapterName.isEmpty()) {
            return "Tên chương không được để trống";
        }
        if (content == null || content.isEmpty()) {
            return "Nội dung không được để trống";
        }
        return null;
    }

    public static Novel getNovelByID(int novelID) throws SQLException {
        return NovelRepository.getInstance().getById(novelID);
    }

    public static Novel getNovelByVolumeID(int volumeID) throws SQLException {
        return NovelRepository.getInstance().getByVolumeID(volumeID);
    }

    public static Novel getNovelByChapterID(int chapterID) throws SQLException {
        Volume volume = VolumeRepository.getInstance().getByChapterId(chapterID);
        return NovelRepository.getInstance().getByVolumeID(volume.getId());
    }

    public static Volume getVolumeByID(int volumeID) throws SQLException {
        return VolumeRepository.getInstance().getById(volumeID);
    }

    public static Chapter getChapterByID(int chapterID) throws SQLException {
        return ChapterRepository.getInstance().getById(chapterID);
    }

    public static void deleteNovel(int novelID) throws SQLException {
        Novel novelToDelete = NovelRepository.getInstance().getById(novelID);
        deleteNovel(novelToDelete);
    }

    private static void deleteNovel(Novel novelToDelete) throws SQLException {
        String imageURI = novelToDelete.getImage();
        if (imageURI == null) {
            BasicLogger.getInstance().getLogger().warning(String.format("Novel %d has null url image", novelToDelete.getId()));
        } else if (imageURI.equals(Novel.DEFAULT_IMAGE)) {
            imageURI = null;
        }
        //delete all belonging volumes
        List<Volume> volumes = novelToDelete.getVolumes();
        for (Volume volume : volumes) {
            deleteVolume(volume);
        }

        Volume virtualVolume = VolumeRepository.getInstance().getVirtualVolumeByNovelId(novelToDelete.getId());
        if (virtualVolume != null) {
            deleteVolume(virtualVolume);
        }


        NovelRepository.getInstance().delete(novelToDelete);
        if (imageURI != null) {
            FileMapper imageMapper = FileMapper.mapURI(imageURI);
            imageMapper.delete();
        }
    }

    public static void deleteVolume(int volumeID) throws SQLException {
        Volume volumeToDelete = VolumeRepository.getInstance().getById(volumeID);
        deleteVolume(volumeToDelete);
    }

    private static void deleteVolume(Volume volumeToDelete) throws SQLException {
        String imageURI = volumeToDelete.getImage();
        if (imageURI == null) {
            BasicLogger.getInstance().getLogger().warning(String.format("Volume %d image url is null", volumeToDelete.getId()));
        } else if (imageURI.equals(Volume.DEFAULT_IMAGE)) {
            imageURI = null;
        }

        //delete all belonging chapters
        List<Chapter> chapters = volumeToDelete.getChapters();
        for (Chapter chapter : chapters) {
            deleteChapter(chapter);
        }

        VolumeRepository.getInstance().delete(volumeToDelete);
        if (imageURI != null) {
            FileMapper imageMapper = FileMapper.mapURI(imageURI);
            imageMapper.delete();
        }
    }

    public static void deleteChapter(int chapterID) throws SQLException {
        Chapter chapterToDelete = ChapterRepository.getInstance().getById(chapterID);
        deleteChapter(chapterToDelete);
    }

    private static void deleteChapter(Chapter chapterToDelete) throws SQLException {
        ChapterRepository.getInstance().delete(chapterToDelete);
    }

    public static boolean isVirtualChapter(Chapter chapter) throws SQLException {
        return ChapterRepository.getInstance().isVirtualChapter(chapter);
    }
}