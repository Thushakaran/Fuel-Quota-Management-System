package com.se.Fuel_Quota_Management_System.controller;


import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')") // Ensure only admins can access
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/vehicles")
    public List<Vehicle> getAllVehicles() {
        return adminService.getAllVehicles();
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

    @GetMapping("/stations")
    public List<FuelStation> getAllFuelStation() {
        return adminService.getAllFuelStation();
    }
}


