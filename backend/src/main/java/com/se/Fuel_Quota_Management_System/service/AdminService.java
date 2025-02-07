package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.DTO.DashboardData;
import com.se.Fuel_Quota_Management_System.DTO.auth.RegisterRequest;
import com.se.Fuel_Quota_Management_System.controller.AuthController;
import com.se.Fuel_Quota_Management_System.exception.ResourceNotFoundException;
import com.se.Fuel_Quota_Management_System.exception.VehicleNotFoundException;
import com.se.Fuel_Quota_Management_System.exception.FuelStationNotFoundException;
import com.se.Fuel_Quota_Management_System.model.*;
import com.se.Fuel_Quota_Management_System.repository.*;
import com.se.Fuel_Quota_Management_System.repository.FuelTransactionRepository;
import com.se.Fuel_Quota_Management_System.repository.VehicleRepository;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private FuelStationRepository fuelStationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthController authController;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserLogRepository userLogRepository;

    @Autowired
    private FuelTransactionRepository fuelTransactionRepository;

/** ------------------------Vehicle Related ------------------*/
    public List<Vehicle> getAllVehicles() {
        logger.info("Fetching all vehicles.");
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        logger.info("Fetching vehicle with ID: {}", id);
        return vehicleRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Vehicle not found with id: {}", id);
                    return new RuntimeException("Vehicle not found with id: " + id);
                });
    }

    public List<Vehicle> getVehiclesByType(String vehicleType) {
        logger.info("Fetching vehicles by type: {}", vehicleType);
        List<Vehicle> vehicles = vehicleRepository.findByVehicleType(vehicleType);
        if (vehicles.isEmpty()) {
            logger.error("No vehicles found with Type: {}", vehicleType);
            throw new RuntimeException("Vehicle not found with Type: " + vehicleType);
        }
        return vehicles;
    }

    public Optional<Vehicle> getVehiclesByOwner(String ownerName) {
        logger.info("Fetching vehicles by owner: {}", ownerName);
        return vehicleRepository.findByOwnerName(ownerName);
    }

    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {
        logger.info("Updating vehicle with ID: {}", id);
        Vehicle existingVehicle = getVehicleById(id);

        if (existingVehicle == null) {
            logger.error("Vehicle not found with id: {}", id);
            throw new ResourceNotFoundException("Vehicle not found with id " + id);
        }

        existingVehicle.setVehicleNumber(updatedVehicle.getVehicleNumber());
        existingVehicle.setOwnerName(updatedVehicle.getOwnerName());
        existingVehicle.setVehicleType(updatedVehicle.getVehicleType());
        existingVehicle.setFuelType(updatedVehicle.getFuelType());
        existingVehicle.setFuelQuota(updatedVehicle.getFuelQuota());
        existingVehicle.setChassisNumber(updatedVehicle.getChassisNumber());

        logger.info("Vehicle updated successfully: {}", existingVehicle);
        return vehicleRepository.save(existingVehicle);
    }

    public Vehicle vehicleActiveStatus(Long vehicleId) {
        Vehicle vehicle = getVehicleById(vehicleId);
        vehicle.setActive(!vehicle.isActive());
        return vehicleRepository.save(vehicle); // Save updated status
    }

    public void deleteVehicle(Long id) {
        logger.info("Attempting to delete vehicle with ID: {}", id);
        if (!vehicleRepository.existsById(id)) {
            logger.error("Vehicle with ID: {} does not exist.", id);
            throw new VehicleNotFoundException("Vehicle with ID " + id + " does not exist.");
        }

        vehicleRepository.deleteById(id);
        logger.info("Vehicle with ID: {} deleted successfully.", id);
    }

