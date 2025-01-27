package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.model.SMSSendRequest;
import com.se.Fuel_Quota_Management_System.service.TwilioOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final TwilioOTPService twilioOTPService;

    @Autowired
    public NotificationController(TwilioOTPService twilioOTPService) {
        this.twilioOTPService = twilioOTPService;
    }

    @PostMapping("/VehicleRegistration")
    public ResponseEntity<String> sendVehicleRegistrationSMS(@RequestBody SMSSendRequest request) {
        if (request.getPhoneNumber() == null || request.getPhoneNumber().isEmpty()) {
            return ResponseEntity.badRequest().body("Phone number is required.");
        }


        String predefinedMessage = "Your vehicle has been registered successfully.";

        try {
            // Call the service to send SMS
            String response = twilioOTPService.sendSMS(request.getPhoneNumber());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send SMS: " + e.getMessage());
        }
    }
    @PostMapping("/FuelStationOwnerRegistration")
    public ResponseEntity<String> sendFuelStationOwnerRegistrationSMS(@RequestBody SMSSendRequest request) {
        if (request.getPhoneNumber() == null || request.getPhoneNumber().isEmpty()) {
            return ResponseEntity.badRequest().body("Phone number is required.");
        }


        String predefinedMessage = "Your are registered successfully as a fuel station owner.";

        try {
            // Call the service to send SMS
            String response = twilioOTPService.sendSMS(request.getPhoneNumber());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send SMS: " + e.getMessage());
        }
    }
}
