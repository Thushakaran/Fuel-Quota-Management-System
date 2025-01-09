package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.DTO.FuelStationOwnerLogDTO;
import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.model.OwnerLog;
import com.se.Fuel_Quota_Management_System.repository.CPST_StationsRepository;
import com.se.Fuel_Quota_Management_System.service.FuelStationOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/owner")
public class FuelStationOwnerController {
    @Autowired
    private FuelStationOwnerService fuelStationOwnerService;

    @Autowired
    private OwnerLogController ownerLogController;

    @Autowired
    private CPST_StationsRepository cpstStationsRepository;

    // for register StationOwner
    @PostMapping("/register")
    public ResponseEntity<?> registerOwner(@RequestBody FuelStationOwnerLogDTO dto) {
        try {
            // Create OwnerLog
            OwnerLog ownerLog = new OwnerLog();
            ownerLog.setOwnerUserName(dto.getOwnerUserName());
            ownerLog.setPassword(dto.getPassword());

            // Save OwnerLog
            OwnerLog registeredLog = ownerLogController.signup(ownerLog);

            // Create FuelStationOwner
            FuelStationOwner owner = new FuelStationOwner();
            owner.setName(dto.getOwnerName());
            owner.setNicNo(dto.getNicNo());
            owner.setPhoneNumber(dto.getPhoneNumber());
            owner.setEmail(dto.getEmail());

            owner.setOwnerLog(registeredLog); // Link OwnerLog

            // Save FuelStationOwner
            fuelStationOwnerService.registerOwner(owner);

            return ResponseEntity.ok(owner);
        } catch (DataIntegrityViolationException e) {
            // make sure that username in unique
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed!");
        }
    }

    @GetMapping("findbyid/{id}")
    public FuelStationOwner findFuelStationOwnerById(@PathVariable Long Id){
        return fuelStationOwnerService.findFuelStationOwnerById(Id);
    }

    @GetMapping("search")
    public FuelStationOwner findOwnerByNicOrEmail(@RequestParam(value="nicNo",required = false) String nicNo,
                                                             @RequestParam(value= "email",required = false) String email){
        return fuelStationOwnerService.findAllByNicOrEmail(nicNo,email);

    }
}
