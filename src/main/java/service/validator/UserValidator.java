package service.validator;

import core.SHA256Hashing;
import model.Token;
import model.User;
import repository.TokenRepository;
import repository.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
     * Verify user credential return true if password is correct
     *
     * @param userName
     * @param plainTextPassword plain text password
     * @return true if password is correct
     * @throws SQLException
     */
    public static boolean credentialVerify(String userName, String plainTextPassword) throws SQLException {
        String storedHashPass = UserRepository.getInstance().getByUsername(userName).getPassword();
        String hashPass = hashPassword(plainTextPassword);
        return hashPass.equals(storedHashPass);
    }

    public static String hashPassword(String plainTextPassword) {
        return SHA256Hashing.computeHash(plainTextPassword);
    }

    public static User getUserInRequest(HttpServletRequest request) throws SQLException {
        //Get by token cookie
        Cookie cookie = TokenService.getTokenCookie(request.getCookies());
        if (cookie != null) {
            String hashedToken = TokenService.hashToken(cookie.getValue());
            Token token = TokenRepository.getInstance().getByHashedToken(hashedToken);
            if (token != null) {
                return UserRepository.getInstance().getById(token.getUserId());
            }
        }

        //Get by session
        HttpSession session = request.getSession();
        return getUserInSession(session);
    }

    private static User getUserInSession(HttpSession session) {
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        if (username == null || password == null) {
            return null;
        }
        try {
            if (UserValidator.credentialVerify(username, password)) {
                return UserRepository.getInstance().getByUsername(username);
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

}
