package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.model.FuelFilling;
import com.se.Fuel_Quota_Management_System.service.FuelFillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fuel-filling")
public class FuelFillingController {

    @Autowired
    private FuelFillingService fuelFillingService;

    @GetMapping("/fuelFillingByStationId/{id}")
    public ResponseEntity<?> findFillingStationId (@PathVariable("id") Long id){
        List<FuelFilling> fuelFilling = fuelFillingService.findFillingStationId(id);
        return ResponseEntity.ok(fuelFilling);
    }
}
