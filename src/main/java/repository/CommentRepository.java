package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.Comment;

import java.sql.SQLException;
import java.util.List;

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
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(ID));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }


    @Override
    protected Comment createEmpty() {
        return new Comment();
    }
}

