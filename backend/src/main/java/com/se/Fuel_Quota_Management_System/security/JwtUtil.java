package com.se.Fuel_Quota_Management_System.security;

import com.se.Fuel_Quota_Management_System.model.Role;
import com.se.Fuel_Quota_Management_System.model.UserLog;
import com.se.Fuel_Quota_Management_System.repository.UserLogRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // secret key
    private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Autowired
    UserLogRepository userLogRepository;


    //generate JWT token
    public String generateToken(String username) {
        Optional<UserLog> userOpt = userLogRepository.findByUserName(username);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Role role = userOpt.get().getRole();
        long jwtExpirationMilliSecond = 24 * 60 * 60 * 1000L; // 1 day

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMilliSecond))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }


    //extract username
    public String extractUserName(String token) {
        return parseClaims(token).getSubject();
    }

    //extract Role
    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    //JWT token validation
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.warn("Invalid JWT Token: {}", e.getMessage());
            return false;
        }
    }

    //parse JWT claims
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
