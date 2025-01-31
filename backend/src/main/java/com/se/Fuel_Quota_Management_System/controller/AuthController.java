package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.DTO.auth.AuthResponse;
import com.se.Fuel_Quota_Management_System.DTO.auth.RegisterRequest;
import com.se.Fuel_Quota_Management_System.model.Role;
import com.se.Fuel_Quota_Management_System.model.UserLog;
import com.se.Fuel_Quota_Management_System.repository.RoleRepository;
import com.se.Fuel_Quota_Management_System.repository.UserLogRepository;
import com.se.Fuel_Quota_Management_System.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private  JwtUtil jwtUtil;

    @Autowired
    private UserLogRepository userLogRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    //register user
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        logger.info("Received registration request for username: {}", registerRequest.getUserName());

        if (userLogRepository.findByUserName(registerRequest.getUserName()).isPresent()) {
            logger.warn("Username already taken: {}", registerRequest.getUserName());
            return ResponseEntity.badRequest().body(Map.of("message", "Username already taken"));
        }

        Optional<Role> roleOptional = roleRepository.findByName(registerRequest.getRole());
        if (roleOptional.isEmpty()) {
            logger.warn("Invalid role name provided: {}", registerRequest.getRole());
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid role name"));
        }

        UserLog newUser = new UserLog();
        newUser.setUserName(registerRequest.getUserName());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole(roleOptional.get());

        userLogRepository.save(newUser);
        logger.info("User registered successfully: {}", registerRequest.getUserName());

        return ResponseEntity.ok(newUser);
    }


    //login user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLog loginRequest) {
        logger.info("Received login request for username: {}", loginRequest.getUserName());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
            );

            //get user from database
            UserLog user = userLogRepository.findByUserName(loginRequest.getUserName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Generate JWT token
            String token = jwtUtil.generateToken(user.getUserName());

            // Create response
            AuthResponse authResponse = new AuthResponse(token, user.getRole(),user.getId());

            logger.info("User logged in successfully: {}", loginRequest.getUserName());
            return ResponseEntity.ok(authResponse);
        } catch (BadCredentialsException e) {
            logger.error("Invalid login attempt for username: {}", loginRequest.getUserName());
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }
}