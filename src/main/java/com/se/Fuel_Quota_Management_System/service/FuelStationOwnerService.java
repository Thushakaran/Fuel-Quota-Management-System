package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.DTO.FuelStationOwnerLogDTO;
import com.se.Fuel_Quota_Management_System.controller.AuthController;
import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.model.RegisterRequest;
import com.se.Fuel_Quota_Management_System.model.Role;
import com.se.Fuel_Quota_Management_System.model.UserLog;
import com.se.Fuel_Quota_Management_System.repository.FuelStationOwnerRepository;
import com.se.Fuel_Quota_Management_System.repository.RoleRepository;
import com.se.Fuel_Quota_Management_System.repository.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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


    @PostMapping("/registerOwner")
    public ResponseEntity<?> registerOwner(@RequestBody FuelStationOwnerLogDTO fuelStationOwnerlog) {
        try {
            // Check if NIC number already exists
            if (fuelStationOwnerRepository.existsByNicNo(fuelStationOwnerlog.getNicNo())) {
                return ResponseEntity.badRequest().body(Map.of("message", "NIC number already registered"));
            }

            // Check if username already exists
            if (userLogRepository.existsByUserName(fuelStationOwnerlog.getUserName())) {
                return ResponseEntity.badRequest().body(Map.of("message", "Username already exists"));
            }

            // Create and validate OwnerLog registration request
            Optional<Role> roleOptional = roleRepository.findByName("stationowner");
            if (roleOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Invalid role name"));
            }

            RegisterRequest ownerLog = new RegisterRequest();
            ownerLog.setUserName(fuelStationOwnerlog.getUserName());
            ownerLog.setPassword(fuelStationOwnerlog.getPassword());
            ownerLog.setRole(roleOptional.get().getName());

            // Register the OwnerLog using the auth controller's register method
            ResponseEntity<?> registerResponse = authController.register(ownerLog);
            if (!registerResponse.getStatusCode().is2xxSuccessful()) {
                return registerResponse; // Forward error response from the register method
            }

            UserLog registeredLog = (UserLog) registerResponse.getBody();

            // Create and save FuelStationOwner
            FuelStationOwner owner = new FuelStationOwner();
            owner.setName(fuelStationOwnerlog.getOwnerName());
            owner.setNicNo(fuelStationOwnerlog.getNicNo());
            owner.setPhoneNumber(fuelStationOwnerlog.getPhoneNumber());
            owner.setEmail(fuelStationOwnerlog.getEmail());
            owner.setOwnerLog(registeredLog);

            fuelStationOwnerRepository.save(owner);

            return ResponseEntity.ok(Map.of("message", "Owner registered successfully", "owner", owner));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "An error occurred", "error", e.getMessage()));
        }
    }


    public FuelStationOwner findAllByNicOrEmail(String nicNo, String email) {
        return fuelStationOwnerRepository.findByNicNoOrEmail(nicNo,email);
    }


    public FuelStationOwner findFuelStationOwnerById(Long Id) {
        return fuelStationOwnerRepository.findFuelStationOwnerById(Id);
    }


}
