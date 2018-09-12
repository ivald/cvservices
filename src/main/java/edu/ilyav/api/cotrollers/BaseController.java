package edu.ilyav.api.cotrollers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ivald on 2018-09-11.
 */
public class BaseController {

    protected Claims getUserNameFromToken(HttpServletRequest req) throws ServletException {

        final String authHeader = req.getHeader("authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header");
        }

        final String token = authHeader.substring(7);

        return Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
    }

}
