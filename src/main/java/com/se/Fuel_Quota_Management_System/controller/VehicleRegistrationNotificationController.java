package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.model.SMSSendRequest;
import com.se.Fuel_Quota_Management_System.service.TwilioOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class VehicleRegistrationNotificationController {

    private final TwilioOTPService twilioOTPService;

    @Autowired
    public VehicleRegistrationNotificationController(TwilioOTPService twilioOTPService) {
        this.twilioOTPService = twilioOTPService;
    }

    @PostMapping("/send-sms")
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
}
