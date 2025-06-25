package masterbikes.catalogo_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import masterbikes.catalogo_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null; // email
        String jwt = null;
        Claims claims = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.getSubject(jwt);
                claims = jwtUtil.getAllClaims(jwt);
            } catch (IllegalArgumentException e) {
                logger.warn("Unable to get JWT Token: " + e.getMessage());
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token has expired: " + e.getMessage());
            } catch (SignatureException e) {
                logger.warn("JWT Token signature does not match: " + e.getMessage());
            } catch (MalformedJwtException e) {
                logger.warn("JWT Token malformed: " + e.getMessage());
            } catch (UnsupportedJwtException e) {
                logger.warn("JWT Token is unsupported: " + e.getMessage());
            } catch (Exception e) {
                logger.warn("JWT Token validation error: " + e.getMessage());
            }
        }

        if (username != null && claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String rolClaim = claims.get("rol", String.class);
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (rolClaim != null && !rolClaim.isEmpty()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + rolClaim));
            }

            UserDetails userDetails = new User(username, "", authorities); // Contraseña vacía, no se usa aquí

            // No es necesario validar userDetails != null porque si username y claims no son null,
            // userDetails se creará. La validación importante es que el token sea correcto (hecho por JwtUtil)
            // y que el contexto de seguridad no tenga ya una autenticación.

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        chain.doFilter(request, response);
    }
}
