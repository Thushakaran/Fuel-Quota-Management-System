package com.se.Fuel_Quota_Management_System.service;


import com.se.Fuel_Quota_Management_System.DTO.FuelStationLogDTO;
import com.se.Fuel_Quota_Management_System.DTO.RegisterRequest;
import com.se.Fuel_Quota_Management_System.controller.AuthController;
import com.se.Fuel_Quota_Management_System.exception.CustomException;
import com.se.Fuel_Quota_Management_System.model.*;
import com.se.Fuel_Quota_Management_System.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    private RoleRepository roleRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AuthController authController;

    @Transactional
    public FuelStation registerFuelStation(FuelStationLogDTO request) throws Exception {
        // Check registration number in the CPST repository or already rgistered
        validateRegistrationNumber(request.getRegistrationNumber());

        // Check if username already exists
        validateUserName(request.getUserName());

        // Fetch the owner
        FuelStationOwner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new CustomException("Owner not found with ID: " + request.getOwnerId()));

        //check details are same
        checkDetails(request.getStationName(),request.getRegistrationNumber(),request.getLocation());

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

    private void checkDetails(String stationName, String registrationNumber, String location) {
        CPST_Stations cpstStation = cpstStationsRepository.findByRegistrationNumber(registrationNumber);
        if((!stationName.equalsIgnoreCase(cpstStation.getStationName())) ||
                !location.equalsIgnoreCase(cpstStation.getLocation())){
            throw new CustomException("Details do not match");
        }
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

    public FuelStation findFuelStationByStationLog(Long loginid) {
        return fuelStationRepository.findFuelStationOwnerByStationLogId(loginid);
    }

    public Map<String, Double> getFuelInventory(Long stationId) {
        Optional<FuelStation> fuelStation = fuelStationRepository.findById(stationId);
        if (fuelStation.isPresent()) {
            return fuelStation.get().getFuelInventory();
        }
        throw new CustomException("Fuel Inventory not Found");
    }

    public Optional<FuelStation> findFuelStationById(Long stationid) {
        return fuelStationRepository.findById(stationid);
    }

    public ResponseEntity<?> addFuels(Long id, Map<String, Double> fuelDetails) {
        try {
            //find fuel station by station ID
            FuelStation fuelStation = fuelStationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Fuel Station not found"));

            //validate the fuel quantity
            String fuelType = fuelDetails.keySet().iterator().next();
            Double quantityToAdd = fuelDetails.get(fuelType);

            if (quantityToAdd == null || quantityToAdd < 0) {
                return ResponseEntity.badRequest().body(Map.of("message", "Invalid quantity for fuel type: " + fuelType));
            }

            //update the fuel for the provided type
            Map<String, Double> inventory = fuelStation.getFuelInventory();
            if (inventory.containsKey(fuelType)) {
                //add the quantity
                Double currentQuantity = inventory.get(fuelType);
                inventory.put(fuelType, currentQuantity + quantityToAdd);
            } else {
                //if fuel type doesn't exist, return error
                return ResponseEntity.badRequest().body(Map.of("message", "Fuel type " + fuelType + " not found in inventory"));
            }

            //save the updated fuel station
            fuelStationRepository.save(fuelStation);

            //return
            return ResponseEntity.ok(Map.of("message", "Fuel added successfully"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }




    //to update Fuel Inventory
    @Transactional
    public void updateFuelInventory(Long stationId, double amount, String qrCodeId) {
        // Retrieve the vehicle entity by qrCodeId
        Vehicle vehicle = vehicleRepository.findByQrCodeId(qrCodeId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with QR Code ID: " + qrCodeId));

        String vehicleFuelType = vehicle.getFuelType();

        // Retrieve the fuel station entity by station ID
        FuelStation fuelStation = fuelStationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Fuel Station not found with ID: " + stationId));

        // Check if the fuelInventory map contains the vehicle's fuel type
        Map<String, Double> fuelInventory = fuelStation.getFuelInventory();
        if (!fuelInventory.containsKey(vehicleFuelType)) {
            throw new RuntimeException("Fuel type " + vehicleFuelType + " is not available at this station.");
        }

        // Get the available fuel for the specified fuel type
        double availableFuel = fuelInventory.get(vehicleFuelType);

        // Check if the station has enough fuel to pump
        if (availableFuel < amount) {
            throw new RuntimeException("Insufficient fuel available for type " + vehicleFuelType + ". Available: " + availableFuel);
        }

        // Deduct the pumped amount from the available fuel
        double remainingFuel = availableFuel - amount;
        fuelInventory.put(vehicleFuelType, remainingFuel);

        // Save the updated fuel station entity back to the repository
        fuelStation.setFuelInventory(fuelInventory);
        fuelStationRepository.save(fuelStation);
    }



    public FuelStation saveEditDetails(Long id, FuelStation fuelStation) {
        // Fetch the existing FuelStation entity from the database
        FuelStation existing = fuelStationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fuel Station with ID " + id + " not found"));

        // Update fields only if the new values are not null
        if (fuelStation.getStationName() != null) {
            existing.setStationName(fuelStation.getStationName());
        }
        if (fuelStation.getRegistrationNumber() != null) {
            existing.setRegistrationNumber(fuelStation.getRegistrationNumber());
        }
        if (fuelStation.getLocation() != null) {
            existing.setLocation(fuelStation.getLocation());
        }

        if (fuelStation.getFuelInventory() != null) {
            // Merge the existing fuelInventory with the new values
            Map<String, Double> updatedInventory = existing.getFuelInventory();
            updatedInventory.putAll(fuelStation.getFuelInventory()); // Overwrite existing or add new fuel types
            existing.setFuelInventory(updatedInventory);
        }

        // Save and return the updated FuelStation entity
        return fuelStationRepository.save(existing);
    }

}


