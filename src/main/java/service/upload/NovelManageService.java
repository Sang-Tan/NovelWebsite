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
        return new Chapter();
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
     * Change the novel info
     *
     * @param newNovelInfo
     * @param genres
     * @param image
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

    /**
     * @param performer
     * @param novelID
     * @return true if the user is the owner of the novel
     * @throws SQLException
     */
    public static boolean checkOwnership(User performer, int novelID) throws SQLException {
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

    public static void uploadNewChapter(int volumeID, String chapterName, String content) throws SQLException {
        Chapter chapter = createDefaultChapter();
        chapter.setName(chapterName);
        chapter.setContent(content);
        chapter.setVolumeId(volumeID);

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
}
