package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.model.OwnerLog;
import com.se.Fuel_Quota_Management_System.service.OwnerLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/owner")
public class OwnerLogController {
    @Autowired
    private OwnerLogService ownerlogservice;

    @PostMapping("/signup")
    public OwnerLog signup(@RequestBody OwnerLog ownerLog) {
        OwnerLog registeredlog = null;
        try {
             registeredlog = ownerlogservice.register(ownerLog);
            return registeredlog;
        } catch (DataIntegrityViolationException e) {
           // return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");
            return  registeredlog;
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody OwnerLog ownerLog) {
        boolean authenticated = ownerlogservice.authenticate(ownerLog.getOwnerUserName(), ownerLog.getPassword());
        if (authenticated) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials!");
        }
    }
}
