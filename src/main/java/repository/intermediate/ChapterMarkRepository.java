package repository.intermediate;

import core.database.BaseRepository;
import model.intermediate.ChapterMark;

public class ChapterMarkRepository extends BaseRepository<ChapterMark> {
    private static ChapterMarkRepository instance;

    public static ChapterMarkRepository getInstance() {
        if (instance == null) {
            instance = new ChapterMarkRepository();
        }
        return instance;
    }

    @Override
    protected ChapterMark createEmpty() {
        return new ChapterMark();
    }


}
