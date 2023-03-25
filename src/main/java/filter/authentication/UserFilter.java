package filter.authentication;

import com.mysql.cj.log.Log;
import model.User;
import service.validator.UserValidator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebFilter(filterName = "UserAuthorize")
public class UserFilter extends HttpFilter {
    private final Logger LOGGER = Logger.getLogger(UserFilter.class.getName());

    @Override
    public void init() throws ServletException {
        LOGGER.setLevel(Level.ALL);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            User user = UserValidator.getUserInRequest(request);
            if (user != null) {
                request.setAttribute("user", user);
            }
        } catch (SQLException e) {
            LOGGER.warning("SQL exception: " + e.getMessage());
            throw new RuntimeException(e);
        }

        chain.doFilter(request, response);
    }
}
