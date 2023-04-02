package model;

import core.DatabaseObject;
import repository.NovelRepository;
import repository.UserRepository;

import javax.persistence.*;
import java.sql.SQLException;


import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "volumes", uniqueConstraints = {@UniqueConstraint(columnNames = {"novel_id", "order_index"})})
public class Volume implements DatabaseObject {
    public static final String DEFAULT_IMAGE = "/images/default-novel-avatar.jpg";
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "novel_id", nullable = false)
    private int novelId;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "image", nullable = true, length = 255)
    private String image;
    @Column(name = "order_index", nullable = false)
    private int orderIndex;
    @OneToMany(mappedBy = "belongVolume")
    private List<Chapter> ownershipChapters;
    @ManyToOne
    @JoinColumn(name = "novel_id", referencedColumnName = "id", nullable = false)
    private Novel belongNovel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNovelId() {
        return novelId;
    }

    public void setNovelId(int novelId) {
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


    public List<Chapter> getChaptersById() {
        return ownershipChapters;
    }
    public void addOwnershipChapter(Chapter chapter) {
        ownershipChapters.add(chapter);
    }

    public void updateOwnershipChapter(Chapter chapter) {
        for (int i = 0; i < ownershipChapters.size(); i++) {
            if (ownershipChapters.get(i).getId() == chapter.getId()) {
                ownershipChapters.set(i, chapter);
                break;
            }
        }
    }
    public void deleteChapter(Chapter chapter) {
        deleteChapter(chapter.getId());
    }
    public void deleteChapter(int chapterId) {
        for (int i = 0; i < ownershipChapters.size(); i++) {
            if (ownershipChapters.get(i).getId() == chapterId) {
                ownershipChapters.remove(i);
                break;
            }
        }
    }

    public Novel getBelongNovel() {
        return belongNovel;
    }
}