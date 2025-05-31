package com.dharshan.weatherapp.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtil {
    @Value("${jwt.secret}")
    private String Secret_key;

//    the below code will convert the secret key into the bytes array and return a cryptographic key
    private Key getSigningKey(){
        byte[] keybytes=Base64.getDecoder().decode(Secret_key);
        return Keys.hmacShaKeyFor(keybytes);
    }
    public String extractEmail(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }

//    the below code is an helper function which accepts token and any function as input and
//    return new generic or collection of any type so we had given the T outside and inside the generic
    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

//    the below function is used to parse the token and returns the claim body
    public Claims extractAllClaims(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (JwtException | IllegalArgumentException e){
            throw new RuntimeException("Invalid Jwt token",e);
        }
    }

//    check the token is expirated by comparing the token expiration date with current date
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
//    the below function will create a new token
    public String generateToken(String email){
        return createToken(email);
    }
    private String createToken(String email){
        long now=System.currentTimeMillis();
        long expirationMillis = 1000 * 60 * 10;
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(now+expirationMillis))
                .setIssuedAt(new Date(now))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    validate the token by the email id matches the from the input and the token
    public Boolean validateToken(String token, String email) {
        final String extractEmail = extractEmail(token);
        return (extractEmail.equals(email) && !isTokenExpired(token));
}

}
