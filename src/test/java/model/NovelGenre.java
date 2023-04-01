package model;

import javax.persistence.*;

@Entity
@Table(name = "novel_genre")
public class NovelGenre {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "genre_id", nullable = false)
    private int genreId;

    @Column(name = "novel_id", nullable = false)
    private int novelId;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id", nullable = false)
    private Genres genresByGenreId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "novel_id", referencedColumnName = "id", nullable = false)
    private Novels novelsByNovelId;

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

    public Genres getGenresByGenreId() {
        return genresByGenreId;
    }

    public void setGenresByGenreId(Genres genresByGenreId) {
        this.genresByGenreId = genresByGenreId;
    }

    public Novels getNovelsByNovelId() {
        return novelsByNovelId;
    }

    public void setNovelsByNovelId(Novels novelsByNovelId) {
        this.novelsByNovelId = novelsByNovelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NovelGenre that = (NovelGenre) o;
        return genreId == that.genreId && novelId == that.novelId;
    }
}
