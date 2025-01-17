package com.se.Fuel_Quota_Management_System.service;


import com.se.Fuel_Quota_Management_System.DTO.FuelStationLogDTO;
import com.se.Fuel_Quota_Management_System.controller.AuthController;
import com.se.Fuel_Quota_Management_System.exception.CustomException;
import com.se.Fuel_Quota_Management_System.model.*;
import com.se.Fuel_Quota_Management_System.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FuelStationService {
    @Autowired
    private FuelStationRepository fuelStationRepository;
    @Autowired
    private CPST_StationsRepository cpstStationsRepository;

    @Autowired
    private UserLogRepository userLogRepository;

    @Autowired
    private FuelStationOwnerRepository ownerRepository;

    @Autowired
    private AuthController authController;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public FuelStation registerFuelStation(FuelStationLogDTO request) throws Exception {
        // Check registration number in the CPST repository or already rgistered
        validateRegistrationNumber(request.getRegistrationNumber());

        // Check if username already exists
        validateUserName(request.getUserName());

        // Fetch the owner
        FuelStationOwner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new CustomException("Owner not found with ID: " + request.getOwnerId()));

        // Create FuelStation entity
        FuelStation fuelStation = new FuelStation();
        fuelStation.setStationName(request.getStationName());
        fuelStation.setRegistrationNumber(request.getRegistrationNumber());
        fuelStation.setLocation(request.getLocation());
        fuelStation.setOwner(owner);
        fuelStation.setFuelInventory(request.getFuelTypes()); // Assuming getFuelTypes() returns Map<String, Double>

        // Create and validate StationLog registration request
        Optional<Role> roleOptional = roleRepository.findByName("station");
        if (roleOptional.isEmpty()) {
            throw new CustomException("Invalid role name: station");
        }

        RegisterRequest stationLog = new RegisterRequest();
        stationLog.setUserName(request.getUserName());
        stationLog.setPassword(request.getPassword());
        stationLog.setRole(roleOptional.get().getName());

        ResponseEntity<?> registerResponse = authController.register(stationLog);
        if (!registerResponse.getStatusCode().is2xxSuccessful()) {
            throw new CustomException("Failed to register station log: " + registerResponse.getBody());
        }

        UserLog registeredLog = (UserLog) registerResponse.getBody();
        fuelStation.setStationLog(registeredLog);
        FuelStation registeredfuelStation = fuelStationRepository.save(fuelStation);

        // Save to database
        return registeredfuelStation;
    }

    private void validateRegistrationNumber(String registrationNumber) {
        if (!cpstStationsRepository.existsByRegistrationNumber(registrationNumber)) {
            throw new CustomException("Registration number does not exist in the CPST repository");
        }
        if (fuelStationRepository.existsByRegistrationNumber(registrationNumber)) {
            throw new CustomException("Registration number is already registered");
        }
    }

    private void validateUserName(String userName) {
        if (userLogRepository.existsByUserName(userName)) {
            throw new CustomException("Username already exists");
        }
    }
    public boolean existsByRegistrationNumber(String registrationNumber) {
        return fuelStationRepository.existsByRegistrationNumber(registrationNumber);
    }

    public List<FuelStation> getByOwnerId(Long id) {
        return fuelStationRepository.getByOwnerId(id);
    }
}







