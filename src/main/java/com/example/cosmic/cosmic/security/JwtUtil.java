package com.example.cosmic.cosmic.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET =
            "TXlTdXBlclNlY3JldEtleUZvckpXVFRva2VuMTIzNDU2Nzg5";

    /* ================= SIGN KEY ================= */

    private SecretKey getSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /* ================= GENERATE TOKEN ================= */

    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSignKey())
                .compact();
    }

    /* ================= EXTRACT USERNAME ================= */

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /* ================= EXTRACT CLAIMS ================= */

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSignKey())   // ✅ Now correct type
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /* ================= CHECK EXPIRATION ================= */

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    /* ================= VALIDATE TOKEN ================= */

    public boolean validateToken(String token, String email) {
        return email.equals(extractUsername(token)) && !isTokenExpired(token);
    }
}
