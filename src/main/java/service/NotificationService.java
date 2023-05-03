package service;

import model.Notification;
import repository.NotificationRepository;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class NotificationService {
    /**
     * @param userId
     * @param content
     * @param link
     * @return error message or null if success
     */
    public static String addNotification(Integer userId, String content, String link) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setContent(content);
        notification.setLink(link);

        try {
            NotificationRepository.getInstance().insert(notification);
        } catch (SQLException e) {
            return "Lỗi Cơ sở dữ liệu";
        }
        return null;
    }

    /**
     * @param userId
     * @param notificationsId HashSet of notification id need to be removed
     * @return error message or null if success
     */

    public static String removeNotification(Integer userId, HashSet<Integer> notificationsId) throws SQLException {
        if (notificationsId == null || notificationsId.isEmpty()) {
            throw new IllegalArgumentException("notificationsId is null or empty");
        }
        for (Integer notificationId : notificationsId) {
            Notification notification = null;
            notification = NotificationRepository.getInstance().getById(notificationId);
            if (notification == null) {
                return "Thông báo không tồn tại";
            }
            if (notification.getUserId() != userId) {
                return "Thông báo không thuộc về bạn";
            }
        }
        for (Integer notificationId : notificationsId) {
            NotificationRepository.getInstance().deleteById(notificationId);
        }
        return null;
    }

    /**
     * Extract a list of interger from a string of interger separated by comma
     *
     * @param notificationIDString string to extract interger
     * @return list of interger
     */

    public static HashSet<Integer> extractNotificationId(String notificationIDString) {

        HashSet<Integer> notificationIDList = null;
        String regex = "^[0-9,]+$";
        if (!(notificationIDString == null) && !notificationIDString.isEmpty() && notificationIDString.matches(regex)) {
            String[] arrGenresIDString = notificationIDString.split(",");
            // convert string array to hashset
            notificationIDList = Arrays.stream(arrGenresIDString).map(Integer::parseInt).collect(Collectors.toCollection(HashSet::new));
        }
        return notificationIDList;
    }
}
