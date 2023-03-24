package core.validator;

import repository.UserRepository;

import java.sql.SQLException;


public class UserValidator {
    //username must include
    private static String usernameRegex = "^[a-zA-Z0-9]+$";
    private static String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
    private static final int USERNAME_MIN_LEN = 3;
    private static final int PASS_MIN_LEN = 3;

    /**
     * @param username must conatin only alphabet and number character, upercase is allowed
     * @return
     */

    public static boolean isValidUsername(String username) {
        // Check if username is not empty and only contains alphanumeric characters
        return username.length() >= USERNAME_MIN_LEN && username.matches(usernameRegex);
    }

    public static boolean isUsernameExists(String username) throws SQLException {
        // Check if username is already exists in database
        if (UserRepository.getInstance().getByUsername(username) == null) {
            return false;
        }
        return true;

    }

    /**
     * @param password password must in contain atleast one capital letter, one lowercase letter and one number
     * @return
     */
    public static boolean isValidPassword(String password) {
        // Check if password is at least 8 characters long and contains at least one uppercase letter, one lowercase letter, and one digit
        return password.length() >= PASS_MIN_LEN && password.matches(passwordRegex);
    }

    public static boolean isValidDisplayName(String displayName) {
        // Check if display name is not empty
        return !displayName.isEmpty();
    }

    public static boolean validateConfirmPassword(String password, String confirmPassword) {
        return !password.isEmpty() && password.equals(confirmPassword);
    }
}
