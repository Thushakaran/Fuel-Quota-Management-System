package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.DTO.DashboardData;
import com.se.Fuel_Quota_Management_System.DTO.RegisterRequest;
import com.se.Fuel_Quota_Management_System.controller.AuthController;
import com.se.Fuel_Quota_Management_System.exception.ResourceNotFoundException;
import com.se.Fuel_Quota_Management_System.exception.VehicleNotFoundException;

import com.se.Fuel_Quota_Management_System.exception.FuelStationNotFoundException;
import com.se.Fuel_Quota_Management_System.model.*;

import com.se.Fuel_Quota_Management_System.repository.*;

import com.se.Fuel_Quota_Management_System.repository.FuelTransactionRepository;
import com.se.Fuel_Quota_Management_System.repository.VehicleRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.Optional;
import java.util.List;

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


    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }


    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
    }


    public List<Vehicle> getVehiclesByType(String vehicleType) {
        List<Vehicle> vehicles = vehicleRepository.findByVehicleType(vehicleType);
        if (vehicles.isEmpty()) {
            throw new RuntimeException("Vehicle not found with Type: " + vehicleType);
        }
        return vehicles;
    }


    public Optional<Vehicle> getVehiclesByOwner(String ownerName) {
        return vehicleRepository.findByOwnerName(ownerName);
    }


    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {
        Vehicle existingVehicle = getVehicleById(id);  // Fetch the existing vehicle by ID

        if (existingVehicle == null) {
            throw new ResourceNotFoundException("Vehicle not found with id " + id);
        }

        // Update fields that are allowed to be modified
        existingVehicle.setVehicleNumber(updatedVehicle.getVehicleNumber());
        existingVehicle.setOwnerName(updatedVehicle.getOwnerName());
        existingVehicle.setVehicleType(updatedVehicle.getVehicleType());
        existingVehicle.setFuelType(updatedVehicle.getFuelType());
        existingVehicle.setFuelQuota(updatedVehicle.getFuelQuota());
        existingVehicle.setChassisNumber(updatedVehicle.getChassisNumber());
        // Add other fields as necessary

        // Save the updated vehicle and return it
        return vehicleRepository.save(existingVehicle);
    }


    public void deleteVehicle(Long id) {
        // Check if the vehicle exists
        if (!vehicleRepository.existsById(id)) {
            throw new VehicleNotFoundException("Vehicle with ID " + id + " does not exist.");
        }

        // Delete the vehicle
        vehicleRepository.deleteById(id);
    }


    public List<FuelStation> getAllFuelStation() {
        return fuelStationRepository.findAll();
    }

    //Get FuelStation by their id
    public FuelStation getFuelStationById(Long id) {
        return fuelStationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fuel station not found with id: " + id));
    }

    //Get FuelStation by their owner_id
    public FuelStation getFuelStationByOwnerId(Long owner_id) {
        System.out.println("Searching for owner_id: " + owner_id);
        return fuelStationRepository.findByOwnerId(owner_id)
                .orElseThrow(() -> new RuntimeException("Fuel station not found with owner id: " + owner_id));
    }

    //Get FuelStation by their location
    public FuelStation getFuelStationByLocation(String location) {
        return fuelStationRepository.findByLocation(location)
                .orElseThrow(() -> new RuntimeException("Fuel station not found with location: " + location));
    }

    //Get FuelStation by their station_name
    public FuelStation getFuelStationByStationName(String station_name) {
        return fuelStationRepository.findByStationName(station_name)
                .orElseThrow(() -> new RuntimeException("Fuel station not found with station_name: " + station_name));
    }

    //Get FuelStation by their registration_number
    public FuelStation getFuelStationByRegistrationNumber(String registration_number) {
        return fuelStationRepository.findByRegistrationNumber(registration_number)
                .orElseThrow(() -> new RuntimeException("Fuel station not found with station_name: " + registration_number));
    }


    // Update FuelStation details by ID.

//    public FuelStation updateFuelStation(Long id, FuelStation updatedFuelStation) {
//        FuelStation existingFuelStation = getFuelStationById(id);
//
//        // Update only the fields that are allowed to be modified
//        existingFuelStation.setLocation(updatedFuelStation.getLocation());
//        existingFuelStation.setOwner(updatedFuelStation.getOwner());
//        existingFuelStation.setStationName(updatedFuelStation.getStationName());
//        existingFuelStation.setRegistrationNumber(updatedFuelStation.getRegistrationNumber());
//
//        return fuelStationRepository.save(existingFuelStation);
//    }

    public FuelStation updateFuelStation(Long id, FuelStation updatedFuelStation) {
        FuelStation existingFuelStation = getFuelStationById(id);

        // Update allowed fields
        existingFuelStation.setLocation(updatedFuelStation.getLocation());
        existingFuelStation.setOwner(updatedFuelStation.getOwner());
        existingFuelStation.setStationName(updatedFuelStation.getStationName());
        existingFuelStation.setRegistrationNumber(updatedFuelStation.getRegistrationNumber());

        // Update fuel inventory
        if (updatedFuelStation.getFuelInventory() != null) {
            existingFuelStation.setFuelInventory(updatedFuelStation.getFuelInventory());
        }

        return fuelStationRepository.save(existingFuelStation);
    }


    // Deletes a FuelStation by its ID.
    
    @Transactional
    public void deleteFuelStation(Long id) {
        // Check if the vehicle exists
        if (!fuelStationRepository.existsById(id)) {
            throw new FuelStationNotFoundException("FuelStation with ID " + id + " does not exist.");
        }

        // Delete the FuelStation
        else {
            // Detach transactions from the fuel station
            fuelTransactionRepository.detachTransactions(id);

            // Now, delete the fuel station
            fuelStationRepository.deleteById(id);
            fuelStationRepository.deleteById(id);
        }


    }

    public DashboardData getDashboardData() {
        DashboardData data = new DashboardData();

        // Fetch totals from the database
        data.setTotalVehicles(vehicleRepository.count());
        data.setTotalStations(fuelStationRepository.count());
        data.setTotalFuelDistributed(fuelTransactionRepository.sumAmount()); // Implement a custom query
        data.setTotalTransactions(fuelTransactionRepository.count());
//      data.setActiveTransactions(vehicleRepository.countActiveTransactions()); // Implement a custom query

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
        // Fetch the existing transaction
        FuelTransaction existingFuelTransaction = getTransactionById(id);

        if (existingFuelTransaction == null) {
            throw new RuntimeException("Transaction not found");
        }

        // Lookup the existing station by ID
        Long stationId = updatedFuelTransaction.getStation().getId();
        FuelStation station = fuelStationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        // Update fields
        existingFuelTransaction.setAmount(updatedFuelTransaction.getAmount());
        existingFuelTransaction.setStation(station);

        // Save and return
        return fuelTransactionRepository.save(existingFuelTransaction);
    }


}




