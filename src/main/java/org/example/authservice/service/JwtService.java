package org.example.authservice.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {
    private String secretKeyStr = "JlkjubhlIBLjgljyvGJvk43223UGVUKVKJ";
    private long jwtExpression = 60000 * 2;
    private long refreshExpression = 60000 * 5;



    //#1 получить имя юзера из токена
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //#1 вспомогательный метод 1
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    //# 1 вспомогательный метод 2
    private Claims extractAllClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyStr.getBytes());
        Claims claims = Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }


    //#2 генерация токена
    public String generateToken(Map<String,Object> extraClaims,UserDetails userDetails){
        return buildToken(extraClaims,userDetails,jwtExpression);
    }
    //#2 генерация токена без Claims
    public String generateToken(UserDetails userDetails){
        return buildToken(new HashMap<>(),userDetails,jwtExpression);
    }

    public String generateRefreshToken(UserDetails userDetails){
        return buildToken(new HashMap<>(),userDetails,refreshExpression);
    }

    //#2 вспомогательный метод
    private String buildToken(Map<String,Object> extraClaims,UserDetails userDetails, Long expiration ){
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyStr.getBytes());

        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    //проверка срока токена true - истек
    public boolean isTokenExpired(String token){
        Date date = extractClaim(token, Claims::getExpiration);
        return date.before(new Date());
    }

    //проверка на валидность токена
    public boolean isValidToken(String token, UserDetails userDetails){
        String userNameFromToken = extractUsername(token);
        return userNameFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }
}
