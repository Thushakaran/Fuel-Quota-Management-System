package com.se.Fuel_Quota_Management_System.controller;


import com.se.Fuel_Quota_Management_System.DTO.FuelStationLogDTO;
import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.repository.FuelStationOwnerRepository;
import com.se.Fuel_Quota_Management_System.service.FuelStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/fuel-station")
public class FuelStationController {
    @Autowired
    private FuelStationService fuelStationService;

    @Autowired
    private FuelStationOwnerRepository ownerRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerFuelStation(@Validated @RequestBody FuelStationLogDTO request) {
        try {
            FuelStation registeredStation = fuelStationService.registerFuelStation(request);
            return ResponseEntity.ok(registeredStation.getId());
        } catch (Exception e) {
            // Log the error (use a logger in production)
            System.err.println("Error during registration: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }


    // Find is any Fuel station registered on this RegisteredNumber
    @GetMapping("{regnum}")
    public boolean existsByRegisNumById(@PathVariable("regnum") String registrationNumber) {
        return fuelStationService.existsByRegistrationNumber(registrationNumber);
    }

    // FindfuelStation Details By id
    @GetMapping("findbyid/{id}")
    public Optional<FuelStation> findByStationId(@PathVariable("id") Long id){
        return fuelStationService.findByStationId(id);
    }


}
