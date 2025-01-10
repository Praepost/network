package org.oril.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expiration;

    private Key key;

    @PostConstruct
    public void initKey() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String getId(String token) {
        String id = getAllClaimsFromToken(token).get("id", String.class);

        return id;
    }

//    public Claims getClaims(String token) {
//        return (Claims) Jwts.parserBuilder().setSigningKey(key).build().parse(token).getBody();
//    }
//
//    private Claims getAllClaimsFromToken(String token) {
//        Claims extractAllClaims = Jwts
//                .parserBuilder()
//                .setSigningKey(secret)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();

    private Claims getAllClaimsFromToken(String token) {
        Claims extractAllClaims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();

//        List<String> claimRoles = extractAllClaims.get("roles", List.class);

        return extractAllClaims;
    }

    public Date getExpirationDate(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    public String generate(String userId, String role, String tokenType) {
        Map<String, String> claims = Map.of("id", userId, "role", role);
        long expMillis = "ACCESS".equalsIgnoreCase(tokenType)
                ? Long.parseLong(expiration) * 1000
                : Long.parseLong(expiration) * 1000 * 5;

        final Date now = new Date();
        final Date exp = new Date(now.getTime() + expMillis);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(claims.get("id"))
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }

    private boolean isExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }
}
