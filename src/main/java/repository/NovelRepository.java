package repository;

import core.database.BaseRepository;
import core.database.SqlRecord;
import io.github.cdimascio.dotenv.Dotenv;
import model.Novel;
import model.Token;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NovelRepository extends BaseRepository<Novel> {
    private static TokenRepository instance;
    public static TokenRepository getInstance() {
        if (instance == null) {
            instance = new TokenRepository();
        }
        return instance;
    }
    protected NovelRepository() {
        super("tokens", new String[]{"id"});
        Dotenv dotenv = Dotenv.load();
    }

    @Override
    protected Novel createDefault() {
        Novel novel = new Novel();
        novel.setStatus("on going");
        novel.setSummary(Novel.DEFAULT_SUMMARY);
        novel.setImage(Novel.DEFAULT_AVATAR);
        novel.setPending(true);
        return novel;
    }

    @Override
    protected Novel mapRow(ResultSet resultSet) throws SQLException {
        Novel novel = new Novel();
        novel.setId(resultSet.getInt("id"));
        novel.setOwner(resultSet.getInt("owner"));
        novel.setSummary(resultSet.getString("summary"));
        novel.setName(resultSet.getString("name"));
        novel.setImage(resultSet.getString("image"));
        novel.setStatus(resultSet.getString("status"));
        novel.setPending(resultSet.getBoolean("pending"));
        return novel;
    }

    @Override
    protected SqlRecord mapObject(Novel novel) {
        SqlRecord record = new SqlRecord();
        record.put("id", novel.getId());
        record.put("owner", novel.getOwnerID());
        record.put("summary", novel.getName());
        record.put("image", novel.getImage());
        record.put("status", novel.getStatus());
        record.put("is_pending", novel.isPending());
        return record;
    }

    @Override
    protected SqlRecord getPrimaryKeyMap(Novel object) {
        SqlRecord record = new SqlRecord();
        record.put("id", object.getId());
        return record;
    }
}
