package model.PK;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class NovelGenrePK implements Serializable {
    @Column(name = "genre_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreId;
    @Column(name = "novel_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int novelId;

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
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
        NovelGenrePK that = (NovelGenrePK) o;
        return genreId == that.genreId && novelId == that.novelId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, novelId);
    }
}
