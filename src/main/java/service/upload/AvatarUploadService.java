package service.upload;

import core.FileUtil;
import core.config.ApplicationPropertiesReader;
import model.User;

import javax.servlet.http.Part;
import java.io.IOException;

public class AvatarUploadService {
    private static final String AVATAR_UPLOAD_PATH =
            ApplicationPropertiesReader.getInstance().getProperty("upload.avatar.path");

    public static void uploadAvatar(User user, Part newAvatar) throws IOException {
        //if user has avatar
        if ((user.getAvatar() != null) && (!(user.getAvatar().equals(User.DEFAULT_AVATAR)))) {
            FileMapper uploadedAvatar = FileMapper.mapURI(user.getAvatar());

            uploadedAvatar.uploadFile(newAvatar.getInputStream(), true);

            user.setAvatar(uploadedAvatar.getURI());
        } else {
            //get new avatar extension
            String avatarExtension = FileUtil.getExtension(newAvatar.getSubmittedFileName());

            //create new avatar uploader
            FileMapper uploadAvatar = FileMapper.mapRandomFile(AVATAR_UPLOAD_PATH, avatarExtension);

            //upload new avatar
            uploadAvatar.uploadFile(newAvatar.getInputStream(), false);

            user.setAvatar(uploadAvatar.getURI());
        }
    }
}
