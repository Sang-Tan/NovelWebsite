package model.temporary;

import core.DatabaseObject;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "novel_changes")
public class NovelChange implements DatabaseObject {

    @Id
    @Column(name = "novel_id", nullable = false)
    private int novelId;

    @Column(name = "summary", nullable = true, length = -1)
    private String summary;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "image", nullable = true, length = 255)
    private String image;

    public int getNovelId() {
        return novelId;
    }

    public void setNovelId(int novelId) {
        this.novelId = novelId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NovelChange that = (NovelChange) o;
        return novelId == that.novelId && Objects.equals(summary, that.summary) && Objects.equals(name, that.name) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(novelId, summary, name, image);
    }
}
