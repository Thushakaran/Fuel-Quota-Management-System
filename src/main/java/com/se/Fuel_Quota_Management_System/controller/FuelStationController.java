package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.service.FuelStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fuel-station")
public class FuelStationController {
    @Autowired
    private FuelStationService fuelStationService;

    //Registering the fuel Staion by checking it alredy registerd or not
    @PostMapping("/register")
    public String registerFuelStation(@RequestBody FuelStation fuelStation) {
        if (findByRegisNumById_Registering(fuelStation.getRegistrationNumber())) {
            return "This Registration Number: " + fuelStation.getRegistrationNumber() + " is already registered";
        } else {
            FuelStation registeredFuelStation = fuelStationService.registerFuelStation(fuelStation);
            return "Successfully Registered";
        }
    }

    @GetMapping("{regnum}")
    public boolean findByRegisNumById_Registering(@PathVariable("regnum") String registrationNumber) {
        Optional<FuelStation> fuelStation = fuelStationService.findByRegistrationNumber(registrationNumber);
        return fuelStation.isPresent();
    }

    // for admin get all the refistered fuelstations
    @GetMapping
    public List<FuelStation> findAllFuelStations(){
        return fuelStationService.findAllFuelStations();
    }

    // for admin delete refistered fuelstations
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> deleteFuelStation(@PathVariable("id") Long Id){
        fuelStationService.deleteById(Id);
        return ResponseEntity.ok(true);
    }

}
