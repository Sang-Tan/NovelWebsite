package model;

import core.DatabaseObject;
import repository.NovelRepository;
import repository.UserRepository;

import javax.persistence.*;
import java.sql.SQLException;

@Entity
@Table(name = "volumes", uniqueConstraints = {@UniqueConstraint(columnNames = {"novel_id", "order_index"})})
public class Volume implements DatabaseObject {
    public static final String DEFAULT_IMAGE = "/images/default-novel-avatar.jpg";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @ManyToOne
    @JoinColumn(name = "novel_id")
    private Novel novel;
    @Column(name = "novel_id")
    private Integer novelId;
    @Column(name = "name")
    private String name;
    @Column(name = "image")
    private String image;
    @Column(name = "order_index")
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
