package core.string_process;

import java.sql.Timestamp;

public class TimeConverter {
    public static String convertToTimeAgo(long timeInMilis) {
        long now = System.currentTimeMillis();
        long diff = now - timeInMilis;
        if (diff < 1000) {
            return "hiện tại";
        } else if (diff < 60 * 1000) {
            return diff / 1000 + " giây trước";
        } else if (diff < 60 * 60 * 1000) {
            return diff / (60 * 1000) + " phút trước";
        } else if (diff < 24 * 60 * 60 * 1000) {
            return diff / (60 * 60 * 1000) + " giờ trước";
        } else if (diff < 30 * 24 * 60 * 60 * 1000L) {
            return diff / (24 * 60 * 60 * 1000L) + " ngày trước";
        } else if (diff < 12 * 30 * 24 * 60 * 60 * 1000L) {
            return diff / (30 * 24 * 60 * 60 * 1000L) + " tháng trước";
        } else return diff / (12 * 30 * 24 * 60 * 60 * 1000L) + " năm trước";
    }

    public static String convertToTimeAgo(Timestamp timestamp) {
        return convertToTimeAgo(timestamp.getTime());
    }

    public static String convertToVietnameseTime(long timeInMilis) {
        return new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date(timeInMilis));
    }

    public static String convertToVietnameseTime(Timestamp timestamp) {
        return convertToVietnameseTime(timestamp.getTime());
    }
}
