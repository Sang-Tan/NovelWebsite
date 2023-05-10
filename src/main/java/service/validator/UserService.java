package service.validator;

import model.User;
import repository.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static User createDefaultUser() {
        User user = new User();
        user.setRole(User.ROLE_MEMBER);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setDisplayName("Anonymous");
        return user;
    }

    private static String getHashedPassword(String plainTextPassword) {
        return UserValidator.hashPassword(plainTextPassword);
    }

    public static void insertNewUser(User userInfo) throws SQLException {
        User user = createDefaultUser();
        if (userInfo.getUsername() != null) {
            user.setUsername(userInfo.getUsername());
        }
        user.setUsername(userInfo.getUsername());
        user.setPassword(getHashedPassword(userInfo.getPassword()));

        UserRepository.getInstance().insert(user);

    }

    /**
     * @return error message, null if no error
     */
    public static String changePassword(User user, String oldPassword, String newPassword) throws SQLException {
        String errorMessage = validatePasswordChange(user, oldPassword, newPassword);
        if (errorMessage != null) {
            return errorMessage;
        }

        user.setPassword(getHashedPassword(newPassword));
        UserRepository.getInstance().update(user);
        return null;
    }

    /**
     * @return error message, null if no error
     */
    private static String validatePasswordChange(User user, String oldPassword, String newPassword) throws SQLException {
        if (oldPassword == null || newPassword == null) {
            return "Vui lòng nhập đầy đủ thông tin";
        }

        if (oldPassword.equals(newPassword)) {
            return "Mật khẩu mới không được trùng với mật khẩu cũ";
        }

        String oldPasswordHash = getHashedPassword(oldPassword);
        if (!oldPasswordHash.equals(user.getPassword())) {
            return "Mật khẩu cũ không đúng";
        }

        if (!UserValidator.isValidPassword(newPassword)) {
            return "Mật khẩu mới không hợp lệ";
        }

        return null;
    }
}