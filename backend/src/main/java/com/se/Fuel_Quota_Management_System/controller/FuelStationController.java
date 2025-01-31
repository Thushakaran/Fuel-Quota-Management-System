package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.DTO.logs.FuelStationLogDTO;
import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.security.JwtUtil;
import com.se.Fuel_Quota_Management_System.service.FuelStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/fuel-station")
public class FuelStationController {


    private static final String ERROR_STATUS = "error";
    private static final String MESSAGE_KEY = "message";

    @Autowired
    private FuelStationService fuelStationService;

    @Autowired
    private JwtUtil jwtUtil;

    // register fuel station
    @PostMapping("/register")
    public ResponseEntity<?> registerFuelStation(@Validated @RequestBody FuelStationLogDTO request) {
        try {
            //register the fuel station
            FuelStation registeredStation = fuelStationService.registerFuelStation(request);

            //generate JWT token for the registered fuel station
            String token = jwtUtil.generateToken(registeredStation.getStationLog().getUserName());
            return ResponseEntity.ok(Map.of("id", registeredStation.getId(), "token", token));
        } catch (Exception e) {
            return handleException(e, "Error during fuel station registration");
        }
    }

    //pump fuel to station
    @PreAuthorize("hasRole('STATION')")
    @PostMapping("/addFuel/{id}")
    public ResponseEntity<?> addFuel(@PathVariable("id") Long id, @RequestBody Map<String, Double> fuelDetails) {
        try {
            return fuelStationService.addFuels(id, fuelDetails);
        } catch (Exception e) {
            return handleException(e, "Error adding fuel to station");
        }
    }


    //find is any Fuel station registered on this RegisteredNumber
    @PreAuthorize("hasRole('STATION')")
    @GetMapping("/exists/{regNum}")
    public boolean existsByRegistrationNumber(@PathVariable("regNum") String registrationNumber) {
        return fuelStationService.existsByRegistrationNumber(registrationNumber);
    }


    // find station by login id
    @GetMapping("/findByLoginId/{id}")
    public ResponseEntity<?> getIdByLoginId(@PathVariable("id") Long loginId) {
        try {
            FuelStation station = fuelStationService.findFuelStationByStationLog(loginId);
            return ResponseEntity.ok(station.getId());
        } catch (Exception e) {
            return handleException(e, "Error finding station by login ID");
        }
    }

    //find fuels in the fuelStation by id
    @GetMapping("/findFuels/{id}")
    public ResponseEntity<?> getFuels(@PathVariable("id") Long stationId) {
        try {
            Map<String, Double> availableFuel = fuelStationService.getFuelInventory(stationId);
            return ResponseEntity.ok(availableFuel);
        } catch (Exception e) {
            return handleException(e, "Error finding fuels in station");
        }
    }

    // find station name by station id
    @GetMapping("/findName/{id}")
    public ResponseEntity<?> getNameById(@PathVariable("id") Long stationId) {
        try {
            FuelStation station = findFuelStationById(stationId);
            return ResponseEntity.ok(station.getStationName());
        } catch (Exception e) {
            return handleException(e, "Error finding station name by ID");
        }
    }

    // find station name by station id
    @PreAuthorize("hasRole('STATION')")
    @GetMapping("/findDetail/{id}")
    public ResponseEntity<?> getDetailsById(@PathVariable Long id) {
        try {
            FuelStation fuelStation = findFuelStationById(id);
            return ResponseEntity.ok(fuelStation);
        } catch (Exception e) {
            return handleException(e, "Error finding station details by ID");
        }
    }

    // Save edited station details
    @PreAuthorize("hasRole('STATION')")
    @PutMapping("/saveDetails/{id}")
    public ResponseEntity<?> saveEditDetails(@PathVariable("id") Long id, @RequestBody FuelStation fuelStation) {
        try {
            fuelStationService.saveEditDetails(id, fuelStation);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return handleException(e, "Error saving station details");
        }
    }

    // find station status by station id
    @PreAuthorize("hasRole('STATION')")
    @GetMapping("/findStatus/{id}")
    public ResponseEntity<?> getStatusById(@PathVariable Long id) {
        try {
            FuelStation fuelStation = findFuelStationById(id);
            return ResponseEntity.ok(fuelStation.isActive());
        } catch (Exception e) {
            return handleException(e, "Error finding station status by ID");
        }
    }

    //method to find a fuel station by ID
    private FuelStation findFuelStationById(Long id) {
        return fuelStationService.findFuelStationById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found"));
    }

    //method to handle exceptions consistently
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