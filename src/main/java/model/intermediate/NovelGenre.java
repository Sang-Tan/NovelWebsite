package model.intermediate;

import core.DatabaseObject;
import model.Genre;
import model.Novel;
import repository.GenreRepository;
import repository.NovelRepository;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.List;

@Entity
@Table(name = "novel_genre", schema = "novelweb")
public class NovelGenre implements DatabaseObject {

    @Id
    @Column(name = "genre_id", nullable = false)
    private int genreId;

    @Id
    @Column(name = "novel_id", nullable = false)
    private int novelId;
    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id", nullable = false)
    private Genre relatedGenre;
    @ManyToOne
    @JoinColumn(name = "novel_id", referencedColumnName = "id", nullable = false)
    private Novel relatedNovel;

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

    public Genre getRelatedGenre() throws SQLException {
        if (relatedGenre == null || relatedGenre.getId() != genreId)
            relatedGenre = GenreRepository.getInstance().findById(genreId);
        return relatedGenre;
    }

    public void setRelatedGenre(Genre relatedGenre) {

        this.genreId = genreId;
        this.relatedGenre = relatedGenre;
    }

    public Novel getRelativeNovel() throws SQLException {
        if (relatedNovel == null || relatedNovel.getId() != novelId)
            relatedNovel = NovelRepository.getInstance().getById(novelId);
        return relatedNovel;

    }

    public void setRelativeNovel(Novel relativeNovel) {

        this.novelId = novelId;
        this.relatedNovel = relativeNovel;
    }
}
