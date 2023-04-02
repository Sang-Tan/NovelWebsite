package service.upload;

import core.FileUtil;
import io.github.cdimascio.dotenv.Dotenv;
import model.User;

import javax.servlet.http.Part;
import java.io.IOException;

public class AvatarUploadService {
    private  static final String DEFAULT_AVATAR = "/uploads/avatars/default.png";
    private static final String AVATAR_UPLOAD_PATH = Dotenv.load().get("AVATAR_UPLOAD_PATH");

    public static void uploadAvatar(User user, Part newAvatar) throws IOException {
        //if user has avatar
        if ((user.getAvatar() != null) && ((user.getAvatar() != DEFAULT_AVATAR)))
        {
            FileMapper uploadedAvatar = FileMapper.mapURI(user.getAvatar());

            uploadedAvatar.uploadFile(newAvatar.getInputStream(), true);

            user.setAvatar(uploadedAvatar.getURI());
        } else {
            //get new avatar extension
            String avatarExtension = FileUtil.getExtension(newAvatar.getSubmittedFileName());

            //create new avatar uploader
            FileMapper uploadAvatar = FileMapper.mapRandomFile("avatar", avatarExtension);

            //upload new avatar
            uploadAvatar.uploadFile(newAvatar.getInputStream(), false);

            user.setAvatar(uploadAvatar.getURI());
        }
    }
}
