package service.admin;

import model.User;
import repository.UserRepository;
import service.validator.UserService;

import java.sql.SQLException;

public class ModeratorPromotionService {
    /**
     * Promote a user to moderator
     *
     * @param userId the user's id to promote
     * @return a string indicating the result of the operation, or null if the operation is successful
     */
    public static String promoteModerator(int userId) throws SQLException {
        User user = UserRepository.getInstance().getById(userId);
        if (user == null) {
            return "User not found";
        }
        if (user.getRole().equals(User.ROLE_ADMIN)) {
            return "Cannot change role of an admin";
        }
        if (user.getRole().equals(User.ROLE_MODERATOR)) {
            return "User is already a moderator";
        }
        user.setRole(User.ROLE_MODERATOR);
        UserRepository.getInstance().update(user);
        return null;
    }

    /**
     * Demote a user from moderator
     *
     * @param userId the user's id to demote
     * @return a string indicating the result of the operation, or null if the operation is successful
     */
    public static String demoteModerator(int userId) throws SQLException {
        User user = UserRepository.getInstance().getById(userId);
        if (user == null) {
            return "User not found";
        }
        if (user.getRole().equals(User.ROLE_ADMIN)) {
            return "Cannot change role of an admin";
        }
        if (user.getRole().equals(User.ROLE_MEMBER)) {
            return "User is already a member";
        }
        user.setRole(User.ROLE_MEMBER);
        UserRepository.getInstance().update(user);
        return null;
    }


}
