package com.kevin.emazon_cart.infraestructure.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

import static com.kevin.emazon_cart.infraestructure.security.util.ConstantsUtilSecurityClass.*;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    public static final String USERNAME_KEY = "username";
    public static final String ROLE_KEY = "role";
    public static final String EXPIRED_TOKEN_MESAGE = "Token expirado";
    public static final String INVALID_TOKEN_MESSAGE = "Token inválido";
    public static final String SECURITY_ROLE_ENTRANCE = "ROLE_";
    public static final String EMPTY_STRING = "";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(HEADER_AUTHORIZATION);

        if (header != null && header.startsWith(PREFIX_TOKEN)) {
            String token = header.replace(PREFIX_TOKEN, "");

            try {
                Claims claims = Jwts.parser()
                        .verifyWith(SECRET_KEY)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                String username = claims.get(USERNAME_KEY, String.class);
                String roleFromToken = claims.get(ROLE_KEY, String.class);

                if (validateToken(claims)) {
                    UserDetails userDetails = loadUserDetails(username, roleFromToken);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(EXPIRED_TOKEN_MESAGE);
                return;
            } catch (JwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(INVALID_TOKEN_MESSAGE);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean validateToken(Claims claims) {
        Date expirationDate = claims.getExpiration();
        return expirationDate.after(new Date());
    }

    private UserDetails loadUserDetails(String username, String roleFromToken) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(EMPTY_STRING)
                .roles(roleFromToken.replace(SECURITY_ROLE_ENTRANCE, EMPTY_STRING))
                .build();
    }
}