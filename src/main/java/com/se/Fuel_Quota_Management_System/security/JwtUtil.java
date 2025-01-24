package com.se.Fuel_Quota_Management_System.security;

import com.se.Fuel_Quota_Management_System.model.Role;
import com.se.Fuel_Quota_Management_System.model.UserLog;
import com.se.Fuel_Quota_Management_System.repository.UserLogRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;


@Component
public class JwtUtil {
    //secret key
    private static final SecretKey secretkey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private final UserLogRepository userlogRepository;

    @Autowired
    public JwtUtil(UserLogRepository userlogRepository) {
        this.userlogRepository = userlogRepository;
    }

    //generate JWT token
    public String generateToken(String username) {
        Optional<UserLog> userOpt = userlogRepository.findByUserName(username);
        if (userOpt.isPresent()) {
            Role role = userOpt.get().getRole();
            //expiration date
            int jwtExpirationMilliSecond = 86400000;
            return Jwts.builder()
                    .setSubject(username)
                    .claim("role", role.getName())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + jwtExpirationMilliSecond))
                    .signWith(secretkey)
                    .compact();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }


    //extract username
    public String extractUserName(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretkey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //extract Role
    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretkey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    //JWT token validation
    public static  boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretkey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
