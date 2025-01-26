package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.DTO.FuelStationLogDTO;
import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.security.JwtUtil;
import com.se.Fuel_Quota_Management_System.service.FuelStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/fuel-station")
public class FuelStationController {
    @Autowired
    private FuelStationService fuelStationService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<?> registerFuelStation(@Validated @RequestBody FuelStationLogDTO request) {
        try {
            FuelStation registeredStation = fuelStationService.registerFuelStation(request);
            return ResponseEntity.ok(registeredStation.getId());
        } catch (Exception e) {
            // Log the error
            System.err.println("Error during registration: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()));
        }
    }

    //add fuel
    @PreAuthorize("station")
    @PostMapping("/addFuel/{id}")
    public ResponseEntity<?> addFuel(@PathVariable("id") Long id,  @RequestBody Map<String, Double> fuelDetails) {
        try {
           return fuelStationService.addFuels(id,fuelDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()));
        }
    }

    //find is any Fuel station registered on this RegisteredNumber
    @PreAuthorize("hasAuthority('station')")
    @GetMapping("{regNum}")
    public boolean existsByRegisNumById(@PathVariable("regnum") String registrationNumber) {
        return fuelStationService.existsByRegistrationNumber(registrationNumber);
    }

    // find station by login id
    @PreAuthorize("hasAuthority('station')")
    @GetMapping("/findByLoginId/{id}")
    public ResponseEntity<?> getIdByLoginId(@PathVariable("id") Long loginid){
        try {
            FuelStation station = fuelStationService.findFuelStationByStationLog(loginid);
            return ResponseEntity.ok(station.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()));
        }
    }

    @PreAuthorize(("hasAuthority('stationowner')"))
    @GetMapping("/findFuels/{id}")
    public ResponseEntity<?> getFuels(@PathVariable("id") Long stationId){
        try {
            Map<String,Double> availablefuel = fuelStationService.getFuelInventory(stationId);
            return ResponseEntity.ok(availablefuel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('station')")
    @GetMapping("/findName/{id}")
    public ResponseEntity<?> getNameById(@PathVariable("id") Long stationid){
        try {
            Optional<FuelStation> station = fuelStationService.findFuelStationById(stationid);
            return ResponseEntity.ok(station.get().getStationName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()));
        }
    }

}
