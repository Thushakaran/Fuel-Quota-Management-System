package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.DTO.FuelStationRegistrationConfirmationDTO;
import com.se.Fuel_Quota_Management_System.DTO.FuelTransactionRegistrationRequestDTO;
import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.model.SMSSendRequest;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.repository.FuelStationOwnerRepository;
import com.se.Fuel_Quota_Management_System.repository.VehicleRepository;
import com.se.Fuel_Quota_Management_System.service.PhoneNumberService;
import com.se.Fuel_Quota_Management_System.service.TwilioOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final TwilioOTPService twilioOTPService;
    private final FuelStationOwnerRepository fuelStationOwnerRepository;
    private final PhoneNumberService phoneNumberService;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public NotificationController(TwilioOTPService twilioOTPService, FuelStationOwnerRepository fuelStationOwnerRepository, PhoneNumberService phoneNumberService, VehicleRepository vehicleRepository) {

        this.twilioOTPService = twilioOTPService;
        this.fuelStationOwnerRepository = fuelStationOwnerRepository;
        this.phoneNumberService = phoneNumberService;
        this.vehicleRepository = vehicleRepository;
    }

    @PostMapping("/VehicleRegistration")
    public ResponseEntity<String> sendVehicleRegistrationSMS(@RequestBody SMSSendRequest request) {
        if (request.getPhoneNumber() == null || request.getPhoneNumber().isEmpty()) {
            return ResponseEntity.badRequest().body("Phone number is required.");
        }

        String formattedPhoneNumber = phoneNumberService.formatPhoneNumber(SMSSendRequest.phoneNumber);
        String predefinedMessage = "Your vehicle has been registered successfully.";
        System.out.println("Sending SMS to: " + formattedPhoneNumber);
        try {

            String response = twilioOTPService.sendSMS(formattedPhoneNumber, predefinedMessage);
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

        String formattedPhoneNumber = phoneNumberService.formatPhoneNumber(SMSSendRequest.phoneNumber);
        String predefinedMessage = "Your are registered successfully as a fuel station owner.";
        System.out.println("Sending SMS to: " + formattedPhoneNumber);
        try {

            String response = twilioOTPService.sendSMS(formattedPhoneNumber, predefinedMessage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send SMS: " + e.getMessage());
        }
    }
    @PostMapping("/FuelStationRegistration")
    public ResponseEntity<String> sendFuelStationRegistrationSMS(@RequestBody FuelStationRegistrationConfirmationDTO request) {
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


        String predefinedMessage = "Your fuel stations were registered successfully.";
        System.out.println("Sending SMS to: " + formattedPhoneNumber); // Log the formatted phone number

        try {

            String response = twilioOTPService.sendSMS(formattedPhoneNumber, predefinedMessage);
            return ResponseEntity.ok("SMS sent successfully: " + response);
        } catch (Exception e) {

            System.err.println("Failed to send SMS for owner ID " + id + ": " + e.getMessage());
            return ResponseEntity.status(500).body("Failed to send SMS: " + e.getMessage());
        }
    }


    @PostMapping("/FuelTransactionNotification")
    public ResponseEntity<String> sendFuelTransactionSMS(@RequestBody FuelTransactionRegistrationRequestDTO request) {
        // Fetch the vehicle owner's phone number using the vehicleId
        Long vehicleId = request.getVehicleId();

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found for ID: " + vehicleId));

        // Validate phone number
        String phoneNumber = vehicle.getPhoneNumber();
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return ResponseEntity.badRequest().body("Phone number is missing for vehicle ID: " + vehicleId);
        }
        String formattedPhoneNumber = phoneNumberService.formatPhoneNumber(phoneNumber);

        // Get the fuel amount
        double fuelAmount = request.getAmount();

        // Get the current time formatted as "dd-MM-yyyy HH:mm:ss"
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));


        String message = "Fuel transaction successful. " + fuelAmount + " liters have been pumped at " + currentTime + ".";
        System.out.println("Sending SMS to: " + formattedPhoneNumber); // Log the formatted phone number

        try {
            String response = twilioOTPService.sendSMS(formattedPhoneNumber, message);
            return ResponseEntity.ok("SMS sent successfully: " + response);
        } catch (Exception e) {

            System.err.println("Failed to send SMS for vehicle ID " + vehicleId + ": " + e.getMessage());
            return ResponseEntity.status(500).body("Failed to send SMS: " + e.getMessage());
        }
    }



}
