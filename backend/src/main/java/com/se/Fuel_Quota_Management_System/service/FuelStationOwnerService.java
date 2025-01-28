package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.DTO.FuelStationOwnerLogDTO;
import com.se.Fuel_Quota_Management_System.controller.AuthController;
import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.DTO.RegisterRequest;
import com.se.Fuel_Quota_Management_System.model.Role;
import com.se.Fuel_Quota_Management_System.model.UserLog;
import com.se.Fuel_Quota_Management_System.repository.FuelStationOwnerRepository;
import com.se.Fuel_Quota_Management_System.repository.RoleRepository;
import com.se.Fuel_Quota_Management_System.repository.UserLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class FuelStationOwnerService {
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
        try {
            // Check if NIC number already exists
            if (fuelStationOwnerRepository.existsByNicNo(fuelStationOwnerlog.getNicNo())) {
                throw new IllegalArgumentException("NIC number already registered");
            }

            // Check if username already exists
            if (userLogRepository.existsByUserName(fuelStationOwnerlog.getUserName())) {
                throw new IllegalArgumentException("Username already exists");
            }

            // Create and validate OwnerLog registration request
            Role role = roleRepository.findByName("stationowner")
                    .orElseThrow(() -> new IllegalArgumentException("Invalid role name"));

            RegisterRequest ownerLog = new RegisterRequest();
            ownerLog.setUserName(fuelStationOwnerlog.getUserName());
            ownerLog.setPassword(fuelStationOwnerlog.getPassword());
            ownerLog.setRole(role.getName());

            // Register the OwnerLog using the auth controller's register method
            ResponseEntity<?> registerResponse = authController.register(ownerLog);
            if (!registerResponse.getStatusCode().is2xxSuccessful()) {
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

            return fuelStationOwnerRepository.save(owner);

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }



    public Optional<FuelStationOwner>findFuelStationOwnerById(Long Id) {
        return fuelStationOwnerRepository.findFuelStationOwnerById(Id);
    }


    public FuelStationOwner findFuelStationOwnerByOwnerLog(Long loginid) {
        return fuelStationOwnerRepository.findFuelStationOwnerByOwnerLogId(loginid);
    }

    public FuelStationOwner saveEditDetails(Long id, FuelStationOwner fuelStationOwner) {
        FuelStationOwner existingOwner = fuelStationOwnerRepository
                .findById(id).orElseThrow();
        // Update only the provided fields
        if(fuelStationOwner.getName() != null){
            existingOwner.setName(fuelStationOwner.getName());
        }
        if(fuelStationOwner.getEmail() != null) {
            existingOwner.setEmail(fuelStationOwner.getEmail());
        }
        if(fuelStationOwner.getPhoneNumber() != null) {
            existingOwner.setPhoneNumber(fuelStationOwner.getPhoneNumber());
        }
        if(fuelStationOwner.getAddress() != null){
            existingOwner.setAddress(fuelStationOwner.getAddress());
        }

        // Save the updated owner
        return fuelStationOwnerRepository.save(existingOwner);
    }
}
