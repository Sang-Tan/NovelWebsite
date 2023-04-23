package core.pagination;

public class Paginator {
    private static final int DEFAULT_PAGE_SIZE = 24;
    private int pageSize = DEFAULT_PAGE_SIZE;

    private int maxPage = 1;
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

        this.pageSize = pageSize;
        maxPage = (int) Math.ceil((double) numOfObjects / pageSize);
        if (page < 1) page = 1;
        if (page > maxPage) page = maxPage;
        currentPage = page;
    }
    public int getPageSize() {
        return pageSize;
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

    public int getFirstPage() {
        return 1;
    }
}