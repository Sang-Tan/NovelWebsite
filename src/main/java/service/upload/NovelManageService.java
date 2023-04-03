package service.upload;

import core.FileUtil;
import io.github.cdimascio.dotenv.Dotenv;
import model.Chapter;
import model.Novel;
import model.Volume;
import repository.ChapterRepository;
import repository.NovelRepository;
import repository.VolumeRepository;

import javax.servlet.http.Part;
import java.awt.*;
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
        Chapter chapter = new Chapter();
        return chapter;
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

    public static void uploadNewNovel(String novelName, String summary, String status,
                                      Integer[] genres, Part image, int ownerID) throws IOException, SQLException {
        Novel novel = createDefaultNovel();
        novel.setName(novelName);
        novel.setSummary(summary);
        novel.setStatus(status);
        novel.setOwnerID(ownerID);

        FileMapper imageMapper = null;
        //create image mapper and upload image if exist
        if (image != null && image.getSize() > 0) {
            String extension = FileUtil.getExtension(image.getSubmittedFileName());
            imageMapper = FileMapper.mapRandomFile(VOLUME_COVER_DIR, extension);
            imageMapper.uploadFile(image.getInputStream());
            String imageURI = imageMapper.getURI();
            novel.setImage(imageURI);
        }


        try {
            //insert novel to database
            NovelRepository.getInstance().insert(novel);

            //insert genres to novel
            NovelRepository.getInstance().addGenresToNovel(novel.getId(), genres);

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

    public static void uploadNewVolume(int novelID, String volumeName, Part image) throws IOException, SQLException {
        Volume volume = createDefaultVolume();
        volume.setName(volumeName);
        volume.setNovelId(novelID);

        FileMapper imageMapper = null;
        //create image mapper and upload image if exist
        if (image != null && image.getSize() > 0) {
            String extension = FileUtil.getExtension(image.getSubmittedFileName());
            imageMapper = FileMapper.mapRandomFile(NOVEL_COVER_DIR, extension);
            imageMapper.uploadFile(image.getInputStream());
            String imageURI = imageMapper.getURI();
            volume.setImage(imageURI);
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
}
