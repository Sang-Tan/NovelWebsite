package service;
import core.pagination.PageItem;
import core.pagination.Paginator;

import java.util.LinkedList;
import java.util.List;

public class PagingService {
    private static final int DEFAULT_MAX_ACTIVE_PAGES = 5;
    private static int maxActivePages = DEFAULT_MAX_ACTIVE_PAGES;
    public static String generatePaginationCondition(List<Object> params, Paginator paginator) {
        String sql = "LIMIT ? OFFSET ?";
        params.add(paginator.getPageSize());
        params.add((paginator.getCurrentPage() - 1) * paginator.getPageSize());
        return sql;
    }
    public static List<PageItem> getActivePageItems(String currentUrl, Paginator paginator) {
        return getActivePageItems(currentUrl, DEFAULT_MAX_ACTIVE_PAGES, paginator);
    }

    public static List<PageItem> getActivePageItems(String currentUrl, int maxActivePages, Paginator paginator) {
        maxActivePages = maxActivePages;
        List<PageItem> activePages = new LinkedList<PageItem>();

        // calculate start and end page
        int startPage = paginator.getCurrentPage() - maxActivePages / 2;
        int endPage = paginator.getCurrentPage() + maxActivePages / 2;
        if (startPage < 1) {
            endPage += 1 - startPage;
            startPage = 1;
        }
        if (endPage > paginator.getMaxPage()) {
            startPage -= endPage - paginator.getMaxPage();
            endPage = paginator.getMaxPage();
        }
        if (startPage < 1) startPage = 1;


        if(startPage >= endPage) return null;
        activePages.add(new PageItem(currentUrl + "&page=1", "Đầu", false, paginator.getCurrentPage() == 1));
        for (int i = startPage; i <= endPage; i++)
            activePages.add(new PageItem(currentUrl + "&page=" + i, Integer.toString(i), paginator.getCurrentPage() == i, false));
        activePages.add(new PageItem(currentUrl + "&page=" + paginator.getMaxPage(), "Cuối", false ,paginator.getCurrentPage() == paginator.getMaxPage()));
        return activePages;

    }
}