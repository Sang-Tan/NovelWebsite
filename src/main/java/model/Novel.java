package model;

import core.DatabaseObject;
import repository.UserRepository;

import java.sql.SQLException;

public class Novel implements DatabaseObject {
    public static final String STATUS_ON_GOING = "Đang tiến hành";
    public static final String STATUS_FINISHED = "Đã hoàn thành";
    public static final String STATUS_PAUSED = "Tạm ngưng";
    public static final String DEFAULT_IMAGE = "/images/default-novel-avatar.jpg";
    public static final String DEFAULT_SUMMARY = "Không có tóm tắt";
    private Integer id;
    private Integer ownerID;
    private User owner;
    private String summary;
    private String name;
    private String image;
    private boolean pending;
    private String status;

    public Novel() {
    }

    public Novel(int id, User owner, String summary, String name,
                 String image, boolean pending, String status) {
        this.id = id;
        this.owner = owner;
        this.summary = summary;
        this.name = name;
        this.image = image;
        this.pending = pending;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwner() throws SQLException {
        owner = UserRepository.getInstance().getById(ownerID);
        return owner;
    }

    public void setOwner(User owner) {
        this.ownerID = owner.getId();
        this.owner = owner;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Integer ownerID) {
        this.ownerID = ownerID;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
