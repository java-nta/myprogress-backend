package com.progress.app.provider;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

import com.progress.app.exception.InvalidTokenException;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
  private String key = "@allahyamolan73--?09HammsaP09lestine";

  private final long EXPIRATION_TIME = 864_000_000; // 10 days

  protected SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
  }

  public String generateJwtToken(String username) {
    log.info("STARTING TOKEN GENERATION FOR USER : [{}]", username);
    Date now = new Date();
    Date expiration = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
    return Jwts.builder()
        .claims()
        .issuedAt(now)
        .expiration(expiration)
        .subject(username)
        .and()
        .signWith(getSecretKey())
        .compact();

  }

  public String extractPayload(String token) {
    JwtParser jwtParser = Jwts.parser().verifyWith(getSecretKey()).build();
    return jwtParser.parseSignedClaims(token).getPayload().getSubject();
  }

  public boolean validateToken(String token) {
    log.info("VALIDATE TOKEN [{}]",token);
    try {
      extractPayload(token);
      log.info("TOKEN IS VALIDE");
      return true;
    } catch (Exception e) {
      throw new InvalidTokenException("Invalide token");
    }
  }

  public String extractTokenFromRequest(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    if (token != null && token.startsWith("Bearer ")) {
      return token.substring(7);
    } else {
      throw new InvalidTokenException("Token is invalid");
    }
  }
}