/** ------------------------ fuelStation Related ----------------*/

    public List<FuelStation> getAllFuelStation() {
        logger.info("Fetching all fuel stations.");
        return fuelStationRepository.findAll();
    }

    public FuelStation getFuelStationById(Long id) {
        logger.info("Fetching fuel station with ID: {}", id);
        return fuelStationRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Fuel station not found with ID: {}", id);
                    return new RuntimeException("Fuel station not found with id: " + id);
                });
    }

    public FuelStation getFuelStationByOwnerId(Long owner_id) {
        logger.info("Searching for fuel station with owner ID: {}", owner_id);
        return fuelStationRepository.findByOwnerId(owner_id)
                .orElseThrow(() -> {
                    logger.error("Fuel station not found with owner ID: {}", owner_id);
                    return new RuntimeException("Fuel station not found with owner ID: " + owner_id);
                });
    }

    public FuelStation getFuelStationByLocation(String location) {
        logger.info("Fetching fuel station by location: {}", location);
        return fuelStationRepository.findByLocation(location)
                .orElseThrow(() -> {
                    logger.error("Fuel station not found with location: {}", location);
                    return new RuntimeException("Fuel station not found with location: " + location);
                });
    }

    public FuelStation getFuelStationByStationName(String station_name) {
        logger.info("Fetching fuel station by station name: {}", station_name);
        return fuelStationRepository.findByStationName(station_name)
                .orElseThrow(() -> {
                    logger.error("Fuel station not found with station name: {}", station_name);
                    return new RuntimeException("Fuel station not found with station_name: " + station_name);
                });
    }

    public FuelStation getFuelStationByRegistrationNumber(String registration_number) {
        logger.info("Fetching fuel station by registration number: {}", registration_number);
        return fuelStationRepository.findByRegistrationNumber(registration_number)
                .orElseThrow(() -> {
                    logger.error("Fuel station not found with registration number: {}", registration_number);
                    return new RuntimeException("Fuel station not found with registration_number: " + registration_number);
                });
    }

    public FuelStation stationActiveStatus(Long stationId) {
        FuelStation station = getFuelStationById(stationId);
        station.setActive(!station.isActive());
        return fuelStationRepository.save(station); // Save updated status
    }

    public boolean isActive(Long stationId) {
        return fuelStationRepository.findById(stationId)
                .map(FuelStation::isActive)
                .orElse(false);
    }
    public FuelStation updateFuelStation(Long id, FuelStation updatedFuelStation) {
        logger.info("Updating fuel station with ID: {}", id);
        FuelStation existingFuelStation = getFuelStationById(id);

        existingFuelStation.setLocation(updatedFuelStation.getLocation());
        existingFuelStation.setOwner(updatedFuelStation.getOwner());
        existingFuelStation.setStationName(updatedFuelStation.getStationName());
        existingFuelStation.setRegistrationNumber(updatedFuelStation.getRegistrationNumber());

        if (updatedFuelStation.getFuelInventory() != null) {
            existingFuelStation.setFuelInventory(updatedFuelStation.getFuelInventory());
        }

        logger.info("Fuel station updated successfully: {}", existingFuelStation);
        return fuelStationRepository.save(existingFuelStation);
    }

    @Transactional
    public void deleteFuelStation(Long id) {
        logger.info("Attempting to delete fuel station with ID: {}", id);
        if (!fuelStationRepository.existsById(id)) {
            logger.error("Fuel station with ID: {} does not exist.", id);
            throw new FuelStationNotFoundException("Fuel station with ID " + id + " does not exist.");
        }

        fuelTransactionRepository.detachTransactions(id);
        fuelStationRepository.deleteById(id);
        logger.info("Fuel station with ID: {} deleted successfully.", id);
    }

    public DashboardData getDashboardData() {
        logger.info("Fetching dashboard data.");
        DashboardData data = new DashboardData();
        data.setTotalVehicles(vehicleRepository.count());
        data.setTotalStations(fuelStationRepository.count());
        data.setTotalFuelDistributed(fuelTransactionRepository.sumAmount());
        data.setTotalTransactions(fuelTransactionRepository.count());
        return data;
    }

/** ------------------------ Admin Register ----------------------*/
    public ResponseEntity<?> registerAdmin(RegisterRequest registerRequest) {
        logger.info("Registering new admin.");
        ResponseEntity<?> registerResponse = authController.register(registerRequest);
        if (!registerResponse.getStatusCode().is2xxSuccessful()) {
            logger.error("Admin registration failed.");
            return registerResponse;
        }

        UserLog adminLog = (UserLog) registerResponse.getBody();
        userLogRepository.save(adminLog);
        logger.info("Admin registered successfully with ID: {}", adminLog.getId());

        return ResponseEntity.ok(adminLog.getId());
    }

/** ------------------------Transaction Related ---------------*/

    public List<FuelTransaction> getFuelTransactions() {
        logger.info("Fetching all fuel transactions.");
        return fuelTransactionRepository.findAll();
    }

    public FuelTransaction getTransactionById(Long id) {
        logger.info("Fetching fuel transaction with ID: {}", id);
        return fuelTransactionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Fuel transaction not found with ID: {}", id);
                    return new RuntimeException("Transaction not found with ID: " + id);
                });
    }

    public void deleteTransaction(Long id) {
        logger.info("Attempting to delete transaction with ID: {}", id);
        fuelTransactionRepository.deleteById(id);
        logger.info("Transaction with ID: {} deleted successfully.", id);
    }

    public FuelTransaction updateFuelTransaction(Long id, FuelTransaction updatedFuelTransaction) {
        logger.info("Updating fuel transaction with ID: {}", id);
        FuelTransaction existingFuelTransaction = getTransactionById(id);

        Long stationId = updatedFuelTransaction.getStation().getId();
        FuelStation station = fuelStationRepository.findById(stationId)
                .orElseThrow(() -> {
                    logger.error("Station not found for fuel transaction with ID: {}", id);
                    return new RuntimeException("Station not found");
                });

        existingFuelTransaction.setAmount(updatedFuelTransaction.getAmount());
        existingFuelTransaction.setStation(station);

        logger.info("Fuel transaction updated successfully: {}", existingFuelTransaction);
        return fuelTransactionRepository.save(existingFuelTransaction);
    }
}
