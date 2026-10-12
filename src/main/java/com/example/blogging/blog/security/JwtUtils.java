package com.example.blogging.blog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private static final String JWT_SECRET_KEY =
            "ThisIsMyJwtSecretKeyThisIsMyJwtSecretKeyThisIsMyJwtSecretKeyThisIsMyJwtSecretKey";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String getUserByUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(
            String token,
            Function<Claims, T> claimResolver) {

        Claims claims = getAllClaimsFromToken(token);

        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();

        return generateToken(userDetails.getUsername(), claims);
    }

    private String generateToken(
            String username,
            Map<String, Object> claims) {

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + JWT_TOKEN_VALIDITY * 1000
                        )
                )
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(
            String token,
            UserDetails userDetails) {

        String username = getUserByUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {

        Date expiration = getExpirationDateFromToken(token);

        return expiration.before(new Date());
    }
}