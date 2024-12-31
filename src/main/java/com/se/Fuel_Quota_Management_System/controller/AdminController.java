package com.se.Fuel_Quota_Management_System.controller;


//import com.se.Fuel_Quota_Management_System.model.Admin;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/admin")
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
}


