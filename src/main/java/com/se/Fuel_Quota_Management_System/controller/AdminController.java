package com.se.Fuel_Quota_Management_System.controller;



//import com.se.Fuel_Quota_Management_System.model.Admin;

import com.se.Fuel_Quota_Management_System.model.FuelStation;

import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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




////    @PutMapping("/vehicles/{id}")
////    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle updatedVehicle) {
////        return ResponseEntity.ok(adminService.updateVehicle(id, updatedVehicle));
////    }
////
////    @GetMapping("/logs")
////    public List<AdminLog> getAdminLogs() {
////        return adminService.getAdminLogs();
////    }
//
//    // Add other endpoints




    @GetMapping("/station")
    public List<FuelStation> getAllFuelStation() {
        return adminService.getAllFuelStation();
    }



     // Get a specific fuel station by ID.

    @GetMapping("/fuelStation/{id}")
    public ResponseEntity<FuelStation> getFuelStationById(@PathVariable Long id) {
        FuelStation fuelStation= adminService.getFuelStationById(id);
        return ResponseEntity.ok(fuelStation);
    }


    // Get a specific fuel station by owner_ID.

    @GetMapping("/fuelStation/{owner_id}")
    public ResponseEntity<FuelStation> getFuelStationByOwnerId(@PathVariable Long owner_id) {
        FuelStation fuelStation= adminService.getFuelStationByOwnerId(owner_id);
        return ResponseEntity.ok(fuelStation);
    }


     // Get vehicles by location.

    @GetMapping("/FuelStation/{location}")
    public ResponseEntity<FuelStation> getFuelStationByLocation(@PathVariable String location) {
        FuelStation fuelStation = adminService.getFuelStationByLocation(location);
        return ResponseEntity.ok(fuelStation);
    }


    // Get vehicles by station_name.

    @GetMapping("/FuelStation/{station_name}")
    public ResponseEntity<FuelStation> getFuelStationByStationName(@PathVariable String station_name) {
        FuelStation fuelStation = adminService.getFuelStationByStationName(station_name);
        return ResponseEntity.ok(fuelStation);
    }


// Get vehicles by registration_number.

    @GetMapping("/FuelStation/{registration_number}")
    public ResponseEntity<FuelStation> getFuelStationByRegistrationNumber(@PathVariable String registration_number) {
        FuelStation fuelStation = adminService.getFuelStationByRegistrationNumber(registration_number);
        return ResponseEntity.ok(fuelStation);
    }




     // Update FuelStation details by ID.

    @PutMapping("/FuelStation/{id}")
    public ResponseEntity<FuelStation> updateFuelStation(@PathVariable Long id, @RequestBody FuelStation updatedFuelStation) {
        FuelStation fuelStation = adminService.updateFuelStation(id, updatedFuelStation);
        return ResponseEntity.ok(fuelStation);
    }



     // Delete a FuelStation details by ID.

    @DeleteMapping("/FuelStation/{id}")
    public ResponseEntity<String> deleteFuelStation(@PathVariable Long id) {
        adminService.deleteFuelStation(id);
        return ResponseEntity.ok("FuelStation with ID " + id + " deleted successfully.");
    }


}


