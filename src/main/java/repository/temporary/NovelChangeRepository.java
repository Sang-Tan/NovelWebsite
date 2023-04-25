package repository.temporary;

import core.database.BaseRepository;
import model.temporary.NovelChange;

import java.sql.SQLException;

public class NovelChangeRepository extends BaseRepository<NovelChange> {
    private static NovelChangeRepository instance;

    public static NovelChangeRepository getInstance() {
        if (instance == null) {
            instance = new NovelChangeRepository();
        }
        return instance;
    }

    @Override
    protected NovelChange createEmpty() {
        return new NovelChange();
    }

    public NovelChange getByNovelId(int novelId) throws SQLException {
        NovelChange novelChange = new NovelChange();
        novelChange.setNovelId(novelId);
        return getByPrimaryKey(novelChange);
    }
}
