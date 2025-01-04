package com.se.Fuel_Quota_Management_System.controller;


import com.se.Fuel_Quota_Management_System.model.StationLog;
import com.se.Fuel_Quota_Management_System.service.StationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/station")
public class StationLogController {

    @Autowired
    private StationLogService stationlogservice;

    @PostMapping("/signup")
    public StationLog signup(@RequestBody StationLog stationLog) {
        StationLog registeredlog = null;
        try {
            registeredlog = stationlogservice.register(stationLog);
            return registeredlog;
        } catch (DataIntegrityViolationException e) {
            // return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");
            return  registeredlog;
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody StationLog stationLog) {
        boolean authenticated = stationlogservice.authenticate(stationLog.getStationUserName(), stationLog.getPassword());
        if (authenticated) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials!");
        }
    }
}
