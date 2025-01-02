package com.se.Fuel_Quota_Management_System.service;
import com.se.Fuel_Quota_Management_System.exception.FuelStationNotFoundException;
import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
//import com.se.Fuel_Quota_Management_System.model.AdminLog;
import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.repository.FuelStationRepository;
import com.se.Fuel_Quota_Management_System.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class AdminService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private  FuelStationRepository fuelStationRepository;

//    @Autowired
//    private AdminLogRepository adminLogRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

   public List<FuelStation> getAllFuelStation() {return fuelStationRepository.findAll(); }







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
        fuelStationRepository.deleteById(id);}


    }

}

