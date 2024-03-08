package service.validator;

import core.SHA256Hashing;
import core.config.ApplicationPropertiesReader;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Base64;

public class TokenService {

    private static final int TOKEN_EXPIRE_TIME =
            Integer.parseInt(ApplicationPropertiesReader.getInstance().getProperty("token.life-time"));
    private static final int TOKEN_LENGTH =
            Integer.parseInt(ApplicationPropertiesReader.getInstance().getProperty("token.length"));

    public static String hashToken(String token) {
        return SHA256Hashing.computeHash(token);
    }

    public static void setTokenCookie(HttpServletResponse response, String plainToken) {
        Cookie cookie = new Cookie("token", plainToken);
        cookie.setMaxAge(TOKEN_EXPIRE_TIME);
        response.addCookie(cookie);
    }

    public static Cookie getTokenCookie(Cookie[] cookies) {
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                return cookie;
            }
        }
        return null;
    }

    public static String generateTokenString() {
        // Generate secure random access token
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[TOKEN_LENGTH * 3 / 4];
        random.nextBytes(bytes);
        String token = Base64.getEncoder().encodeToString(bytes);

        return token;
    }

    public static Timestamp getExpiredTime() {
        return new Timestamp(System.currentTimeMillis() + TOKEN_EXPIRE_TIME * 1000L);
    }

}
