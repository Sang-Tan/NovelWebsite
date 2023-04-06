package service.upload;

import core.FileUtil;
import io.github.cdimascio.dotenv.Dotenv;
import model.Chapter;
import model.Novel;
import model.User;
import model.Volume;
import repository.ChapterRepository;
import repository.NovelRepository;
import repository.VolumeRepository;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class NovelManageService {
    private static final String NOVEL_COVER_DIR = Dotenv.load().get("COVER_UPLOAD_PATH");
    private static final String VOLUME_COVER_DIR = Dotenv.load().get("COVER_UPLOAD_PATH");

    private static Novel createDefaultNovel() {
        Novel novel = new Novel();
        novel.setImage(Novel.DEFAULT_IMAGE);
        novel.setPending(true);
        return novel;
    }

    private static Volume createDefaultVolume() {
        Volume volume = new Volume();
        volume.setImage(Volume.DEFAULT_IMAGE);
        return volume;
    }

    private static Chapter createDefaultChapter() {
        Chapter newChap = new Chapter();
        newChap.setPending(true);
        return newChap;
    }

    private static Volume createVirtualVolume(int novelID) {
        Volume volume = new Volume();
        volume.setName("Virtual");
        volume.setNovelId(novelID);
        return volume;
    }

    private static Chapter createVirtualChapter(int volumeID) {
        Chapter chapter = new Chapter();
        chapter.setName("Virtual");
        chapter.setVolumeId(volumeID);
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
     *
     * @param newNovelInfo
     * @param genres
     * @param image
     * @throws IOException
     * @throws SQLException
     */
    public static void updateNovelInfo(Novel newNovelInfo, int[] genres, Part image) throws IOException, SQLException {
        if (newNovelInfo == null) {
            throw new IllegalArgumentException("Novel cannot be null");
        }
        if (newNovelInfo.getId() == 0) {
            throw new IllegalArgumentException("Novel ID invalid");
        }
        Novel novelInDb = NovelRepository.getInstance().getById(newNovelInfo.getId());
        if (novelInDb == null) {
            throw new IllegalArgumentException("Novel not found");
        }

        novelInDb.setName(newNovelInfo.getName());
        novelInDb.setSummary(newNovelInfo.getSummary());
        novelInDb.setStatus(newNovelInfo.getStatus());
        novelInDb.setPending(newNovelInfo.isPending());

        FileMapper imageMapper = null;
        //create image mapper and upload image if exist
        if (image != null && image.getSize() > 0) {
            if (novelInDb.getImage() == null || novelInDb.getImage().equals(Novel.DEFAULT_IMAGE)) {
                //if the novel has no image, create a new mapper
                imageMapper = uploadImage(NOVEL_COVER_DIR, image);
                novelInDb.setImage(imageMapper.getURI());
            } else {
                //if the novel has an image, update the image
                imageMapper = FileMapper.mapURI(novelInDb.getImage());
                imageMapper.uploadFile(image.getInputStream(), true);
            }
        }
        NovelRepository.getInstance().update(novelInDb);
        NovelRepository.getInstance().changeNovelGenres(novelInDb.getId(), genres);
    }

    public static void updateVolumeInfo(Volume newVolumeInfo, Part image) throws SQLException, IOException {
        if (newVolumeInfo == null) {
            throw new IllegalArgumentException("Volume cannot be null");
        }
        if (newVolumeInfo.getId() == 0) {
            throw new IllegalArgumentException("Volume ID invalid");
        }
        Volume volumeInDb = VolumeRepository.getInstance().getById(newVolumeInfo.getId());
        if (volumeInDb == null) {
            throw new IllegalArgumentException("Volume not found");
        }

        volumeInDb.setName(newVolumeInfo.getName());

        FileMapper imageMapper = null;
        //create image mapper and upload image if exist
        if (image != null && image.getSize() > 0) {
            if (volumeInDb.getImage() == null || volumeInDb.getImage().equals(Volume.DEFAULT_IMAGE)) {
                //if the volume has no image, create a new mapper
                imageMapper = uploadImage(VOLUME_COVER_DIR, image);
                volumeInDb.setImage(imageMapper.getURI());
            } else {
                //if the volume has an image, update the image
                imageMapper = FileMapper.mapURI(volumeInDb.getImage());
                imageMapper.uploadFile(image.getInputStream(), true);
            }
        }
        VolumeRepository.getInstance().update(volumeInDb);
    }

    public static void updateChapterInfo(Chapter newChapterInfo) throws SQLException {
        if (newChapterInfo == null) {
            throw new IllegalArgumentException("Chapter cannot be null");
        }
        if (newChapterInfo.getId() == 0) {
            throw new IllegalArgumentException("Chapter ID invalid");
        }
        Chapter chapterInDb = ChapterRepository.getInstance().getById(newChapterInfo.getId());
        if (chapterInDb == null) {
            throw new IllegalArgumentException("Chapter not found");
        }

        chapterInDb.setName(newChapterInfo.getName());
        chapterInDb.setContent(newChapterInfo.getContent());
        ChapterRepository.getInstance().update(chapterInDb);
    }

    /**
     * @param performer
     * @param novelID
     * @return true if the user is the owner of the novel
     * @throws SQLException
     */
    public static boolean checkNovelOwnership(User performer, int novelID) throws SQLException {
        if (performer == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (novelID == 0) {
            throw new IllegalArgumentException("Novel ID invalid");
        }

        int novelOwnerId = NovelRepository.getInstance().getById(novelID).getOwnerID();
        if (novelOwnerId != performer.getId()) {
            return false;
        }
        return true;
    }

    private static boolean checkVolumeOwnership(User performer, int volumeID) throws SQLException {
        if (performer == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (volumeID == 0) {
            throw new IllegalArgumentException("Volume ID invalid");
        }

        int volumeOwnerId = VolumeRepository.getInstance().getById(volumeID).getNovelId();
        if (volumeOwnerId != performer.getId()) {
            return false;
        }
        return true;
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
     * @param novelInfo
     * @param genres
     * @param image
     * @return null if novel is valid, an error string if there is an error
     * @throws IOException
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
        if (image == null) {
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
        if (image == null) {
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
        if (imageURI == Novel.DEFAULT_IMAGE) {
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
        if (imageURI == Volume.DEFAULT_IMAGE) {
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
}
