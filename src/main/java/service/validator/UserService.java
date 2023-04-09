package service.validator;

import model.User;
import repository.UserRepository;

import java.sql.SQLException;

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
}