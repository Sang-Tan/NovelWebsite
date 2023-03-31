package repository;

import core.database.BaseRepository;
import io.github.cdimascio.dotenv.Dotenv;
import model.Token;
import core.SHA256Hashing;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.User;
import service.validator.TokenService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class TokenRepository extends BaseRepository<Token> {
    private static TokenRepository instance;

    protected TokenRepository() {
        super("tokens", new String[]{"id"});
        Dotenv dotenv = Dotenv.load();
    }

    public static TokenRepository getInstance() {
        if (instance == null) {
            instance = new TokenRepository();
        }
        return instance;
    }

    @Override
    protected Token createDefault() {
        Token token = new Token();
        token.setExpiredTime(TokenService.getExpiredTime());
        return token;
    }

    @Override
    protected Token mapRow(ResultSet resultSet) throws SQLException {
        Token token = new Token();
        token.setId(resultSet.getInt("id"));
        token.setUserId(resultSet.getInt("user_id"));
        token.setHashToken(resultSet.getString("token_hash"));
        token.setExpiredTime(resultSet.getTimestamp("expired_time"));
        return token;
    }

    @Override
    protected SqlRecord mapObject(Token token) {
        SqlRecord record = new SqlRecord();
        record.put("id", token.getId());
        record.put("user_id", token.getUserId());
        record.put("token_hash", token.getTokenHash());
        record.put("expired_time", token.getExpiredTime());
        return record;
    }

    @Override
    protected SqlRecord getPrimaryKeyMap(Token object) {
        SqlRecord record = new SqlRecord();
        record.put("id", object.getId());
        return record;
    }

    public Token createNewToken(int userID, String hashedToken) {
        Token token = createDefault();
        token.setUserId(userID);
        token.setHashToken(hashedToken);
        return token;
    }

    public Token getByHashedToken(String hashedToken) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE token_hash = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().select(sql, new Object[]{hashedToken});
        if (result.next()) {
            return mapRow(result);
        }
        return null;
    }


    public void deleteToken(String hashedToken) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE token_hash = ?", getTableName());
        MySQLdb.getInstance().execute(sql, new Object[]{hashedToken});
    }

}
