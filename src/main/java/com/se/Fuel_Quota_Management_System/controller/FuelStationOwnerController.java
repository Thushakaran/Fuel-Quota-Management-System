package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.DTO.FuelStationOwnerLogDTO;
import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.service.FuelStationOwnerService;
import com.se.Fuel_Quota_Management_System.service.FuelStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/owner")
public class FuelStationOwnerController {
    @Autowired
    private FuelStationOwnerService fuelStationOwnerService;

    @Autowired
    private OwnerLogController ownerLogController;

    @Autowired
    private FuelStationService fuelStationService;


    // for register StationOwner
    @PostMapping("/register")
    public ResponseEntity<?> registerOwner(@Validated @RequestBody FuelStationOwnerLogDTO request) {
        try {
            FuelStationOwner registeredowner = fuelStationOwnerService.registerOwner(request);
            return ResponseEntity.ok(registeredowner.getId());
        } catch (Exception e) {
            // Log the error (use a logger in production)
            System.err.println("Error during registration: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }



    @GetMapping("/findname/{id}")
    public ResponseEntity<?> getOwnerById(@PathVariable Long id) {
        try {
            FuelStationOwner owner = fuelStationOwnerService.findFuelStationOwnerById(id);
            return ResponseEntity.ok(owner.getName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("finddetail/{id}")
    public ResponseEntity<?> getDetailsbyId(@PathVariable Long id){
        try {
            FuelStationOwner owner = fuelStationOwnerService.findFuelStationOwnerById(id);
            return ResponseEntity.ok(owner);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("findstations/{id}")
    public ResponseEntity<?> getStationsById(@PathVariable Long id){
        try {
            Optional<FuelStation> fuelStation = fuelStationService.findByOwnerId(id);
            return ResponseEntity.ok(fuelStation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("findbyid/{id}")
    public FuelStationOwner findFuelStationOwnerById(@PathVariable("id") Long Id){
        return fuelStationOwnerService.findFuelStationOwnerById(Id);
    }



    @GetMapping("search")
    public FuelStationOwner findOwnerByNicOrEmail(@RequestParam(value="nicNo",required = false) String nicNo,
                                                             @RequestParam(value= "email",required = false) String email){
        return fuelStationOwnerService.findAllByNicOrEmail(nicNo,email);

    }
}
