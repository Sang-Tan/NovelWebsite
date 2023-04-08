package repository;

import core.database.BaseRepository;
import model.intermediate.NovelFavourite;

public class NovelFavouriteRepository extends BaseRepository<NovelFavourite> {

    private static NovelFavouriteRepository instance;

    public static NovelFavouriteRepository getInstance() {
        if (instance == null) {
            instance = new NovelFavouriteRepository();
        }
        return instance;
    }

    @Override
    protected NovelFavourite createEmpty() {
        return new NovelFavourite();
    }

}
