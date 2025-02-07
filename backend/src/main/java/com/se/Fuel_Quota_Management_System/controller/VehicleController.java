package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.DTO.logs.VehicleOwnerLogDTO;
import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.service.VehicleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    // Register a new vehicle
    @PostMapping("/register")
    public ResponseEntity<Vehicle> registerVehicle(@RequestBody VehicleOwnerLogDTO vehicleOwnerLogDTO) {
        Vehicle registeredVehicle = vehicleService.registerVehicle(vehicleOwnerLogDTO);
        return ResponseEntity.ok(registeredVehicle);
    }

    // find vehicle by login Id
    @PreAuthorize("hasAuthority('VEHICLE')")
    @GetMapping("/findbyloginid/{id}")
    public ResponseEntity<?> getidbyloginid(@PathVariable("id") Long loginid) {
        try {
            Vehicle vehicle = vehicleService.findVehicleByOwnerLog(loginid);
            return ResponseEntity.ok(vehicle.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }


    // Get vehicle details by vehicle number
    @GetMapping("/{vehicleNumber}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable String vehicleNumber) {
        Vehicle vehicle = vehicleService.getVehicleByNumber(vehicleNumber);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/dashboard/{vehicleId}")
    @Transactional
    public ResponseEntity<Vehicle> getVehicle(@PathVariable Long vehicleId) {
        Optional<Vehicle> vehicleOptional = vehicleService.getVehicleById(vehicleId);

        if (vehicleOptional.isPresent()) {
            return ResponseEntity.ok(vehicleOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
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

        // If the vehicle exists, update it
        if (existingVehicle != null) {
//            existingVehicle.setOwnerName(updatedVehicle.getOwnerName());
            existingVehicle.setVehicleType(updatedVehicle.getVehicleType());
            existingVehicle.setFuelType(updatedVehicle.getFuelType());
            existingVehicle.setFuelQuota(updatedVehicle.getFuelQuota());

            // Save the updated vehicle
            Vehicle savedVehicle = vehicleService.updateVehicle(existingVehicle);
            return ResponseEntity.ok(savedVehicle);
        }

        // If vehicle does not exist, return 404
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/qr/{vehicleNumber}")
    public ResponseEntity<byte[]> getQrCode(@PathVariable String vehicleNumber) {
        Vehicle vehicle = vehicleService.getVehicleByNumber(vehicleNumber);
        byte[] qrCodeBytes = Base64.getDecoder().decode(vehicle.getQrCode());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vehicle_qr.png")
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCodeBytes);
    }

}