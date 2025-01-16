package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.security.JwtUtil;
import com.se.Fuel_Quota_Management_System.model.RegisterRequest;
import com.se.Fuel_Quota_Management_System.model.Role;
import com.se.Fuel_Quota_Management_System.model.UserLog;
import com.se.Fuel_Quota_Management_System.repository.RoleRepository;
import com.se.Fuel_Quota_Management_System.repository.UserLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

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
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {

        if (userLogRepository.findByUserName(registerRequest.getUserName()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username already taken"));
        }

        Optional<Role> roleOptional = roleRepository.findByName(registerRequest.getRole());
        if (roleOptional.isEmpty()) {
            System.out.println("Invalid role name: " + registerRequest.getRole());
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid role name"));
        }

        UserLog newUser = new UserLog();
        newUser.setUserName(registerRequest.getUserName());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole(roleOptional.get());

        userLogRepository.save(newUser);

        return ResponseEntity.ok(Map.of("message", "User Registered Successfully"));
    }


    //login user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLog loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
            );

            //get user from database
            UserLog user = userLogRepository.findByUserName(loginRequest.getUserName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            //generate JWT token with roles
            String token = jwtUtil.generateToken(user.getUserName());
            return ResponseEntity.ok(token);

        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }


}
