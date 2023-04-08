package repository;

import core.database.BaseRepository;
import model.intermediate.Restriction;

public class RestrictionRepository extends BaseRepository<Restriction> {
    private static RestrictionRepository instance;

    public static RestrictionRepository getInstance() {
        if (instance == null) {
            instance = new RestrictionRepository();
        }
        return instance;
    }

    @Override
    protected Restriction createEmpty() {
        return new Restriction();
    }
}