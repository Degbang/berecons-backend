package com.shipping.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${app.jwt.secret:change-this-secret}")
  private String secret;

  @Value("${app.jwt.ttl-ms:86400000}")
  private long ttlMs;

  public String generateToken(String username) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + ttlMs);
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(now)
        .setExpiration(expiry)
        .signWith(getKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String extractUsername(String token) {
    return getClaims(token).getSubject();
  }

  public boolean isTokenValid(String token) {
    try {
      Claims claims = getClaims(token);
      return claims.getExpiration().after(new Date());
    } catch (Exception ex) {
      return false;
    }
  }

  private Claims getClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getKey() {
    byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
    if (keyBytes.length < 32) { // 256 bits minimum
      throw new IllegalStateException("JWT secret too short; must be at least 32 bytes. Set env JWT_SECRET to a long random value.");
    }
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
