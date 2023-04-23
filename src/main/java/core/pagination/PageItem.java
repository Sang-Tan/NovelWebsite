package core.pagination;

public class PageItem {
    public String url;
    public String text;
    public boolean active;
    public boolean isDisabled;
    public PageItem(String url, String text, boolean active, boolean isDisabled) {
        this.url = url;
        this.text = text;
        this.active = active;
        this.isDisabled = isDisabled;

    }
    public String getUrl() {
        return url;
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
    public void setUrl(String url) {
        this.url = url;
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
