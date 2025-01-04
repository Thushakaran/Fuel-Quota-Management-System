package com.se.Fuel_Quota_Management_System.service;


//import com.se.Fuel_Quota_Management_System.model.AdminLog;
import com.se.Fuel_Quota_Management_System.exception.VehicleNotFoundException;

import com.se.Fuel_Quota_Management_System.exception.FuelStationNotFoundException;
import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
//import com.se.Fuel_Quota_Management_System.model.AdminLog;
import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.repository.FuelStationRepository;

import com.se.Fuel_Quota_Management_System.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class AdminService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private FuelStationRepository fuelStationRepository;


//    @Autowired
//    private AdminLogRepository adminLogRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }


    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
    }

    /**
     * Get vehicles by their type (e.g., "Car", "Truck").
     *
     * @param vehicleType Type of vehicle
     * @return List of vehicles matching the type
     */
    public List<Vehicle> getVehiclesByType(String vehicleType) {
        List<Vehicle> vehicles = vehicleRepository.findByVehicleType(vehicleType);
        if (vehicles.isEmpty()) {
            throw new RuntimeException("Vehicle not found with Type: " + vehicleType);
        }
        return vehicles;
    }

    /**
     * Get vehicles by owner name.
     *
     * @param ownerName Name of the vehicle owner
     * @return List of vehicles registered under the owner's name
     */
    public Optional<Vehicle> getVehiclesByOwner(String ownerName) {
        return vehicleRepository.findByOwnerName(ownerName);
    }

    /**
     * Update vehicle details by ID.
     *
     * @param id             Vehicle ID
     * @param updatedVehicle Updated vehicle details
     * @return Updated vehicle object
     */
    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {
        Vehicle existingVehicle = getVehicleById(id);

        // Update only the fields that are allowed to be modified
        existingVehicle.setFuelQuota(updatedVehicle.getFuelQuota());
        existingVehicle.setFuelType(updatedVehicle.getFuelType());
        existingVehicle.setNotificationType(updatedVehicle.getNotificationType());

        return vehicleRepository.save(existingVehicle);
    }

    /**
     * Deletes a vehicle by its ID.
     *
     * @param id the ID of the vehicle to delete
     * @throws VehicleNotFoundException if no vehicle with the given ID is found
     */
    public void deleteVehicle(Long id) {
        // Check if the vehicle exists
        if (!vehicleRepository.existsById(id)) {
            throw new VehicleNotFoundException("Vehicle with ID " + id + " does not exist.");
        }

        // Delete the vehicle
        vehicleRepository.deleteById(id);
    }


//    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {
//        Vehicle existingVehicle = vehicleRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
//
//        // Update vehicle details
//        existingVehicle.setQuota(updatedVehicle.getQuota());
//        vehicleRepository.save(existingVehicle);

////        // Log the action
////        AdminLog log = new AdminLog("Updated Vehicle Quota", LocalDateTime.now(),admin);
////        adminLogRepository.save(log);
////
////        return existingVehicle;
////    }
////
////    public List<AdminLog> getAdminLogs() {
////        return adminLogRepository.findAll();
////    }


    public List<FuelStation> getAllFuelStation() {
        return fuelStationRepository.findAll();
    }


    public FuelStation getFuelStationById(Long id) {
        return fuelStationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fuel station not found with id: " + id));
    }


    public FuelStation getFuelStationByOwnerId(Long owner_id) {
        System.out.println("Searching for owner_id: " + owner_id);
        return fuelStationRepository.findByOwnerId(owner_id)
                .orElseThrow(() -> new RuntimeException("Fuel station not found with owner id: " + owner_id));
    }


    public FuelStation getFuelStationByLocation(String location) {
        return fuelStationRepository.findByLocation(location)
                .orElseThrow(() -> new RuntimeException("Fuel station not found with location: " + location));
    }


    public FuelStation getFuelStationByStationName(String station_name) {
        return fuelStationRepository.findByStationName(station_name)
                .orElseThrow(() -> new RuntimeException("Fuel station not found with station_name: " + station_name));
    }


    public FuelStation getFuelStationByRegistrationNumber(String registration_number) {
        return fuelStationRepository.findByRegistrationNumber(registration_number)
                .orElseThrow(() -> new RuntimeException("Fuel station not found with station_name: " + registration_number));
    }


    // Update vehicle details by ID.

    public FuelStation updateFuelStation(Long id, FuelStation updatedFuelStation) {
        FuelStation existingFuelStation = getFuelStationById(id);

        // Update only the fields that are allowed to be modified
        existingFuelStation.setLocation(updatedFuelStation.getLocation());
        existingFuelStation.setOwner(updatedFuelStation.getOwner());
        existingFuelStation.setStationName(updatedFuelStation.getStationName());
        existingFuelStation.setRegistrationNumber(updatedFuelStation.getRegistrationNumber());

        return fuelStationRepository.save(existingFuelStation);
    }


    // Deletes a vehicle by its ID.


    public void deleteFuelStation(Long id) {
        // Check if the vehicle exists
        if (!fuelStationRepository.existsById(id)) {
            throw new FuelStationNotFoundException("FuelStation with ID " + id + " does not exist.");
        }

        // Delete the vehicle
        else {
            fuelStationRepository.deleteById(id);
        }


    }
}



