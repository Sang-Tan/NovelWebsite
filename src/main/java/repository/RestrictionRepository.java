package repository;

import core.database.BaseRepository;
import core.database.SqlRecord;
import model.intermediate.Restriction;

import java.sql.ResultSet;
import java.sql.SQLException;

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
