package edu.ilyav.api.config;

import edu.ilyav.api.models.UserInfo;
import edu.ilyav.api.service.UserService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@PropertySource("classpath:application.properties")
public class JwtFilter extends GenericFilterBean {

    private Environment env;
    private UserService userService;

    private final String SECRET_KEY = "secret.key";

    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader("authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);

            chain.doFilter(req, res);
        } else {

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new ServletException("Missing or invalid Authorization header");
            }

            final String token = authHeader.substring(7);

            if (userService == null) {
                ServletContext servletContext = request.getServletContext();
                WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                userService = webApplicationContext.getBean(UserService.class);
                env = webApplicationContext.getBean(Environment.class);
            }

            try {
                final Claims claims = Jwts.parser().setSigningKey(env.getProperty(SECRET_KEY).getBytes("UTF-8")).parseClaimsJws(token).getBody();
                Optional<UserInfo> user = Optional.ofNullable(userService.findByUserName(claims.getSubject()));
                request.setAttribute("currentUser", user.get());
                request.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                throw new ServletException("Invalid token.");
            } catch (ResourceNotFoundException e) {
                throw new ServletException("Post authentication - Resource not found.");
            }

            chain.doFilter(req, res);
        }

    }
}
