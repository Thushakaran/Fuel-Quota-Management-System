package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.model.VehicleOwner;
import com.se.Fuel_Quota_Management_System.service.VehicleOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicle-owner")
public class VehicleOwnerController {

    @Autowired
    private VehicleOwnerService vehicleOwnerService;

    @GetMapping("/details/{id}")
    public ResponseEntity<VehicleOwner> getOwnerDetails(@PathVariable Long id) {
        VehicleOwner owner = vehicleOwnerService.getOwnerById(id);
        if (owner != null) {
            return ResponseEntity.ok(owner);
        }
        return ResponseEntity.notFound().build();
    }
}
