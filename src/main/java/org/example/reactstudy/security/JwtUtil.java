package org.example.reactstudy.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProperties jwtProperties;
    private Key key;

    @Bean
    public JwtUtil jwtSecretKey() {
        key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
        return this;
    }

    public String generateAccessToken(Long userId, Collection<? extends GrantedAuthority> authorities) {
        return generateToken(userId, authorities, jwtProperties.getAccessTokenExpiration());
    }

    public String generateRefreshToken(Long userId, Collection<? extends GrantedAuthority> authorities) {
        return generateToken(userId, authorities, jwtProperties.getRefreshTokenExpiration());
    }

    private String generateToken(Long userId, Collection<? extends GrantedAuthority> authorities, long validity) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validity);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                //권한 해제
                //.claim("roles", authorities.stream().map(GrantedAuthority::getAuthority).toList())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch (ExpiredJwtException e) {
            log.warn("JWT 토큰이 만료됨 : {}", e.getMessage());
        }
        catch (JwtException | IllegalArgumentException e) {
            log.warn("JWT 토큰이 유효하지 않음 : {}", e.getMessage());
        }
        return false;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
