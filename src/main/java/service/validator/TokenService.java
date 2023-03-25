package service.validator;

import core.SHA256Hashing;
import io.github.cdimascio.dotenv.Dotenv;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.Base64;

public class TokenService {

    private static final int TOKEN_EXPIRE_TIME = Integer.parseInt(Dotenv.load().get("TOKEN_LIFE_TIME"));
    private static final int TOKEN_LENGTH = Integer.parseInt(Dotenv.load().get("TOKEN_LENGTH"));

    public static String hashToken(String token) {
        return SHA256Hashing.computeHash(token);
    }

    public static void setTokenCookie(HttpServletResponse response, String plainToken) {
        Cookie cookie = new Cookie("token", plainToken);
        cookie.setMaxAge(TOKEN_EXPIRE_TIME);
        response.addCookie(cookie);
    }

    public static Cookie getTokenCookie(Cookie[] cookies) {
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

}
