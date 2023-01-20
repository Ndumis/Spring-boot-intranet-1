package com.intranet.springboot.security;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenGenerator {
    public  String generateToken(Authentication authentication){
        String username = authentication.getName();

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + Constant.JWT_Expiration))
                .signWith(SignatureAlgorithm.HS512, Constant.JWT_Secret)
                .compact();
        return token;
    }

    public String getUsernameFromJWAT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(Constant.JWT_Secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(Constant.JWT_Secret).parseClaimsJws(token);
            return true;
        }catch (Exception ex){
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }

}
