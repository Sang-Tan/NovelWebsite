package service.mod;

import model.Novel;
import repository.NovelRepository;
import service.NotificationService;
import service.upload.NovelManageService;

import java.sql.SQLException;

public class DeleteNovelService {
    public static String deleteNovel(int novelId, String reason) throws SQLException {
        String error = getDeleteNovelError(novelId, reason);
        if (error != null) {
            return error;
        }

        Novel novel = NovelRepository.getInstance().getById(novelId);
        NovelManageService.deleteNovel(novelId);
        NotificationService.addNotification(novel.getOwnerID(),
                String.format("Tiểu thuyết %s của bạn đã bị xóa vì : %s", novel.getName(), reason), null);
        return null;
    }

    /**
     * @return null if valid, error message if invalid
     */
    public static String getDeleteNovelError(int novelId, String reason) {
        if (reason == null || reason.isEmpty()) {
            return "Phải nhập lí do xóa truyện";
        }
        return null;
    }
}
