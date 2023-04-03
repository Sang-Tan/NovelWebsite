package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.Comment;
import model.Novel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentRepository extends BaseRepository<Comment> {
    private static CommentRepository instance;

    public static CommentRepository getInstance() {
        if (instance == null) {
            instance = new CommentRepository();
        }
        return instance;
    }

    public Comment getById(Integer ID) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{ID});
        if (result.next()) {
            return mapObject(result);
        }
        return null;
    }


    @Override
    protected Comment createEmpty() {
        return new Comment();
    }

    @Override
    public Comment createDefault() {
        return new Comment();
    }

}

