package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserLogController {
    @Autowired
    private JwtUtil jwtUtil;

    @Value("${role.admin}")
    private String roleAdmin;

    @Value("${role.stationowner}")
    private String roleStationowner;

    @Value("${role.station}")
    private String roleStation;

    @GetMapping("/protected-data")
    public ResponseEntity<String> getProtectedData(@RequestHeader("Authorization") String token){
        System.out.println(token);
        if(token != null && token.startsWith("Bearer ")){
            String jwtToken = token.substring(7);
            try {
                if(jwtUtil.isTokenValid(jwtToken)){
                    String username = jwtUtil.extractUserName(jwtToken);

                    String roles = jwtUtil.extractRole(jwtToken);

                    if(roles.equals(roleAdmin)){
                        return ResponseEntity.ok("Welcome "+ username + " Here is the "+ roles + " - specific data");
                    }else if (roles.equals(roleStationowner)){
                        return ResponseEntity.ok("Welcome "+ username + " Here is the "+ roles + " - specific data");
                    }else if (roles.equals(roleStation)) {
                        return ResponseEntity.ok("Welcome " + username + " Here is the " + roles + " - specific data");
                    }else {
                        return ResponseEntity.status(403).body("Access Denied: You don't have the necessary role");

                    }
                }
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Token");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header missing or invalid");
    }


}
