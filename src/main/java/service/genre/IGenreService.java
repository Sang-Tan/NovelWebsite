package service.genre;

import model.Genre;

import java.sql.SQLException;
import java.util.List;

public interface IGenreService{
    List<Genre> getAll() throws SQLException;
    Genre insert(Genre genre) throws SQLException;
    void update(Genre genre) throws SQLException;
    void delete(Genre genre) throws SQLException;
    Genre findById(int id) throws SQLException;
}
