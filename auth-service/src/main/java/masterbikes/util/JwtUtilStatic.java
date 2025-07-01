package masterbikes.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class JwtUtilStatic {
    private static final Key key = Keys.hmacShaKeyFor("replace-this-key-with-your-real-secret-key-1234567890".getBytes());

    public static Object getClaim(String token, String claim) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get(claim);
    }
}
