package model.temporary;

import core.DatabaseObject;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "chapter_changes")
public class ChapterChange implements DatabaseObject, INovelContentChange {
    @Id
    @Column(name = "chapter_id", nullable = false)
    private int chapterId;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChapterChange that = (ChapterChange) o;
        return chapterId == that.chapterId && Objects.equals(name, that.name) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chapterId, name, content);
    }
}
