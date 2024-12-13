package com.ptojetodb.projetodb.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private final String SECRET_KEY = "vTgYl2bSde1KERgrC9SiIWaErcBhahO0p400GjiTMRVPhMhyEjiECD527F0zeGpmfhvF8QvjPOQyY2FsEMsBLkXK84Gzb6d5M72GudygAaFlTTwr0UhrUgaoy8Y156W46kMrhGz4GDQxQfQwt4M5gpDqpyt6lOLqQQSxLYL2jqqhJw";
    private final long EXPIRATION_TIME = 86400000;

    // Geração do token JWT
    public String generateToken(String username) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        return getClaims(token).getExpiration().after(new Date());
    }

    private Claims getClaims(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
