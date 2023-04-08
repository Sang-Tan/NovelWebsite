package service.Pagination;

import java.util.LinkedList;
import java.util.List;

public class Paginator {
    private static final int DEFAULT_PAGE_SIZE = 2;
    private static final int DEFAULT_MAX_ACTIVE_PAGES = 5;
    private int maxPage = 0;
    private int currentPage = 1;


    public Paginator() {

    }

    /**
     * @param numOfObjects number of all avaible object that can be displayed
     */
    public Paginator(long numOfObjects, int page) {
        this(numOfObjects, page, DEFAULT_PAGE_SIZE);
    }

    public Paginator(long numOfObjects, int page, int pageSize) {

        maxPage = (int) Math.ceil((double) numOfObjects / pageSize);
        if (page < 1) page = 1;
        if (page > maxPage) page = maxPage;
        currentPage = page;
    }
    public String generatePaginationCondition(List<Object> params) {
        String sql = "LIMIT ? OFFSET ?";
        params.add(DEFAULT_PAGE_SIZE);
        params.add((currentPage - 1) * DEFAULT_PAGE_SIZE);
        return sql;
    }

    public List<PageItem> getActivePageItems(String currentUrl) {
        List<PageItem> activePages = new LinkedList<PageItem>();

        // calculate start and end page
        int startPage = currentPage - DEFAULT_MAX_ACTIVE_PAGES / 2;
        int endPage = currentPage + DEFAULT_MAX_ACTIVE_PAGES / 2;
        if (startPage < 1) {
            endPage += 1 - startPage;
            startPage = 1;
        }
        if (endPage > maxPage) {
            startPage -= endPage - maxPage;
            endPage = maxPage;
        }
        if (startPage < 1) startPage = 1;


        if(startPage >= endPage) return null;
        activePages.add(new PageItem(currentUrl + "&page=1", "Đầu", false, currentPage == 1));
        for (int i = startPage; i <= endPage; i++)
            activePages.add(new PageItem(currentUrl + "&page=" + i, Integer.toString(i), currentPage == i, false));
        activePages.add(new PageItem(currentUrl + "&page=" + maxPage, "Cuối", false ,currentPage == maxPage));
        return activePages;

    }

    public int getLastPage() {
        return maxPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    //    public int navigateToPage(int page) {
//        if(page < 1) page = 1;
//        if(page > maxPage) page = maxPage;
//        currentPage = page;
//        return page;
//
    public int getFirstPage() {
        return 1;
    }
}
