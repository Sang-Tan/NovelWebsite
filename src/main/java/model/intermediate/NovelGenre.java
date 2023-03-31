package model.intermediate;

import model.Genre;
import model.Novel;

public class NovelGenre {
    private Genre genreId;
    private Novel novelId;

    public NovelGenre() {
    }

    public Genre getGenreId() {
        return genreId;
    }

    public void setGenreId(Genre genreId) {
        this.genreId = genreId;
    }

    public Novel getNovelId() {
        return novelId;
    }

    public void setNovelId(Novel novelId) {
        this.novelId = novelId;
    }

    public NovelGenre(Genre genreId, Novel novelId) {
        this.genreId = genreId;
        this.novelId = novelId;
    }
}
