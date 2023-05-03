package service;

import model.Notification;
import repository.NotificationRepository;

import java.security.Timestamp;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;

public class NotificationService {
    /**
     *
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
     *
     * @param userId
     * @param notificationsId HashSet of notification id need to be removed
     * @return error message or null if success
     */

    public static String removeNotification(Integer userId, HashSet<Integer> notificationsId) {
        if (notificationsId == null || notificationsId.isEmpty()) {
            throw new IllegalArgumentException("notificationsId is null or empty");
        }
        for(Integer notificationId : notificationsId) {
            Notification notification = null;
            try {
                notification = NotificationRepository.getInstance().getById(notificationId);
            } catch (SQLException e) {
                return "Lỗi Cơ sở dữ liệu";
            }
            if (notification == null) {
                return "Thông báo không tồn tại";
            }
            if (notification.getUserId() != userId) {
                return "Thông báo không thuộc về bạn";
            }
        }
        for(Integer notificationId : notificationsId) {
            try {
                NotificationRepository.getInstance().deleteById(notificationId);
            } catch (SQLException e) {
                return "Lỗi Cơ sở dữ liệu";
            }
        }
        return null;
    }
}
