package com.se.Fuel_Quota_Management_System.controller;

//import com.se.Fuel_Quota_Management_System.model.Admin;

import com.se.Fuel_Quota_Management_System.DTO.DashboardData;
import com.se.Fuel_Quota_Management_System.DTO.RegisterRequest;
import com.se.Fuel_Quota_Management_System.model.FuelStation;

import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/admin")
//@PreAuthorize("hasRole('ADMIN')") // Ensure only admins can access
public class AdminController {

    @Autowired
    private AdminService adminService;
    /* Get all Vehicle*/

    @GetMapping("/vehicles")
    public List<Vehicle> getAllVehicles() {
        return adminService.getAllVehicles();
    }


    /**
     * Get a specific vehicle by ID.
     */
    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        Vehicle vehicle = adminService.getVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }

    /**
     * Get vehicles by type.
     */


    @GetMapping("/vehicles/type/{vehicleType}")
    public ResponseEntity<List<Vehicle>> getVehiclesByType(@PathVariable String vehicleType) {
        List<Vehicle> vehicles = adminService.getVehiclesByType(vehicleType);
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Get vehicles by owner name.
     */
    @GetMapping("/vehicles/owner/{ownerName}")
    public ResponseEntity<Optional<Vehicle>> getVehiclesByOwner(@PathVariable String ownerName) {
        Optional<Vehicle> vehicles = adminService.getVehiclesByOwner(ownerName);
        return ResponseEntity.ok(vehicles);
    }


    /**
     * Update vehicle details by ID.
     */
    @PutMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle updatedVehicle) {
        Vehicle vehicle = adminService.updateVehicle(id, updatedVehicle);
        return ResponseEntity.ok(vehicle);
    }

    /**
     * Delete a vehicle by ID.
     */
    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long id) {
        adminService.deleteVehicle(id);
        return ResponseEntity.ok("Vehicle with ID " + id + " deleted successfully.");
    }


    @GetMapping("/station")
    public List<FuelStation> getAllFuelStation() {
        return adminService.getAllFuelStation();
    }


    // Get a specific fuel station by ID.

    @GetMapping("/station/serch_id/{id}")
    public ResponseEntity<FuelStation> getFuelStationById(@PathVariable Long id) {
        FuelStation fuelStation = adminService.getFuelStationById(id);
        return ResponseEntity.ok(fuelStation);
    }


    // Get a specific fuel station by owner_ID.

    @GetMapping("/station/serch_owner_id/{owner_id}")
    public ResponseEntity<FuelStation> getFuelStationByOwnerId(@PathVariable Long owner_id) {
        FuelStation fuelStation = adminService.getFuelStationByOwnerId(owner_id);
        return ResponseEntity.ok(fuelStation);
    }



     // Get fuel station by location.

    @GetMapping("/station/serch_location/{location}")
    public ResponseEntity<FuelStation> getFuelStationByLocation(@PathVariable String location) {
        FuelStation fuelStation = adminService.getFuelStationByLocation(location);
        return ResponseEntity.ok(fuelStation);
    }


    // Get fuel station by station_name.

    @GetMapping("/station/serch_station_name/{station_name}")
    public ResponseEntity<FuelStation> getFuelStationByStationName(@PathVariable String station_name) {
        FuelStation fuelStation = adminService.getFuelStationByStationName(station_name);
        return ResponseEntity.ok(fuelStation);
    }


    // Get fuel station by registration_number.

    @GetMapping("/station/serch_registration_number/{registration_number}")
    public ResponseEntity<FuelStation> getFuelStationByRegistrationNumber(@PathVariable String registration_number) {
        FuelStation fuelStation = adminService.getFuelStationByRegistrationNumber(registration_number);
        return ResponseEntity.ok(fuelStation);
    }

    // Update FuelStation details by ID.

    @PutMapping("/station/{id}")
    public ResponseEntity<FuelStation> updateFuelStation(@PathVariable Long id, @RequestBody FuelStation updatedFuelStation) {
        FuelStation fuelStation = adminService.updateFuelStation(id, updatedFuelStation);
        return ResponseEntity.ok(fuelStation);
    }



     // Delete a FuelStation details by ID.


    @DeleteMapping("/station/{id}")
    public ResponseEntity<String> deleteFuelStation(@PathVariable Long id) {
        adminService.deleteFuelStation(id);
        return ResponseEntity.ok("FuelStation with ID " + id + " deleted successfully.");
    }


    @GetMapping("/dashboard-data")
    public DashboardData getDashboardData() {
        return adminService.getDashboardData();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@Validated @RequestBody RegisterRequest registerRequest){
        try {
            ResponseEntity<?> registerAdmin = adminService.registerAdmin(registerRequest);
            return ResponseEntity.ok(registerAdmin.getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()));
        }
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Optional<FuelTransaction>> getTransactionById(@PathVariable Long id) {
        Optional<FuelTransaction> transactions = Optional.ofNullable(adminService.getTransactionById(id));
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/transactions")
    public List<FuelTransaction> getFuelTransactions() {
        return adminService.getFuelTransactions();
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        adminService.deleteTransaction(id);
        return ResponseEntity.ok("FuelTransactions with ID " + id + " deleted successfully.");
    }
}


