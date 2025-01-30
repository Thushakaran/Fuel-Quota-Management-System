package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.DTO.DashboardData;
import com.se.Fuel_Quota_Management_System.DTO.auth.RegisterRequest;
import com.se.Fuel_Quota_Management_System.controller.AuthController;
import com.se.Fuel_Quota_Management_System.exception.VehicleNotFoundException;
import com.se.Fuel_Quota_Management_System.exception.FuelStationNotFoundException;
import com.se.Fuel_Quota_Management_System.model.*;
import com.se.Fuel_Quota_Management_System.repository.*;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

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

    /** VEHICLE SERVICES */

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found with id: " + id));
    }

    public List<Vehicle> getVehiclesByType(String vehicleType) {
        List<Vehicle> vehicles = vehicleRepository.findByVehicleType(vehicleType);
        if (vehicles.isEmpty()) {
            throw new VehicleNotFoundException("No vehicles found with type: " + vehicleType);
        }
        return vehicles;
    }

    public Optional<Vehicle> getVehiclesByOwner(String ownerName) {

        return vehicleRepository.findByOwnerName(ownerName);
    }

    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {
        Vehicle existingVehicle = getVehicleById(id);

        existingVehicle.setVehicleNumber(updatedVehicle.getVehicleNumber());
        existingVehicle.setOwnerName(updatedVehicle.getOwnerName());
        existingVehicle.setVehicleType(updatedVehicle.getVehicleType());
        existingVehicle.setFuelType(updatedVehicle.getFuelType());
        existingVehicle.setFuelQuota(updatedVehicle.getFuelQuota());
        existingVehicle.setChassisNumber(updatedVehicle.getChassisNumber());

        return vehicleRepository.save(existingVehicle);
    }

    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new VehicleNotFoundException("Vehicle with ID " + id + " does not exist.");
        }
        vehicleRepository.deleteById(id);
    }

    /** FUEL STATION SERVICES  */

    public List<FuelStation> getAllFuelStations() {
        return fuelStationRepository.findAll();
    }

    public FuelStation getFuelStationById(Long id) {
        return fuelStationRepository.findById(id)
                .orElseThrow(() -> new FuelStationNotFoundException("Fuel station not found with id: " + id));
    }

    public FuelStation getFuelStationByOwnerId(Long ownerId) {
        return fuelStationRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new FuelStationNotFoundException("Fuel station not found with owner id: " + ownerId));
    }

    public FuelStation getFuelStationByLocation(String location) {
        return fuelStationRepository.findByLocation(location)
                .orElseThrow(() -> new FuelStationNotFoundException("Fuel station not found with location: " + location));
    }

    public FuelStation updateFuelStation(Long id, FuelStation updatedFuelStation) {
        FuelStation existingFuelStation = getFuelStationById(id);

        existingFuelStation.setLocation(updatedFuelStation.getLocation());
        existingFuelStation.setOwner(updatedFuelStation.getOwner());
        existingFuelStation.setStationName(updatedFuelStation.getStationName());
        existingFuelStation.setRegistrationNumber(updatedFuelStation.getRegistrationNumber());
        existingFuelStation.setFuelInventory(updatedFuelStation.getFuelInventory());

        return fuelStationRepository.save(existingFuelStation);
    }

    @Transactional
    public void deleteFuelStation(Long id) {
        if (!fuelStationRepository.existsById(id)) {
            throw new FuelStationNotFoundException("Fuel Station with ID " + id + " does not exist.");
        }
        fuelTransactionRepository.detachTransactions(id);
        fuelStationRepository.deleteById(id);
    }

    /** DASHBOARD & ADMIN SERVICES */

    public DashboardData getDashboardData() {
        DashboardData data = new DashboardData();
        data.setTotalVehicles(vehicleRepository.count());
        data.setTotalStations(fuelStationRepository.count());
        data.setTotalFuelDistributed(fuelTransactionRepository.sumAmount());
        data.setTotalTransactions(fuelTransactionRepository.count());
        return data;
    }

    public ResponseEntity<?> registerAdmin(RegisterRequest registerRequest) {
        ResponseEntity<?> registerResponse = authController.register(registerRequest);
        if (!registerResponse.getStatusCode().is2xxSuccessful()) {
            return registerResponse;
        }
        UserLog adminLog = (UserLog) registerResponse.getBody();
        userLogRepository.save(adminLog);
        return ResponseEntity.ok(adminLog.getId());
    }

    /** TRANSACTION SERVICES  */

    public List<FuelTransaction> getFuelTransactions() {
        return fuelTransactionRepository.findAll();
    }

    public FuelTransaction getTransactionById(Long id) {
        return fuelTransactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));
    }

    public void deleteTransaction(Long id) {
        fuelTransactionRepository.deleteById(id);
    }

    public FuelTransaction updateFuelTransaction(Long id, FuelTransaction updatedFuelTransaction) {
        FuelTransaction existingTransaction = getTransactionById(id);

        Long stationId = updatedFuelTransaction.getStation().getId();
        FuelStation station = fuelStationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        existingTransaction.setAmount(updatedFuelTransaction.getAmount());
        existingTransaction.setStation(station);

        return fuelTransactionRepository.save(existingTransaction);
    }
}
