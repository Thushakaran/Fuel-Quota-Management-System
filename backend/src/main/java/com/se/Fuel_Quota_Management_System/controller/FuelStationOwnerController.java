package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.DTO.FuelStationOwnerLogDTO;
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
import java.util.Optional;


@RestController
@RequestMapping("api/owner")
public class FuelStationOwnerController {
    @Autowired
    private FuelStationOwnerService fuelStationOwnerService;

    @Autowired
    private FuelStationService fuelStationService;

    @Autowired
    private JwtUtil jwtUtil;


    // for register StationOwner
    @PostMapping("/register")
    public ResponseEntity<?> registerOwner(@Validated @RequestBody FuelStationOwnerLogDTO request) {
        try {
            // Register the fuel stationOwner
            FuelStationOwner registeredStationOwner = fuelStationOwnerService.registerOwner(request);

            // Generate JWT token for the registered fuel station
            String token = jwtUtil.generateToken(registeredStationOwner.getOwnerLog().getUserName());

            // Return the response with the station ID and token
            return ResponseEntity.ok(Map.of("id", registeredStationOwner.getId(), "token", token));
        }
        catch (Exception e) {
            System.err.println("Error during registration: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", e.getMessage()));
        }
    }



    // find owner name by Id
    @PreAuthorize("hasAuthority('stationowner')")
    @GetMapping("/findName/{id}")
    public ResponseEntity<?> getOwnerById(@PathVariable("id") Long id) {
        try {
            FuelStationOwner owner = fuelStationOwnerService.findFuelStationOwnerById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found"));
            return ResponseEntity.ok(owner.getName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", e.getMessage()));
        }
    }


    // find owner details by Id
    @PreAuthorize("hasAuthority('stationowner')")
    @GetMapping("findDetail/{id}")
    public ResponseEntity<?> getDetailsbyId(@PathVariable Long id){
        try {
            FuelStationOwner owner = fuelStationOwnerService.findFuelStationOwnerById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found"));
            return ResponseEntity.ok(owner);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", e.getMessage()));
        }
    }


    // find fuel stations owned by owner through owner id
    @PreAuthorize("hasAuthority('stationowner')")
    @GetMapping("findStations/{id}")
    public ResponseEntity<?> getStationsById(@PathVariable("id") Long id){
        try {
            List<FuelStation> fuelStation = fuelStationService.getByOwnerId(id);
            if (fuelStation.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "error", "message", "No fuel stations found"));
            }
            return ResponseEntity.ok(fuelStation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", e.getMessage()));
        }
    }


    // find owner by login Id
    @PreAuthorize("hasAuthority('stationowner')")
    @GetMapping("/findByLoginId/{id}")
    public ResponseEntity<?> getIdByLoginId(@PathVariable("id") Long loginid){
        try {
            FuelStationOwner owner = fuelStationOwnerService.findFuelStationOwnerByOwnerLog(loginid);
            return ResponseEntity.ok(owner.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", e.getMessage()));
        }
    }


    // save details when update profile
    @PreAuthorize("hasAuthority('stationowner')")
    @PutMapping("saveDetails/{id}")
    public ResponseEntity<?> saveEditDetails(@PathVariable("id") Long id, @RequestBody FuelStationOwner fuelStationOwner){
        try {
            FuelStationOwner owner = fuelStationOwnerService.saveEditDetails(id,fuelStationOwner);
            return ResponseEntity.ok("sucess");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

}
