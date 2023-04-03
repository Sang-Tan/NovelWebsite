package repository;

import core.Pair;
import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import io.github.cdimascio.dotenv.Dotenv;
import model.Novel;
import model.User;
import model.intermediate.NovelGenre;
import repository.intermediate.NovelGenreRepository;

import javax.servlet.http.Part;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

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
    public Novel createDefault() {
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

    public Novel createNovel(String novelName, String summary,
                             String status, String imageURI, int ownerID) {
        Novel novel = new Novel();
        novel.setName(novelName);
        novel.setSummary(summary);
        novel.setStatus(status);
        novel.setImage(imageURI);
        novel.setOwnerID(ownerID);
        return novel;
    }

    public void addGenresToNovel(int novelID, Integer[] genres) throws SQLException {
        ArrayList<NovelGenre> novelGenres = new ArrayList<>();
        for (Integer genre : genres) {
            NovelGenre novelGenre = new NovelGenre();
            novelGenre.setNovelId(novelID);
            novelGenre.setGenreId(genre);
            novelGenres.add(novelGenre);
        }
        NovelGenreRepository.getInstance().insertBatch(novelGenres);
    }

    public Collection<Novel> getNovelsByOwnerID(int ownerID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE owner = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{ownerID});
        Collection<Novel> novels = new LinkedList<>();
        while (result.next()) {
            novels.add(mapObject(result));
        }
        return novels;
    }
}
