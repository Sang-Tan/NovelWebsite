package model;

import core.DatabaseObject;
import repository.NovelRepository;
import repository.UserRepository;

import java.sql.SQLException;

public class Volume implements DatabaseObject {
    public static final String DEFAULT_IMAGE = "/images/default-novel-avatar.jpg";
    private int id;
    private Novel novel;
    private Integer novelId;
    private String name;
    private String image;
    private int orderIndex;

    public Volume() {
    }

    public Volume(int id, Novel novel, String name, String image, int orderIndex) {
        this.id = id;
        this.novel = novel;
        this.name = name;
        this.image = image;
        this.orderIndex = orderIndex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Novel getNovel() throws SQLException {
        novel = NovelRepository.getInstance().getById(novelId);
        return novel;
    }

    public void setNovel(Novel novel) {
        this.novelId = novel.getId();
        this.novel = novel;
    }

    public Integer getNovelId() {
        return novelId;
    }

    public void setNovelId(Integer novelId) {
        this.novelId = novelId;
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

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }
}
