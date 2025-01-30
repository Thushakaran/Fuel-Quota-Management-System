package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.DTO.logs.VehicleOwnerLogDTO;
import com.se.Fuel_Quota_Management_System.exception.ResourceNotFoundException;
import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    //register a new vehicle
    @PostMapping("/register")
    public ResponseEntity<Vehicle> registerVehicle(@Validated @RequestBody VehicleOwnerLogDTO vehicleOwnerLogDTO) {
        Vehicle registeredVehicle = vehicleService.registerVehicle(vehicleOwnerLogDTO);
        return ResponseEntity.ok(registeredVehicle);
    }

    // find vehicle by login Id
    @PreAuthorize("hasRole('VEHICLE')")
    @GetMapping("/findbyloginid/{id}")
    public ResponseEntity<?> getIdByLoginId(@PathVariable("id") Long loginId) {
        try {
            Vehicle vehicle = vehicleService.findVehicleByOwnerLog(loginId);
            return ResponseEntity.ok(vehicle.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    //get vehicle details by vehicle number
    @GetMapping("/by-number/{vehicleNumber}")
    public ResponseEntity<Vehicle> getVehicleByNumber(@PathVariable String vehicleNumber) {
        Vehicle vehicle = vehicleService.getVehicleByNumber(vehicleNumber);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/by-id/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long vehicleId) {
        return vehicleService.getVehicleById(vehicleId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/dashboard/transactions/{vehicleId}")
    public ResponseEntity<List<FuelTransaction>> getFuelTransactions(@PathVariable Long vehicleId) {
        List<FuelTransaction> transactions = vehicleService.getFuelTransactions(vehicleId);
        return ResponseEntity.ok(transactions);
    }

    // Update an existing vehicle details
    @PutMapping("/{vehicleNumber}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable String vehicleNumber, @RequestBody Vehicle updatedVehicle) {
        Vehicle existingVehicle = vehicleService.getVehicleByNumber(vehicleNumber);
        if (existingVehicle == null) {
            throw new ResourceNotFoundException("Vehicle not found with number: " + vehicleNumber);
        }
        existingVehicle.setVehicleType(updatedVehicle.getVehicleType());
        existingVehicle.setFuelType(updatedVehicle.getFuelType());
        existingVehicle.setFuelQuota(updatedVehicle.getFuelQuota());
        Vehicle savedVehicle = vehicleService.updateVehicle(existingVehicle);
        return ResponseEntity.ok(savedVehicle);
    }

    @GetMapping("/qr/{vehicleNumber}")
    public ResponseEntity<byte[]> getQrCode(@PathVariable String vehicleNumber) {
        Vehicle vehicle = vehicleService.getVehicleByNumber(vehicleNumber);
        if (vehicle == null || vehicle.getQrCode() == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] qrCodeBytes = Base64.getDecoder().decode(vehicle.getQrCode());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vehicle_qr.png")
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCodeBytes);
    }

}