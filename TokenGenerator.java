package com.example.service;
/*
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;

public class TokenUtil {

    private static final long EXPIRATION_TIME = 3600000; // 1 hour
    private static final String JWT_SECRET = System.getenv("JWT_SECRET");

    public static String generateToken(String username, String email) {
        if (username == null || email == null || JWT_SECRET == null) {
            throw new IllegalArgumentException("Username, email, and JWT_SECRET must not be null");
        }

        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("email", email);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public static String verifyToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // Check if the token has expired
        if (claims.getExpiration().before(new Date())) {
            throw new RuntimeException("Token has expired");
        }

        return claims.getSubject();
    }

}
*/

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;


public class TokenGenerator {

    private static final SecretKey JWT_SECRET_KEY;
    private static final long EXPIRATION_TIME = 3600000; // 1 hour in milliseconds

    static {
        String encodedSecret = System.getenv("JWT_SECRET");
        if (encodedSecret == null || encodedSecret.isEmpty()) {
            throw new IllegalStateException("JWT_SECRET environment variable not set");
        }
        byte[] decodedSecret = Base64.getDecoder().decode(encodedSecret);
        JWT_SECRET_KEY = Keys.hmacShaKeyFor(decodedSecret);
    }

    public static String generateToken(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(JWT_SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}


