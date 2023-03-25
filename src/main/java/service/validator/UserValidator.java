package service.validator;

import core.SHA256Hashing;
import repository.UserRepository;

import java.sql.SQLException;


public class UserValidator {
    //username must include
    private static final String usernameRegex = "^[a-zA-Z0-9]+$";
    private static final String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
    private static final int USERNAME_MIN_LEN = 6;
    private static final int PASS_MIN_LEN = 6;

    /**
     * @param username must conatin only alphabet and number character, upercase is allowed
     * @return return true if username valid
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

    public static boolean isValidConfirmPassword(String password, String confirmPassword) {
        return !password.isEmpty() && password.equals(confirmPassword);
    }

    /**
     * Verify user credential return true if credential is correct
     *
     * @param userName
     * @param password original password (not hashed)
     * @return
     * @throws SQLException
     */
    public static boolean credentialVerify(String userName, String password) throws SQLException {
        return SHA256Hashing.computeHash(password).equals(UserRepository.getInstance().getByUsername(userName).getPassword());
    }
}
