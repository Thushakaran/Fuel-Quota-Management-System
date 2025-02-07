package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.DTO.logs.FuelStationOwnerLogDTO;
import com.se.Fuel_Quota_Management_System.DTO.auth.RegisterRequest;
import com.se.Fuel_Quota_Management_System.controller.AuthController;
import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.model.Role;
import com.se.Fuel_Quota_Management_System.model.UserLog;
import com.se.Fuel_Quota_Management_System.repository.FuelStationOwnerRepository;
import com.se.Fuel_Quota_Management_System.repository.RoleRepository;
import com.se.Fuel_Quota_Management_System.repository.UserLogRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuelStationOwnerService {

    private static final Logger logger = LoggerFactory.getLogger(FuelStationOwnerService.class);

    @Autowired
    private FuelStationOwnerRepository fuelStationOwnerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthController authController;

    @Autowired
    private UserLogRepository userLogRepository;

    @Transactional
    public FuelStationOwner registerOwner(FuelStationOwnerLogDTO fuelStationOwnerlog) {
        logger.info("Attempting to register Fuel Station Owner with username: {}", fuelStationOwnerlog.getUserName());

        // Validate input
        if (fuelStationOwnerRepository.existsByNicNo(fuelStationOwnerlog.getNicNo())) {
            logger.error("NIC number already registered: {}", fuelStationOwnerlog.getNicNo());
            throw new IllegalArgumentException("NIC number already registered");
        }
        if (userLogRepository.existsByUserName(fuelStationOwnerlog.getUserName())) {
            logger.error("Username already exists: {}", fuelStationOwnerlog.getUserName());
            throw new IllegalArgumentException("Username already exists");
        }

        // Fetch role
        Role role = roleRepository.findByName("STATIONOWNER")
                .orElseThrow(() -> {
                    logger.error("Invalid role name for Fuel Station Owner");
                    return new IllegalArgumentException("Invalid role name");
                });

        // Register user
        RegisterRequest ownerLog = new RegisterRequest();
        ownerLog.setUserName(fuelStationOwnerlog.getUserName());
        ownerLog.setPassword(fuelStationOwnerlog.getPassword());
        ownerLog.setRole(role.getName());

        ResponseEntity<?> registerResponse = authController.register(ownerLog);
        if (!registerResponse.getStatusCode().is2xxSuccessful()) {
            logger.error("Error during registration: {}", registerResponse.getBody());
            throw new RuntimeException("Error during registration: " + registerResponse.getBody());
        }

        // Create and save FuelStationOwner
        FuelStationOwner owner = new FuelStationOwner();
        owner.setName(fuelStationOwnerlog.getOwnerName());
        owner.setNicNo(fuelStationOwnerlog.getNicNo());
        owner.setPhoneNumber(fuelStationOwnerlog.getPhoneNumber());
        owner.setEmail(fuelStationOwnerlog.getEmail());
        owner.setAddress(fuelStationOwnerlog.getAddress());
        owner.setOwnerLog((UserLog) registerResponse.getBody());

        FuelStationOwner savedOwner = fuelStationOwnerRepository.save(owner);
        logger.info("Fuel Station Owner registered successfully with ID: {}", savedOwner.getId());

        return savedOwner;
    }

    public Optional<FuelStationOwner> findFuelStationOwnerById(Long id) {
        logger.info("Fetching Fuel Station Owner with ID: {}", id);
        return fuelStationOwnerRepository.findFuelStationOwnerById(id);
    }

    public FuelStationOwner findFuelStationOwnerByOwnerLog(Long loginId) {
        logger.info("Fetching Fuel Station Owner by Owner Log ID: {}", loginId);
        return fuelStationOwnerRepository.findFuelStationOwnerByOwnerLogId(loginId);
    }

    public void saveEditDetails(Long id, FuelStationOwner fuelStationOwner) {
        logger.info("Attempting to update Fuel Station Owner with ID: {}", id);

        FuelStationOwner existingOwner = fuelStationOwnerRepository
                .findById(id)
                .orElseThrow(() -> {
                    logger.error("Owner not found with ID: {}", id);
                    return new IllegalArgumentException("Owner not found");
                });

        // Update only the provided fields
        if (fuelStationOwner.getName() != null) {
            existingOwner.setName(fuelStationOwner.getName());
        }
        if (fuelStationOwner.getEmail() != null) {
            existingOwner.setEmail(fuelStationOwner.getEmail());
        }
        if (fuelStationOwner.getPhoneNumber() != null) {
            existingOwner.setPhoneNumber(fuelStationOwner.getPhoneNumber());
        }
        if (fuelStationOwner.getAddress() != null) {
            existingOwner.setAddress(fuelStationOwner.getAddress());
        }

        FuelStationOwner updatedOwner = fuelStationOwnerRepository.save(existingOwner);
        logger.info("Fuel Station Owner updated successfully with ID: {}", updatedOwner.getId());

    }
}
