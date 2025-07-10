package com.sparky6od.logingestor.auth_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // TODO: Guardar la secret de produccion en un Vault (Ver como realizarlo)
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMs;

    private SecretKey getSigninKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String clientId, String serviceName) {
        return Jwts.builder()
                .subject(clientId)
                .claim("service", serviceName)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigninKey())
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
}
