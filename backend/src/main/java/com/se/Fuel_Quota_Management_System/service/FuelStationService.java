package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.DTO.logs.FuelStationLogDTO;
import com.se.Fuel_Quota_Management_System.DTO.auth.RegisterRequest;
import com.se.Fuel_Quota_Management_System.controller.AuthController;
import com.se.Fuel_Quota_Management_System.exception.CustomException;
import com.se.Fuel_Quota_Management_System.model.*;
import com.se.Fuel_Quota_Management_System.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FuelStationService {

    private static final Logger logger = LoggerFactory.getLogger(FuelStationService.class);

    @Autowired
    private FuelStationRepository fuelStationRepository;

    @Autowired
    private PreRegisteredStationRepository preregisteredStationsRepository;

    @Autowired
    private UserLogRepository userLogRepository;

    @Autowired
    private FuelStationOwnerRepository ownerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private FuelFillingRepository fuelFillingRepository;

    @Autowired
    private AuthController authController;

    @Transactional
    public FuelStation registerFuelStation(FuelStationLogDTO request) throws CustomException {
        logger.info("Attempting to register Fuel Station with registration number: {}", request.getRegistrationNumber());
        // Check registration number in the CPST repository or already
        validateRegistrationNumber(request.getRegistrationNumber());

        // Check if username already exists
        validateUserName(request.getUserName());

        // Fetch the owner
        FuelStationOwner owner = getOwner(request.getOwnerId());

        //check details are same
        validateStationDetails(request);

        // Create FuelStation entity
        FuelStation fuelStation = buildFuelStation(request, owner);
        UserLog registeredLog = registerStationLog(request.getUserName(), request.getPassword());

        // Create and validate StationLog registration request
        fuelStation.setStationLog(registeredLog);
        FuelStation savedFuelStation = fuelStationRepository.save(fuelStation);

        logger.info("Fuel Station registered successfully with ID: {}", savedFuelStation.getId());
        return savedFuelStation;
    }

    // check owner already registered or not
    private FuelStationOwner getOwner(Long ownerId) throws CustomException {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new CustomException("Owner not found with ID: " + ownerId));
    }

    //check detail match with our database
    private void validateStationDetails(FuelStationLogDTO request) throws CustomException {
        PreRegisteredStation cpstStation = preregisteredStationsRepository.findByRegistrationNumber(request.getRegistrationNumber());
        if (!request.getStationName().equalsIgnoreCase(cpstStation.getStationName()) ||
                !request.getLocation().equalsIgnoreCase(cpstStation.getLocation())) {
            throw new CustomException("Details do not match with CPST station");
        }
    }

    private FuelStation buildFuelStation(FuelStationLogDTO request, FuelStationOwner owner) {
        FuelStation fuelStation = new FuelStation();
        fuelStation.setStationName(request.getStationName());
        fuelStation.setRegistrationNumber(request.getRegistrationNumber());
        fuelStation.setLocation(request.getLocation());
        fuelStation.setActive(true);
        fuelStation.setOwner(owner);
        fuelStation.setFuelInventory(request.getFuelTypes());
        return fuelStation;
    }

    private UserLog registerStationLog(String userName, String password) throws CustomException {
        Role role = roleRepository.findByName("STATION")
                .orElseThrow(() -> new CustomException("Invalid role name: station"));

        RegisterRequest stationLog = new RegisterRequest();
        stationLog.setUserName(userName);
        stationLog.setPassword(password);
        stationLog.setRole(role.getName());

        ResponseEntity<?> registerResponse = authController.register(stationLog);
        if (!registerResponse.getStatusCode().is2xxSuccessful()) {
            logger.error("Failed to register station log: {}", registerResponse.getBody());
            throw new CustomException("Failed to register station log: " + registerResponse.getBody());
        }

        return (UserLog) registerResponse.getBody();
    }

    // check fuelstation registration number
    private void validateRegistrationNumber(String registrationNumber) throws CustomException {
        if (!preregisteredStationsRepository.existsByRegistrationNumber(registrationNumber)) {
            throw new CustomException("Registration number does not exist in the CPST repository");
        }
        if (fuelStationRepository.existsByRegistrationNumber(registrationNumber)) {
            throw new CustomException("Registration number is already registered");
        }
    }

    private void validateUserName(String userName) throws CustomException {
        if (userLogRepository.existsByUserName(userName)) {
            throw new CustomException("Username already exists");
        }
    }

    public boolean existsByRegistrationNumber(String registrationNumber) {
        return fuelStationRepository.existsByRegistrationNumber(registrationNumber);
    }

    @Transactional
    public List<FuelStation> getByOwnerId(Long id) {
        return fuelStationRepository.getByOwnerId(id);
    }

    public FuelStation findFuelStationByStationLog(Long loginId) {
        return fuelStationRepository.findFuelStationOwnerByStationLogId(loginId);
    }

    @Transactional
    public Map<String, Double> getFuelInventory(Long stationId) throws CustomException {
        return fuelStationRepository.findByIdWithInventory(stationId)
                .map(FuelStation::getFuelInventory) // Extract fuelInventory
                .orElseThrow(() -> new CustomException("Fuel Inventory not Found"));
    }


    public Optional<FuelStation> findFuelStationById(Long stationId) {
        return fuelStationRepository.findById(stationId);
    }

    @Transactional
    public ResponseEntity<?> addFuels(Long id, Map<String, Double> fuelDetails) throws CustomException {
        FuelStation fuelStation = fuelStationRepository.findById(id)
                .orElseThrow(() -> new CustomException("Fuel Station not found"));

        if (!fuelStation.isActive()) {
            throw new CustomException("Station is Not Active");
        }

        String fuelType = getFuelTypeFromDetails(fuelDetails);

        if (!fuelDetails.containsKey(fuelType) || fuelDetails.get(fuelType) == null) {
            throw new CustomException("Fuel quantity is missing or invalid.");
        }

        Double quantityToAdd = fuelDetails.get(fuelType);
        validateFuelQuantity(fuelType, quantityToAdd);

        // Ensure inventory is initialized
        Map<String, Double> inventory = fuelStation.getFuelInventory();
        if (inventory == null) {
            inventory = new HashMap<>();
            fuelStation.setFuelInventory(inventory);
        }

        // Ensure fuel type exists in inventory
        inventory.put(fuelType, inventory.getOrDefault(fuelType, 0.0) + quantityToAdd);

        // Save updated station inventory
        fuelStationRepository.save(fuelStation);

        // Save fuel filling details directly
        fuelFillingRepository.save(saveFuelFillingDetails(fuelStation, fuelType, quantityToAdd));

        return ResponseEntity.ok(Map.of(
                "message", "Fuel added successfully",
                "updatedInventory", inventory
        ));
    }

    public FuelFilling saveFuelFillingDetails(FuelStation fuelStation, String fuelType, Double quantityToAdd) {
        return new FuelFilling(
                fuelStation,
                fuelType,
                quantityToAdd,
                LocalDateTime.now()
        );
    }


    private String getFuelTypeFromDetails(Map<String, Double> fuelDetails) {
        return fuelDetails.keySet().iterator().next();
    }

    private void validateFuelQuantity(String fuelType, Double quantityToAdd) throws CustomException {
        if (quantityToAdd == null || quantityToAdd < 0) {
            throw new CustomException("Invalid quantity for fuel type: " + fuelType);
        }
    }

    @Transactional
    public void updateFuelInventory(Long stationId, double amount, String qrCodeId) throws CustomException {
        // Retrieve the vehicle entity by qrCodeId
        Vehicle vehicle = vehicleRepository.findByQrCodeId(qrCodeId)
                .orElseThrow(() -> new CustomException("Vehicle not found with QR Code ID: " + qrCodeId));

        // Retrieve the fuel station entity by station ID
        FuelStation fuelStation = fuelStationRepository.findById(stationId)
                .orElseThrow(() -> new CustomException("Fuel Station not found with ID: " + stationId));

        // Check if the fuelInventory map contains the vehicle's fuel type
        Map<String, Double> fuelInventory = fuelStation.getFuelInventory();
        String vehicleFuelType = vehicle.getFuelType();

        if (!fuelInventory.containsKey(vehicleFuelType)) {
            throw new CustomException("Fuel type " + vehicleFuelType + " is not available at this station.");
        }

        // Get the available fuel for the specified fuel type
        double availableFuel = fuelInventory.get(vehicleFuelType);

        // Check if the station has enough fuel to pump
        if (availableFuel < amount) {
            throw new CustomException("Insufficient fuel available for type " + vehicleFuelType);
        }

        // Deduct the pumped amount from the available fuel
        fuelInventory.put(vehicleFuelType, availableFuel - amount);
        fuelStationRepository.save(fuelStation);
    }

    @Transactional
    public void saveEditDetails(Long id, FuelStation fuelStation) throws CustomException {
        FuelStation existing = fuelStationRepository.findById(id)
                .orElseThrow(() -> new CustomException("Fuel Station with ID " + id + " not found"));

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
            existing.getFuelInventory().putAll(fuelStation.getFuelInventory());
        }

        FuelStation updatedFuelStation = fuelStationRepository.save(existing);
        logger.info("Fuel Station updated successfully with ID: {}", updatedFuelStation.getId());
    }

}
