package service;

import model.intermediate.NovelFavourite;
import repository.NovelFavouriteRepository;

import java.sql.SQLException;

public class ReadingListService {
    private static NovelFavourite getFollowedNovel(Integer novelId, Integer userId) throws SQLException {
        if (novelId == null || userId == null) {
            throw new IllegalArgumentException("novelId or userId is null");
        }

        NovelFavourite novelFavourite = new NovelFavourite();
        novelFavourite.setNovelId(novelId);
        novelFavourite.setUserId(userId);

        return NovelFavouriteRepository.getInstance().getByPrimaryKey(novelFavourite);

    }

    /**
     * Follow a novel
     *
     * @param novelId
     * @param userId
     * @return null if success, error message if failed
     * @throws SQLException
     */
    public static String followNovel(Integer novelId, Integer userId) throws SQLException {
        if (novelId == null || userId == null) {
            throw new IllegalArgumentException("novelId or userId is null");
        }

        NovelFavourite novelFavourite = getFollowedNovel(novelId, userId);

        if (novelFavourite != null) {
            return "Đã theo dõi truyện này";
        }

        novelFavourite = new NovelFavourite();
        novelFavourite.setNovelId(novelId);
        novelFavourite.setUserId(userId);
        NovelFavouriteRepository.getInstance().insert(novelFavourite);
        return null;
    }

    /**
     * Unfollow a novel
     *
     * @param novelId
     * @param userId
     * @return null if success, error message if failed
     * @throws SQLException
     */
    public static String unfollowNovel(Integer novelId, Integer userId) throws SQLException {
        if (novelId == null || userId == null) {
            throw new IllegalArgumentException("novelId or userId is null");
        }

        NovelFavourite novelFavourite = getFollowedNovel(novelId, userId);

        if (novelFavourite == null) {
            return "Chưa theo dõi truyện này";
        }

        NovelFavouriteRepository.getInstance().delete(novelFavourite);
        return null;
    }
}
