package com.example.towns.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.function.Function;


@Component
public class TokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.duration}")
    private String duration;

    public String generateOneDayToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(duration)))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validate(String token, String username) {
        String tokenUsername = getUsername(token);
        Date expiration = getExpirationDate(token);
        return (tokenUsername.equals(username) && expiration.after(new Date()));
    }

    public Date getExpirationDate(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String getUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }
}
