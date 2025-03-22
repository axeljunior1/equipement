package com.projet.equipement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private  String secret;


    @Value("${jwt.expiration}")
    private long expiration;

    // Générer un token


    public  String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith( getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private  SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Valider un token
    public  boolean validateToken(String token) {
        try {
            System.out.println("validation");
            Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
            return extractAllClaims(token).getSubject();
    }



}
