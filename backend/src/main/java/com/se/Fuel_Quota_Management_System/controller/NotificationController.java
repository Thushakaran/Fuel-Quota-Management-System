package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.DTO.FuelStationRegistrationRequestDTO;
import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.model.SMSSendRequest;
import com.se.Fuel_Quota_Management_System.repository.FuelStationOwnerRepository;
import com.se.Fuel_Quota_Management_System.service.PhoneNumberService;
import com.se.Fuel_Quota_Management_System.service.TwilioOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final TwilioOTPService twilioOTPService;
    private final FuelStationOwnerRepository fuelStationOwnerRepository;
    private final PhoneNumberService phoneNumberService;

    @Autowired
    public NotificationController(TwilioOTPService twilioOTPService, FuelStationOwnerRepository fuelStationOwnerRepository, PhoneNumberService phoneNumberService) {

        this.twilioOTPService = twilioOTPService;
        this.fuelStationOwnerRepository = fuelStationOwnerRepository;
        this.phoneNumberService = phoneNumberService;
    }

    @PostMapping("/VehicleRegistration")
    public ResponseEntity<String> sendVehicleRegistrationSMS(@RequestBody SMSSendRequest request) {
        if (request.getPhoneNumber() == null || request.getPhoneNumber().isEmpty()) {
            return ResponseEntity.badRequest().body("Phone number is required.");
        }


        String predefinedMessage = "Your vehicle has been registered successfully.";

        try {
            // Call the service to send SMS
            String response = twilioOTPService.sendSMS(request.getPhoneNumber(), predefinedMessage);
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
            String response = twilioOTPService.sendSMS(request.getPhoneNumber(), predefinedMessage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send SMS: " + e.getMessage());
        }
    }
    @PostMapping("/FuelStationRegistration")
    public ResponseEntity<String> sendFuelStationRegistrationSMS(@RequestBody FuelStationRegistrationRequestDTO request) {
        // Fetch the owner's phone number using the ownerId
        Long id = request.getId();

        FuelStationOwner owner = fuelStationOwnerRepository.findFuelStationOwnerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found for ID: " + id));

        // Validate phone number
        String phoneNumber = owner.getPhoneNumber();
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return ResponseEntity.badRequest().body("Phone number is missing for owner ID: " + id);
        }
        String formattedPhoneNumber = phoneNumberService.formatPhoneNumber(phoneNumber);

        // Predefined message
        String predefinedMessage = "Your fuel stations were registered successfully.";
        System.out.println("Sending SMS to: " + formattedPhoneNumber); // Log the formatted phone number

        try {
            // Call the service to send SMS
            String response = twilioOTPService.sendSMS(formattedPhoneNumber, predefinedMessage);
            return ResponseEntity.ok("SMS sent successfully: " + response);
        } catch (Exception e) {
            // Log the exception and return error response
            System.err.println("Failed to send SMS for owner ID " + id + ": " + e.getMessage());
            return ResponseEntity.status(500).body("Failed to send SMS: " + e.getMessage());
        }
    }

}
