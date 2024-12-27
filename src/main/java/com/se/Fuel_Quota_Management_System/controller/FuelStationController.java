package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.model.CPST_Stations;
import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.repository.CPST_StationsRepository;
import com.se.Fuel_Quota_Management_System.service.FuelStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/fuel-station")
public class FuelStationController {
    @Autowired
    private FuelStationService fuelStationService;

    @Autowired
    private CPST_StationsRepository cpstStationsRepository;

    //Registering the fuelStaion
    // by checking It already registerd or not
    // by checking It is on ourDatabase
    @PostMapping("/register")
    public String registerFuelStation(@RequestBody FuelStation fuelStation) {
        //checking it alredy registerd or not
        if (findByRegisNumById(fuelStation.getRegistrationNumber())) {
            return "This Registration Number: " + fuelStation.getRegistrationNumber() + " is already registered";
        } else {
            //checking it on database
            if(cpstStationsRepository.findByRegistrationNumber(fuelStation.getRegistrationNumber())){
                FuelStation registeredFuelStation = fuelStationService.registerFuelStation(fuelStation);
                return "Successfully Registered";
            }else {
                return "Check Your Registraion Number";
            }

        }
    }

    // Find is any Fuelstation registered on this RegisterdNumber
    @GetMapping("{regnum}")
    public boolean findByRegisNumById(@PathVariable("regnum") String registrationNumber) {
        Optional<FuelStation> fuelStation = fuelStationService.findByRegistrationNumber(registrationNumber);
        return fuelStation.isPresent();
    }

    // for admin get all the registered fuelstations
    @GetMapping
    public List<FuelStation> findAllFuelStations(){
        return fuelStationService.findAllFuelStations();
    }

    // for admin delete registered fuelstations
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> deleteFuelStation(@PathVariable("id") Long Id){
        fuelStationService.deleteById(Id);
        return ResponseEntity.ok(true);
    }



}
