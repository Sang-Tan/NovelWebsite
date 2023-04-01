package model;

import model.PK.NovelGenrePK;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "novel_genre", schema = "novelweb", catalog = "")
@IdClass(NovelGenrePK.class)
public class NovelGenre {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "genre_id", nullable = false)
    private int genreId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "novel_id", nullable = false)
    private int novelId;
    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id", nullable = false)
    private Genre relativeGenre;
    @ManyToOne
    @JoinColumn(name = "novel_id", referencedColumnName = "id", nullable = false)
    private Novel relativeNovel;

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
        NovelGenre that = (NovelGenre) o;
        return genreId == that.genreId && novelId == that.novelId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, novelId);
    }

    public Genre getRelativeGenre() {
        return relativeGenre;
    }

    public void setRelativeGenre(Genre relativeGenre) {
        this.relativeGenre = relativeGenre;
    }

    public Novel getRelativeNovel() {
        return relativeNovel;
    }

    public void setRelativeNovel(Novel relativeNovel) {
        this.relativeNovel = relativeNovel;
    }
}
