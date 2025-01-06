package com.se.Fuel_Quota_Management_System.controller;


import com.se.Fuel_Quota_Management_System.DTO.FuelStationLogDTO;
import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.model.StationLog;
import com.se.Fuel_Quota_Management_System.service.FuelStationService;
import com.se.Fuel_Quota_Management_System.service.StationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/fuel-station")
public class FuelStationController {
    @Autowired
    private FuelStationService fuelStationService;

    @Autowired
    private StationLogService stationLogService;

    //Registering the fuelStaion
    @PostMapping("/register")
    public ResponseEntity<?> registerFuelStation(@RequestBody FuelStationLogDTO dto) {
        try {
            // Create StationLog
            StationLog stationLog = new StationLog();
            stationLog.setStationUserName(dto.getStationUserName());
            stationLog.setPassword(dto.getPassword());

            // Save StationLog
            StationLog registeredLog = stationLogService.register(stationLog);

            // Create Station
            FuelStation station = new FuelStation();
            station.setStationName(dto.getStationName());
            station.setLocation(dto.getLocation());
            station.setRegistrationNumber(dto.getRegistrationNumber());

            station.setStationLog(registeredLog);// Link StationLog

            // Save FuelStation
            fuelStationService.registerFuelStation(station);

            return ResponseEntity.ok(station);
        } catch (DataIntegrityViolationException e) {
            // make sure that user name in unique
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");
        } catch (Exception e) {
            // if any error occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed!");
        }


    }




    // Find is any Fuelstation registered on this RegisterdNumber
    @GetMapping("{regnum}")
    public boolean existsByRegisNumById(@PathVariable("regnum") String registrationNumber) {
        return fuelStationService.existsByRegistrationNumber(registrationNumber);

    }




}
