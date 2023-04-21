package repository;

import core.database.BaseRepository;
import model.intermediate.Restriction;

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

    @Override
    public Restriction insert(Restriction restriction) throws SQLException {
        return insert(restriction, false);
    }

    public Restriction insert(Restriction restriction, boolean overrideExpired) throws SQLException {
        Restriction existing = getByPrimaryKey(restriction);
        if (existing == null) {
            return super.insert(restriction);
        } else {
            long timeToExpire = existing.getDueTime().getTime() - System.currentTimeMillis();
            if (timeToExpire > 0) {
                throw new SQLException("Restriction already exists and not expired yet");
            } else {
                if (overrideExpired) {
                    delete(existing);
                    return super.insert(restriction);
                } else {
                    throw new SQLException("overrideExpired must be true to override expired restriction");
                }
            }
        }
    }
}
