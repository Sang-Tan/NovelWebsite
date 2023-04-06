package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import model.Novel;
import model.intermediate.NovelGenre;
import repository.intermediate.NovelGenreRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
    public List<Novel> getOverviewNovels(String condition, Object[] params) throws SQLException {
        String sql = String.format("SELECT * FROM %s %s", getTableName(), condition);
        ResultSet result = MySQLdb.getInstance().select(sql, params);
        return mapObjects(result);
    }
    public int countNovels(String condition, Object[] params) throws SQLException {
        String sql = String.format("SELECT COUNT(id) FROM %s %s", getTableName(), condition);
        ResultSet result = MySQLdb.getInstance().select(sql, params);
        if (result.next()) {
            return result.getInt(1);
        }
        return 0;
    }
//    public HashSet<Novel> search(String novelName, String authorName, String Status, Set<Integer> genresId, String sortAttribute) throws SQLException
//    {
//        String sql = " 1=1 ";
//        List<Object> params = new ArrayList<>();
//        if (novelName != null && !novelName.isEmpty()) {
//            sql += " AND name LIKE ?";
//            params.add("%" + novelName + "%");
//        }
//        if (authorName != null && !authorName.isEmpty()) {
//            sql += " AND owner IN (SELECT id FROM users WHERE display_name LIKE ?)";
//            params.add("%" + authorName + "%");
//        }
//        if (Status != null && !Status.isEmpty() && !Status.equals("all")) {
//            sql += " AND status = ?";
//            params.add(Status);
//        }
//        if (genresId != null && !genresId.isEmpty()) {
//            sql += " AND id IN (SELECT novel_id FROM novel_genre WHERE genre_id IN (";
//            String placeholders = String.join(",", Collections.nCopies(genresId.size(), "?"));
//            sql += placeholders;
//            sql += "))";
//            params.addAll(genresId);
//        }
//        if (sortAttribute != null && !sortAttribute.isEmpty() && !sortAttribute.equals("comment")
//        ) {
//            sql += " ORDER BY " + sortAttribute;
//        }
//        else if (sortAttribute != null && !sortAttribute.isEmpty() && sortAttribute.equals("comment")) {
//            sql += " ORDER BY (SELECT COUNT(*) FROM comments WHERE novel_id = novel.id) DESC";
//        }
////        else if (sortAttribute == null || sortAttribute.isEmpty()) && sortAttribute.equals("author name") {
////            sql += " ORDER BY (SELECT display_name FROM users WHERE id = novel.owner_id)";
////        }
//        return getByConditionString(sql, params.toArray());
//    }


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
