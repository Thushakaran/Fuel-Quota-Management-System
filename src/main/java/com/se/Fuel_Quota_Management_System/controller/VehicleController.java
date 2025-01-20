package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.service.vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    // Register a new vehicle
    @PostMapping("/register")
    public ResponseEntity<Vehicle> registerVehicle(@RequestBody Vehicle vehicle) {
        Vehicle registeredVehicle = vehicleService.registerVehicle(vehicle);
        return ResponseEntity.ok(registeredVehicle);
    }

    // Get vehicle details by vehicle number
    @GetMapping("/{vehicleNumber}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable String vehicleNumber) {
        Vehicle vehicle = vehicleService.getVehicleByNumber(vehicleNumber);
        return ResponseEntity.ok(vehicle);
    }

    // Update an existing vehicle details
    @PutMapping("/{vehicleNumber}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable String vehicleNumber, @RequestBody Vehicle updatedVehicle) {
        Vehicle existingVehicle = vehicleService.getVehicleByNumber(vehicleNumber);

        // If the vehicle exists, update it
        if (existingVehicle != null) {
            // Update only the fields you want to modify (example: owner name, vehicle type, etc.)
            existingVehicle.setOwnerName(updatedVehicle.getOwnerName());
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