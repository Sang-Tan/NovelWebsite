package repository.temporary;

import core.database.BaseRepository;
import model.temporary.ChapterChange;

public class ChapterChangeRepository extends BaseRepository<ChapterChange> {
    @Override
    protected ChapterChange createEmpty() {
        return new ChapterChange();
    }
}
