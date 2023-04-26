package repository.temporary;

import core.database.BaseRepository;
import model.temporary.ChapterChange;

import java.sql.SQLException;

public class ChapterChangeRepository extends BaseRepository<ChapterChange> {
    private static ChapterChangeRepository instance;

    public static ChapterChangeRepository getInstance() {
        if (instance == null) {
            synchronized (ChapterChangeRepository.class) {
                if (instance == null) {
                    instance = new ChapterChangeRepository();
                }
            }
        }
        return instance;
    }

    @Override
    protected ChapterChange createEmpty() {
        return new ChapterChange();
    }

    public ChapterChange getByChapterId(int chapterId) throws SQLException {
        ChapterChange chapterChange = new ChapterChange();
        chapterChange.setChapterId(chapterId);
        return getByPrimaryKey(chapterChange);
    }
}
