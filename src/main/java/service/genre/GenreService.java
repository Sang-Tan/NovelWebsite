package service.genre;

import model.Genre;
import repository.GenreRepository;

import java.sql.SQLException;
import java.util.List;

public class GenreService implements IGenreService{
    @Override
    public List<Genre> getAll() throws SQLException {
        return GenreRepository.getInstance().getAll();
    }

    @Override
    public Genre insert(Genre genre) throws SQLException {
        return GenreRepository.getInstance().insert(genre);
    }

    @Override
    public void update(Genre genre) throws SQLException {
        GenreRepository.getInstance().update(genre);
    }

    @Override
    public void delete(Genre genre) throws SQLException {
        GenreRepository.getInstance().delete(genre);
    }

    @Override
    public Genre findById(int id) throws SQLException {
        return GenreRepository.getInstance().findById(id);
    }
}
