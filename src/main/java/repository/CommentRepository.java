package repository;

import core.database.BaseRepository;
import core.database.SqlRecord;
import model.Comment;

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


    @Override
    protected Comment createEmpty() {
        return new Comment();
    }

    @Override
    protected Comment createDefault() {
        return null;
    }

}

