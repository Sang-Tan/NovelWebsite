package service.validator;

import model.Novel;
import repository.NovelRepository;
import core.pagination.Paginator;
import service.PagingService;
import service.SearchNovelService;

import java.sql.SQLException;
import java.util.*;

public class NovelSearchService {
    private static final String DEFAULT_SORT_ATTRIBUTE = "name";
    private static NovelSearchService instance;
    private String sql;
    private List<Object> params;
    private Paginator paginator;


    public static NovelSearchService getInstance() {
        if (instance == null) {
            instance = new NovelSearchService();
        }
        return instance;
    }

    private NovelSearchService() {
    }

    public NovelSearchService(String sql, List<Object> params, Paginator paginator) {
        this.sql = sql;
        this.params = params;
        this.paginator = paginator;
    }

    public List<Novel> getLatestUpdateNovels(int pageSize) throws SQLException {
        List searchedNovel = searchApprovedNovels("", "", "", "", "update_time", "DESC", 1, pageSize);
        return removeNoChapterNovels(searchedNovel);
    }

    public String getSql() {
        return sql;
    }

    public List<Object> getParams() {
        return params;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    /**
     * @param name   novel name
     * @param params will be added by value
     * @return SQL condition string
     */
    private static String generateNovelNameCondition(String name, List<Object> params) {
        String sql = "";
        if (name == null || name.isEmpty()) {
            return sql;
        }
        sql += "MATCH(name) AGAINST(? IN NATURAL LANGUAGE MODE)";
        params.add(name);
        return sql;
    }

    /**
     * @param authorName author name
     * @param params     will be added by value
     * @return SQL condition string
     */
    private static String generateAuthorCondition(String authorName, List<Object> params) {
        String sql = "";
        if (authorName == null || authorName.isEmpty()) {
            return sql;
        }
        sql += "owner IN (SELECT id FROM users WHERE MATCH(display_name) AGAINST(? IN NATURAL LANGUAGE MODE))";
        params.add(authorName);
        return sql;

    }

    private static String generateStatusCondition(String status, List<Object> params) {
        String sql = "";
        List<String> validStatus = List.of(Novel.STATUS_ON_GOING, Novel.STATUS_FINISHED, Novel.STATUS_PAUSED);
        if (status == null || status.isEmpty() || status.equals("all") || !validStatus.contains(status)) {
            return sql;
        }
        sql += "status = ?";
        params.add(status);
        return sql;
    }

    private static String generateGenresIDCondition(String genresIdString, List<Object> params) {
        HashSet<Integer> genresId = SearchNovelService.extractGenresId(genresIdString);
        String sql = "";
        if (genresIdString == null || genresIdString.isEmpty()) {
            return sql;
        }
        sql += "id IN (SELECT novel_id FROM novel_genre WHERE genre_id IN (";
        String placeholders = String.join(",", Collections.nCopies(genresId.size(), "?"));
        sql += placeholders;
        sql += "))";
        params.addAll(genresId);
        return sql;

    }

    /**
     * @param attribute in SortAttribute enum is valid
     * @param order     include ASC and DESC else will be set to ASC
     * @return SQL condition string
     */
    private static String generateSortCondition(String attribute, String order, List<Object> params) {
        String sql = "";
        if (attribute == null || attribute.isEmpty())
            attribute = DEFAULT_SORT_ATTRIBUTE;
        if (order == null || order.isEmpty())
            order = "ASC";

        if (!order.equals("DESC"))
            order = "ASC";

        switch (attribute) {
            case "name":
                sql += "ORDER BY name " + order;
                break;
            case "view":
                sql += "ORDER BY view_count " + order;
                break;
            case "update_time":
                sql += " ORDER BY (\n" +
                        "SELECT MAX(ct.created_at) FROM novels AS nv \n" +
                        "JOIN  volumes AS vl ON nv.id = vl.novel_id \n" +
                        "JOIN chapters AS ct ON vl.id = ct.volume_id\n" +
                        "WHERE novel1.id = nv.id\n" +
                        "AND ct.approval_status = 'approved'\n " +
                        "GROUP BY nv.id )" + order;
                break;
        }

        return sql;
    }


    /**
     * @param name           name of novel
     * @param authorName     name of author
     * @param status         status of novel
     * @param genresIdString genres id of novel in string
     * @param sortAttribute  sort attribute (name, update_time)
     * @param sortOrder      sort order (ASC, DESC)
     * @param page           page number
     * @return
     * @throws SQLException
     */
    public List<Novel> searchApprovedNovels(String name, String authorName, String status, String genresIdString, String sortAttribute, String sortOrder, int page, int pageSize) throws SQLException {
        List<Object> params = new ArrayList<>();
        List<String> conditionsSQL = new ArrayList<>();

        conditionsSQL.add(generateNovelNameCondition(name, params));
        conditionsSQL.add(generateAuthorCondition(authorName, params));
        conditionsSQL.add(generateStatusCondition(status, params));
        conditionsSQL.add(generateGenresIDCondition(genresIdString, params));
        conditionsSQL.removeIf(String::isEmpty);

        sql = conditionsSQL.size() > 0 ? String.join(" AND ", conditionsSQL) : "1=1";
        sql += " AND approval_status = 'approved'";

        // check if novel has at least 1 chapter
        sql += " AND (SELECT COUNT(id) as chapter_count FROM chapters " +
                "WHERE approval_status = 'approved' " +
                "AND volume_id IN " +
                "(SELECT id FROM volumes " +
                "WHERE novel_id = novel1.id)) > 1 ";
        paginator = new Paginator(NovelRepository.getInstance().countNovels(sql, params), page, pageSize);
        // order
        sql += " " + generateSortCondition(sortAttribute, sortOrder, params);
        sql += " " + PagingService.generatePaginationCondition(params, paginator);


        return removeNoChapterNovels(
                NovelRepository
                        .getInstance()
                        .getByConditionString(sql, params));
    }

    public List<Novel> removeNoChapterNovels(List<Novel> novels) throws SQLException {
        List<Novel> result = new ArrayList<>();
        for (Novel novel : novels) {
            boolean hasChapter = NovelRepository.getInstance().isNovelHasAnyApprovedChapter(novel.getId());
            if (hasChapter) {
                result.add(novel);
            }
        }
        return result;
    }
}