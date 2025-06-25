package masterbikes.venta_service.util;

// Componente utilitario para generación y validación de JWT.
// Permite emitir tokens con claims personalizados y extraer información de los mismos.
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    private final Key key;
    private final long EXPIRATION = 1000 * 60 * 60 * 4; // 4 horas, aunque para validación no se usa directamente.

    /**
     * Inyecta la clave secreta desde application.properties (en base64).
     */
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }

    /**
     * Genera un JWT para el usuario autenticado.
     * @param subject email del usuario
     * @param claims mapa de claims personalizados (rol, sucursal, etc.)
     * @return token JWT firmado
     */
    public String generateToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    /**
     * Extrae el subject (email) de un JWT. Valida firma y expiración.
     */
    public String getSubject(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Extrae todos los claims de un JWT. Valida firma y expiración.
     */
    public io.jsonwebtoken.Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
    }


    /**
     * Extrae un claim personalizado de un JWT.
     * @param token JWT
     * @param claim nombre del claim
     * @return valor del claim
     */
    public Object getClaim(String token, String claim) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get(claim);
    }
}
