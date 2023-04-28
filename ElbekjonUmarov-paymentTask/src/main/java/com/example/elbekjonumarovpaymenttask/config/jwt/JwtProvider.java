package com.example.elbekjonumarovpaymenttask.config.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return Jwts
                .builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
       return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
            public boolean validateToken(String token){
            try {
                Jwts
                        .parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token);
                return true;
            } catch (MalformedJwtException e){
                System.out.println("mistakenly created token");
            }catch (UnsupportedJwtException e){
                System.out.println("not supportable token");
            }catch (IllegalArgumentException e){
                System.out.println("empty token");
            }catch (SignatureException e){
                System.out.println("not real token");
            }
            return false;
            }

}
