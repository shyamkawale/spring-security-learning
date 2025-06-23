package com.shyam_learning.jwt_auth.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY_ID = "Z3VhdmFuZ2FuYXBha2Etd3RmeHdpcXJwdXN1aA==";
    private static final Key hmacKey = Keys.hmacShaKeyFor(SECRET_KEY_ID.getBytes(StandardCharsets.UTF_8));

    public static String generateJwtToken(String username, long expirationTimeInMinutes) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMinutes * 60 * 1000))
                .signWith(hmacKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String validateAndExtractUsername(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            return null; // Invalid or expired JWT
        }
    }
}
