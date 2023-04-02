package service.upload;

import core.FileUtil;
import io.github.cdimascio.dotenv.Dotenv;
import model.Novel;
import repository.NovelRepository;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

public class NovelManageService {
    private static final String NOVEL_COVER_DIR = Dotenv.load().get("COVER_UPLOAD_PATH");

    public static void uploadNewNovel(String novelName, String summary, String status,
                                      Integer[] genres, Part image, int ownerID) throws IOException, SQLException {
        Novel novel = new Novel();
        novel.setName(novelName);
        novel.setSummary(summary);
        novel.setStatus(status);
        novel.setOwnerID(ownerID);

        FileMapper imageMapper = null;
        //create image mapper and upload image if exist
        if (image.getSize() > 0) {
            String extension = FileUtil.getExtension(image.getSubmittedFileName());
            imageMapper = FileMapper.mapRandomFile(NOVEL_COVER_DIR, extension);
            imageMapper.uploadFile(image.getInputStream());
            String imageURI = imageMapper.getURI();
            novel.setImage(imageURI);
        }


        try {
            //insert novel to database
            novel = NovelRepository.getInstance().insert(novel);

            //insert genres to novel
            NovelRepository.getInstance().addGenresToNovel(novel.getId(), genres);

        } catch (SQLException e) {
            //if insert failed, delete the image
            if (imageMapper != null) {
                imageMapper.delete();
            }
            throw e;
        }
    }
}
