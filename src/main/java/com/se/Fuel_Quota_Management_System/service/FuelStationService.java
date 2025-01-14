package com.se.Fuel_Quota_Management_System.service;


import com.se.Fuel_Quota_Management_System.DTO.FuelStationLogDTO;
import com.se.Fuel_Quota_Management_System.controller.FuelStationController;
import com.se.Fuel_Quota_Management_System.controller.StationLogController;
import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.model.StationLog;

import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.repository.CPST_StationsRepository;
import com.se.Fuel_Quota_Management_System.repository.FuelStationOwnerRepository;
import com.se.Fuel_Quota_Management_System.repository.FuelStationRepository;
import com.se.Fuel_Quota_Management_System.repository.StationLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class FuelStationService {
    @Autowired
    private FuelStationRepository fuelStationRepository;
    @Autowired
    private CPST_StationsRepository cpstStationsRepository;

    @Autowired
    private StationLogRepository stationLogRepository;

    @Autowired
    private FuelStationOwnerRepository ownerRepository;
    @Autowired
    private StationLogController stationLogController;


    @Transactional
    public FuelStation registerFuelStation(FuelStationLogDTO request) throws Exception {
        System.out.println(request.getRegistrationNumber());
        if(!cpstStationsRepository.existsByRegistrationNumber(request.getRegistrationNumber())){
            throw new Exception("Registration number is not exists at CPST repository");
        }
        // Check if registration number already exists
        if (fuelStationRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new Exception("Registration number already exists");
        }

        // Check if username already exists
        if (stationLogRepository.existsByStationUserName(request.getStationUserName())) {
            throw new Exception("Username already exists");
        }

        // Fetch the owner
        FuelStationOwner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new Exception("Owner not found with ID: " + request.getOwnerId()));

        // Create FuelStation entity
        FuelStation fuelStation = new FuelStation();
        fuelStation.setStationName(request.getStationName());
        fuelStation.setRegistrationNumber(request.getRegistrationNumber());
        fuelStation.setLocation(request.getLocation());
        fuelStation.setOwner(owner);
        fuelStation.setFuelInventory((Map<String, Double>) request.getFuelTypes());

        StationLog stationLog = new StationLog();
        stationLog.setStationUserName(request.getStationUserName());
        stationLog.setPassword(request.getPassword());

        StationLog registeredLog =stationLogController.signup(stationLog);

        fuelStation.setStationLog(registeredLog);
        // Save to database

        FuelStationController fuelStationController = new FuelStationController();

        return fuelStationRepository.save(fuelStation);

    }


//    public ResponseEntity<?> registerFuelStation(FuelStation fuelStation) throws ConfigDataResourceNotFoundException {
//
//        // Check if fuel station is already registered using the registration number
//        if (fuelStationRepository.existsByRegistrationNumber(fuelStation.getRegistrationNumber())) {
//            return new ResponseEntity<>("Fuel station with this registration number is already registered.", HttpStatus.CONFLICT);
//        } else {
//            // Check if the registration number exists in repository (cpstStationsRepository)
//            if (cpstStationsRepository.existsByRegistrationNumber(fuelStation.getRegistrationNumber())) {
//                FuelStation registeredStation = fuelStationRepository.save(fuelStation);
//                return new ResponseEntity<>(registeredStation, HttpStatus.CREATED);
//            } else {
//                return new ResponseEntity<>("Fuel station with this registration number is not in DMT File.", HttpStatus.CONFLICT);
//
//
//            }
//        }
//    }


    public boolean existsByRegistrationNumber(String registrationNumber) {
        return fuelStationRepository.existsByRegistrationNumber(registrationNumber);
    }

    public Optional<FuelStation> findByOwnerId(Long id) {
        return fuelStationRepository.findByOwnerId(id);
    }
}







