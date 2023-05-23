package service.admin;

import model.User;

import java.util.List;

/**
 * @param selectedUsers List of users selected by the selector with the given condition within a range
 * @param totalUsers    Total number of users that match the condition
 */
public record UserSelectResult(List<User> selectedUsers, int totalUsers) {
}
