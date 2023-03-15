package model;

public class NovelGenre {
    private int genreId;
    private int novelId;

    public NovelGenre() {
    }

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

    public NovelGenre(int genreId, int novelId) {
        this.genreId = genreId;
        this.novelId = novelId;
    }
}
