package repository;

import core.database.BaseRepository;
import model.Token;
import core.SHA256Hashing;
import core.database.MySQLdb;
import core.database.SqlRecord;
import model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class TokenRepository extends BaseRepository<Token> {
    private int DEFAULT_TOKEN_EXPIRE_TIME = 60 * 60 * 24 * 7; // 7 days
    private int TOKEN_LENGTH = 64;
    private static TokenRepository instance;

    protected TokenRepository() {
        super("tokens", new String[]{"id"});
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
        return token;
    }

    @Override
    protected Token mapRow(ResultSet resultSet) throws SQLException {
        Token token = new Token();
        token.setId(resultSet.getInt("id"));
        token.setUserId(resultSet.getInt("user_id"));
        token.setHashToken(resultSet.getString("token_hash"));
        return token;
    }

    @Override
    protected SqlRecord mapObject(Token token) {
        SqlRecord record = new SqlRecord();
        record.setValue("id", token.getId());
        record.setValue("user_id", token.getUserId());
        record.setValue("token_hash", token.getTokenHash());
        return record;
    }

    public Token createNewToken(int userID, String plainToken) {
        Token token = createDefault();
        token.setUserId(userID);
        token.setHashToken(SHA256Hashing.computeHash(plainToken));
        return token;
    }

    public Token getByPlainToken(String plainToken) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE username = ?", getTableName());
        ResultSet result = MySQLdb.getInstance().query(sql, new Object[]{SHA256Hashing.computeHash(plainToken)});
        if (result.next()) {
            return mapRow(result);
        }
        return null;
    }

    public void setTokenCookie(HttpServletResponse response, String plainToken) {

        Cookie cookie = new Cookie("token",plainToken);
        cookie.setMaxAge(DEFAULT_TOKEN_EXPIRE_TIME);
        response.addCookie(cookie);
    }

    public String generateTokenString() {
        // Generate secure random access token
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[TOKEN_LENGTH*3/4];
        random.nextBytes(bytes);
        String token = Base64.getEncoder().encodeToString(bytes);

        return token;
    }

    public void deleteToken(String plainToken) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE token_hash = ?", getTableName());
        MySQLdb.getInstance().execute(sql, new Object[]{SHA256Hashing.computeHash(plainToken)});
    }

}
