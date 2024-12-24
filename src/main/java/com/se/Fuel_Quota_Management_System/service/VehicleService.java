package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.exception.VehicleAlreadyRegisteredException;
import com.se.Fuel_Quota_Management_System.exception.VehicleNotFoundException;
import com.se.Fuel_Quota_Management_System.model.DmtVehicle;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.repository.DmtVehicleRepository;
import com.se.Fuel_Quota_Management_System.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private DmtVehicleRepository dmtVehicleRepository;

    // Registers a vehicle by validating its details from the DMT mock database,
    // ensuring the vehicle is not already registered, assigning a fuel quota, and generating a QR code.
    // @return The registered vehicle with all necessary details set.
    public Vehicle registerVehicle(Vehicle vehicle) {
        // Validate the vehicle details in the Department of Motor Traffic (DMT) mock database
        DmtVehicle dmtVehicle = dmtVehicleRepository
                .findByVehicleNumber(vehicle.getVehicleNumber())
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle details not found in the Department of Motor Traffic database"));

        // Verify that the owner name matches the DMT record
        if (!dmtVehicle.getOwnerName().equals(vehicle.getOwnerName())) {
            throw new RuntimeException("Owner details do not match");
        }

        if(!dmtVehicle.getOwnerIcNumber().equals((vehicle.getOwnerIcNumber()))){
            throw new RuntimeException("Owner IC Number do not match");
        }

        if(!dmtVehicle.getChassisNumber().equals((vehicle.getChassisNumber()))){
            throw new RuntimeException("Vehicle Chassis Number do not match");
        }

        if(!dmtVehicle.getVehicleType().equals((vehicle.getVehicleType()))){
            throw new RuntimeException("Vehicle type do not match");
        }

        if(!dmtVehicle.getFuelType().equals((vehicle.getFuelType()))){
            throw new RuntimeException("Vehicle Fuel type do not match");
        }

        // Ensure the vehicle is not already registered in the system
        Optional<Vehicle> existingVehicle = vehicleRepository.findByVehicleNumber(vehicle.getVehicleNumber());
        if (existingVehicle.isPresent()) {
            throw new VehicleAlreadyRegisteredException("Vehicle already registered");
        }

        // Assign the fuel quota based on the vehicle type
        double fuelQuota = calculateFuelQuota(dmtVehicle.getVehicleType());
        vehicle.setFuelQuota(fuelQuota);

        // Generate a QR code that includes vehicle number and fuel quota
        String qrCode = generateQrCode(vehicle.getVehicleNumber(), fuelQuota);
        vehicle.setQrCode(qrCode);

        // Set additional details from the DMT mock database
        vehicle.setFuelType(dmtVehicle.getFuelType());
        vehicle.setOwnerName(dmtVehicle.getOwnerName());

        // Save the vehicle to the repository and return
        return vehicleRepository.save(vehicle);
    }

    // Retrieves a vehicle by its vehicle number from the repository.
    // @return The vehicle details if found; otherwise, throws an exception.
    public Vehicle getVehicleByNumber(String vehicleNumber) {
        // Find the vehicle by its number or throw an exception if not found
        return vehicleRepository.findByVehicleNumber(vehicleNumber)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with number: " + vehicleNumber));
    }

    // Determines the fuel quota based on the type of vehicle.
    // @return The fuel quota for the given vehicle type.
    private double calculateFuelQuota(String vehicleType) {
        // Assign fuel quota based on vehicle type
        switch (vehicleType.toLowerCase()) {
            case "motorcycle" -> {
                return 15.0;
            }
            case "taxi" -> {
                return 40.0;
            }
            case "three-wheeler" -> {
                return 30.0;
            }
            case "heavy vehicles" -> {
                return 200.0;
            }
            case "commercial vehicle" -> {
                return 100.0;
            }
            case "special vehicle" -> {
                return 150.0;
            }
            case "van" -> {
                return 60.0;
            }
            case "car" -> {
                return 50.0;
            }
            default -> {
                return 20.0; // Default quota for unknown vehicle types
            }
        }
    }

    // Generates a QR code string containing the vehicle number and fuel quota.
    // @return The formatted QR code string.
    private String generateQrCode(String vehicleNumber, double fuelQuota) {
        // Format the QR code to include vehicle number and fuel quota
        return String.format("QR|VehicleNumber:%s|FuelQuota:%.2f", vehicleNumber, fuelQuota);
    }

    // Update vehicle details in the system
    public Vehicle updateVehicle(Vehicle vehicle) {
        // Fetch the existing vehicle from the database
        Optional<Vehicle> existingVehicleOpt = vehicleRepository.findByVehicleNumber(vehicle.getVehicleNumber());

        if (!existingVehicleOpt.isPresent()) {
            throw new RuntimeException("Vehicle not found with number: " + vehicle.getVehicleNumber());
        }

        // Fetch the DmtVehicle details to validate and update
        DmtVehicle dmtVehicle = dmtVehicleRepository.findByVehicleNumber(vehicle.getVehicleNumber())
                .orElseThrow(() -> new RuntimeException("Vehicle not found in DMT database"));

        // Check if the owner name matches the DMT record
        if (!dmtVehicle.getOwnerName().equals(vehicle.getOwnerName())) {
            throw new RuntimeException("Owner details do not match the DMT record");
        }

        // Get the existing vehicle object
        Vehicle existingVehicle = existingVehicleOpt.get();

        // Update the existing vehicle with new details
        existingVehicle.setOwnerName(vehicle.getOwnerName());
        existingVehicle.setFuelType(vehicle.getFuelType());
        existingVehicle.setFuelQuota(vehicle.getFuelQuota());
        existingVehicle.setVehicleType(vehicle.getVehicleType());
        existingVehicle.setChassisNumber(vehicle.getChassisNumber());
        existingVehicle.setQrCode(vehicle.getQrCode());
        existingVehicle.setNotificationType(vehicle.getNotificationType());
        // Update additional fields as needed

        // Save the updated vehicle
        return vehicleRepository.save(existingVehicle);
    }
}