package repository;

import core.database.BaseRepository;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.Token;
import service.validator.TokenService;

import java.sql.SQLException;
import java.util.List;

public class TokenRepository extends BaseRepository<Token> {
    private static TokenRepository instance;


    @Override
    protected Token createEmpty() {
        return new Token();
    }

    public static TokenRepository getInstance() {
        if (instance == null) {
            instance = new TokenRepository();
        }
        return instance;
    }

    @Override
    public Token createDefault() {
        Token token = new Token();
        token.setExpiredTime(TokenService.getExpiredTime());
        return token;
    }


    public Token createNewToken(int userID, String hashedToken) {
        Token token = createEmpty();
        token.setUserId(userID);
        token.setTokenHash(hashedToken);
        token.setExpiredTime(TokenService.getExpiredTime());
        return token;
    }

    public Token getByHashedToken(String hashedToken) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE token_hash = ?", getTableName());
        List<SqlRecord> records = MySQLdb.getInstance().select(sql, List.of(hashedToken));
        for (SqlRecord record : records) {
            return mapObject(record);
        }
        return null;
    }


    public void deleteToken(String hashedToken) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE token_hash = ?", getTableName());
        MySQLdb.getInstance().execute(sql, List.of(hashedToken));
    }

}
