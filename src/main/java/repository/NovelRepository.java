package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import io.github.cdimascio.dotenv.Dotenv;
import model.Novel;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NovelRepository extends BaseRepository<Novel> {
    private static NovelRepository instance;

    public static NovelRepository getInstance() {
        if (instance == null) {
            instance = new NovelRepository();
        }
        return instance;
    }

    @Override
    protected Novel createEmpty() {
        return new Novel();
    }

    @Override
    protected Novel createDefault() {
        Novel novel = new Novel();
        novel.setStatus(Novel.STATUS_ON_GOING);
        novel.setSummary(Novel.DEFAULT_SUMMARY);
        novel.setImage(Novel.DEFAULT_IMAGE);
        novel.setPending(true);
        return novel;
    }


    public Novel getById(Integer ID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{ID});
        if (result.next()) {
            return mapObject(result);
        }
        return null;
    }
}
