package model;

public class Notification {
    private int id;
    private User userId;
    private String content;
    private String link;

    public Notification() {
    }

    public Notification(int id, User userId, String content, String link) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
