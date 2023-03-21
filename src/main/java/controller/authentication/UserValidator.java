package controller.authentication;

import database.UserRepository;

import java.sql.Connection;

public class UserValidator {
    private static String usernameRegex = "^[a-zA-Z0-9]+$";
    private static String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
    public static boolean isValidUsername(String username) {
        // Check if username is not empty and only contains alphanumeric characters
        return !(username.length() >= 8) && username.isEmpty() && username.matches(usernameRegex);

    }
    public static boolean isUsernameExists(String username) {
        // Check if username is already exists in database
        if(UserRepository.getInstance().getByUsername(username) != null) {
            return true;
        }
        return false;

    }

    public static boolean isValidPassword(String password) {
        // Check if password is at least 8 characters long and contains at least one uppercase letter, one lowercase letter, and one digit
        return password.length() >= 8 && password.matches(passwordRegex);
    }

    public static boolean isValidDisplayName(String displayName) {
        // Check if display name is not empty
        return !displayName.isEmpty();
    }

    public static boolean validateConfirmPassword(String password, String confirmPassword) {
        return !password.isEmpty() && password.equals(confirmPassword);
    }
}
