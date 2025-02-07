package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.DTO.logs.FuelStationOwnerLogDTO;
import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.security.JwtUtil;
import com.se.Fuel_Quota_Management_System.service.FuelStationOwnerService;
import com.se.Fuel_Quota_Management_System.service.FuelStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/owner")
public class FuelStationOwnerController {

    private static final String ERROR_STATUS = "error";
    private static final String MESSAGE_KEY = "message";

    @Autowired
    private FuelStationOwnerService fuelStationOwnerService;

    @Autowired
    private FuelStationService fuelStationService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerOwner(@Validated @RequestBody FuelStationOwnerLogDTO request) {
        try {
            FuelStationOwner registeredStationOwner = fuelStationOwnerService.registerOwner(request);
            String token = jwtUtil.generateToken(registeredStationOwner.getOwnerLog().getUserName());
            return ResponseEntity.ok(Map.of("id", registeredStationOwner.getId(), "token", token));
        } catch (Exception e) {
            return handleException(e, "Error during registration");
        }
    }

    @PreAuthorize("hasRole('STATIONOWNER')")
    @GetMapping("/findName/{id}")
    public ResponseEntity<?> getOwnerById(@PathVariable("id") Long id) {
        try {
            FuelStationOwner owner = findOwnerById(id);
            return ResponseEntity.ok(owner.getName());
        } catch (Exception e) {
            return handleException(e, "Error finding owner by ID");
        }
    }

    private FuelStationOwner findOwnerById(Long id) {
        return fuelStationOwnerService.findFuelStationOwnerById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found"));
    }

    @PreAuthorize("hasRole('STATIONOWNER')")
    @GetMapping("/findDetail/{id}")
    public ResponseEntity<?> getDetailsById(@PathVariable Long id) {
        try {
            FuelStationOwner owner = findOwnerById(id);
            return ResponseEntity.ok(owner);
        } catch (Exception e) {
            return handleException(e, "Error finding owner details by ID");
        }
    }

    @PreAuthorize("hasRole('STATIONOWNER')")
    @GetMapping("/findStations/{id}")
    public ResponseEntity<?> getStationsById(@PathVariable("id") Long id) {
        try {
            List<FuelStation> fuelStations = fuelStationService.getByOwnerId(id);
            if (fuelStations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", ERROR_STATUS, MESSAGE_KEY, "No fuel stations found"));
            }
            return ResponseEntity.ok(fuelStations);
        } catch (Exception e) {
            return handleException(e, "Error finding fuel stations by owner ID");
        }
    }


    @GetMapping("/findByLoginId/{id}")
    public ResponseEntity<?> getIdByLoginId(@PathVariable("id") Long loginId) {
        try {
            FuelStationOwner owner = fuelStationOwnerService.findFuelStationOwnerByOwnerLog(loginId);
            return ResponseEntity.ok(owner.getId());
        } catch (Exception e) {
            return handleException(e, "Error finding owner by login ID");
        }
    }

    @PreAuthorize("hasRole('STATIONOWNER')")
    @PutMapping("/saveDetails/{id}")
    public ResponseEntity<?> saveEditDetails(@PathVariable("id") Long id, @RequestBody FuelStationOwner fuelStationOwner) {
        try {
            fuelStationOwnerService.saveEditDetails(id, fuelStationOwner);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return handleException(e, "Error saving owner details");
        }
    }

    private ResponseEntity<?> handleException(Exception e, String errorMessage) {
        System.err.println(errorMessage + ": " + e.getMessage());

        Map<String, Object> response = Map.of(
                "status", "error",
                "message", errorMessage,
                "details", e.getMessage()
        );

        return ResponseEntity.badRequest().body(response);
    }

}