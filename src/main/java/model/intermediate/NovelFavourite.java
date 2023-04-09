package model.intermediate;

import core.DatabaseObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "novel_favourite")
public class NovelFavourite implements DatabaseObject {
    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Id
    @Column(name = "novel_id", nullable = false)
    private int novelId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNovelId() {
        return novelId;
    }

    public void setNovelId(int novelId) {
        this.novelId = novelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NovelFavourite that = (NovelFavourite) o;
        return userId == that.userId && novelId == that.novelId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, novelId);
    }
}
