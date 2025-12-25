package me.sreejithnair.ecomx_api.auth.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import me.sreejithnair.ecomx_api.auth.service.JwtService;
import me.sreejithnair.ecomx_api.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import me.sreejithnair.ecomx_api.user.entity.Role;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Override
    public String generateToken(User user, long expiryMinutes) {

        long expiryMillis = System.currentTimeMillis() + expiryMinutes * 60 * 1000;

        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("roles", roleNames)
                .issuedAt(new Date())
                .expiration(new Date(expiryMillis))
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

}
