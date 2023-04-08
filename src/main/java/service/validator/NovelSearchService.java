package service.validator;

import model.Novel;
import repository.NovelRepository;
import service.Pagination.Paginator;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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
    public NovelSearchService( String sql, List<Object> params, Paginator paginator) {
        this.sql = sql;
        this.params = params;
        this.paginator = paginator;
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
    private String generateNovelNameCondition(String name, List<Object> params) {
        String sql = "";
        if (name == null || name.isEmpty()) {
            return sql;
        }
        sql += "name LIKE ?";
        params.add("%" + name + "%");
        return sql;
    }

    /**
     * @param authorName author name
     * @param params will be added by value
     * @return SQL condition string
     */
    private String generateAuthorCondition(String authorName, List<Object> params) {
        String sql = "";
        if (authorName == null || authorName.isEmpty()) {
            return sql;
        }
        sql += "owner IN (SELECT id FROM users WHERE display_name LIKE ?)";
        params.add("%" + authorName + "%");
        return sql;
    }

    private String generateStatusCondition(String status, List<Object> params) {
        String sql = "";
        List<String> validStatus = List.of(Novel.STATUS_ON_GOING, Novel.STATUS_FINISHED, Novel.STATUS_PAUSED);
        if (status == null || status.isEmpty() || status.equals("all") || !validStatus.contains(status)) {
            return sql;
        }
        sql += "status = ?";
        params.add(status);
        return sql;
    }
    public static HashSet<Integer> extractGenresIDs(String genresIDString)  {

        HashSet<Integer> genresIDList = null;
        String regex = "^[0-9,]+$";
        if (!(genresIDString == null ) && !genresIDString.isEmpty() && genresIDString.matches(regex) ) {
            String[] arrGenresIDString = genresIDString.split(",");
            // convert string array to hashset
            genresIDList = Arrays.stream(arrGenresIDString).map(Integer::parseInt).collect(Collectors.toCollection(HashSet::new));
        }
        return genresIDList;
    }
    private String generateGenresIDCondition(String genresIdString, List<Object> params) {
        HashSet<Integer> genresId = extractGenresIDs(genresIdString);
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
    private String generateSortCondition(String sortAttribute) {
        String sql = "";
        if (sortAttribute == null || sortAttribute.isEmpty() || sortAttribute.equals("name")){
            sql += "ORDER BY " + DEFAULT_SORT_ATTRIBUTE + " ASC";
        }
        return sql;
    }


    /**
     *
     * @param name name of novel
     * @param authorName name of author
     * @param status status of novel
     * @param genresIdString genres id of novel in string
     * @param sortAttribute sort attribute (name novels ...
     * @param page page number
     * @return
     * @throws SQLException
     */
    public List<Novel> searchNovels(String name, String authorName, String status, String genresIdString, String sortAttribute, int page) throws SQLException {
        List<Object> params = new ArrayList<>();
        List<String> conditionsSQL = new ArrayList<>();

        conditionsSQL.add(generateNovelNameCondition(name, params));
        conditionsSQL.add(generateAuthorCondition(authorName, params));
        conditionsSQL.add(generateStatusCondition(status, params));
        conditionsSQL.add(generateGenresIDCondition(genresIdString, params));
        conditionsSQL.removeIf(String::isEmpty);

        sql = conditionsSQL.size() > 0 ? String.join(" AND ", conditionsSQL): "1=1";
        paginator = new Paginator(NovelRepository.getInstance().countNovels(sql, params), page);
        // order
        sql += " " + generateSortCondition(sortAttribute);
        sql += " " + paginator.generatePaginationCondition(params);
        return NovelRepository.getInstance().getByConditionString(sql, params);
    }
}
