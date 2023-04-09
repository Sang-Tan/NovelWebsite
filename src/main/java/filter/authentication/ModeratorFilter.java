package filter.authentication;

import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Objects;

@WebFilter(filterName = "ModeratorAuthorize")
public class ModeratorFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User) req.getAttribute("user");
        if (user == null || (!user.getRole().equals(User.ROLE_MODERATOR))) {
            res.setStatus(401);
            return;
        }
        chain.doFilter(req, res);
    }
}
