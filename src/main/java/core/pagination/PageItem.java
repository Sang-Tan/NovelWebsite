package core.pagination;

public class PageItem {
    public String text;
    public int page;
    public boolean active;
    public boolean isDisabled;
    public PageItem(int page, String text, boolean active, boolean isDisabled) {
        this.text = text;
        this.active = active;
        this.isDisabled = isDisabled;
        this.page = page;

    }

    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public boolean isActive() {
        return active;
    }
    public boolean isDisabled() {
        return isDisabled;
    }
    public String getText() {
        return text;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

}
