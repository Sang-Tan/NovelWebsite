package repository.intermediate;

import core.DatabaseObject;
import core.database.BaseRepository;
import model.intermediate.NovelGenre;

public class NovelGenreRepository extends BaseRepository<NovelGenre> {

    private static NovelGenreRepository instance;

    public static NovelGenreRepository getInstance() {
        if (instance == null) {
            instance = new NovelGenreRepository();
        }
        return instance;
    }

    @Override
    protected NovelGenre createEmpty() {
        return new NovelGenre();
    }

    @Override
    protected NovelGenre createDefault() {
        return new NovelGenre();
    }
}
