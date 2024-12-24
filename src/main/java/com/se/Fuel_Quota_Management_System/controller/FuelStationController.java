package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.service.FuelStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fuelstation")
public class FuelStationController {
    @Autowired
    private FuelStationService fuelStationService;

    //Registering the fuel Staion
    @PostMapping("/register")
    public ResponseEntity<FuelStation> registerFuelStation(@RequestBody  FuelStation fuelStation){
        FuelStation registerdFuelStation = fuelStationService.registerFuelStation(fuelStation);
        return  ResponseEntity.ok(registerdFuelStation);

    }

}
