package model;

import javax.persistence.*;

@Entity
@Table(name = "notifications", schema = "novelweb")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "link", nullable = false)
    private String link;

    public Notification() {
    }

    public Notification(int id, int userId, String content, String link) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
