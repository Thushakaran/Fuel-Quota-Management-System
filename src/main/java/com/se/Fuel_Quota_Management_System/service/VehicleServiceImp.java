package com.se.Fuel_Quota_Management_System.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.se.Fuel_Quota_Management_System.exception.InsufficientQuotaException;
import com.se.Fuel_Quota_Management_System.DTO.RegisterRequest;
import com.se.Fuel_Quota_Management_System.DTO.VehicleOwnerLogDTO;
import com.se.Fuel_Quota_Management_System.controller.AuthController;
import com.se.Fuel_Quota_Management_System.exception.VehicleAlreadyRegisteredException;
import com.se.Fuel_Quota_Management_System.exception.VehicleNotFoundException;
import com.se.Fuel_Quota_Management_System.model.*;
import com.se.Fuel_Quota_Management_System.model.DmtVehicle;
//import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.repository.DmtVehicleRepository;
import com.se.Fuel_Quota_Management_System.repository.FuelTransactionRepository;
import com.se.Fuel_Quota_Management_System.repository.RoleRepository;
//import com.se.Fuel_Quota_Management_System.repository.FuelTransactionRepository;
import com.se.Fuel_Quota_Management_System.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImp implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DmtVehicleRepository dmtVehicleRepository;

    @Autowired
    private AuthController authController;

    @Autowired
    private FuelTransactionRepository fuelTransactionRepository;

    // Registers a vehicle by validating its details from the DMT mock database,
    // ensuring the vehicle is not already registered, assigning a fuel quota, and generating a QR code.
    // @return The registered vehicle with all necessary details set.

    @Transactional
    public Vehicle registerVehicle(VehicleOwnerLogDTO vehicledto) {
        try {
            // Validate the vehicle details in the DMT mock database
            DmtVehicle dmtVehicle = dmtVehicleRepository
                    .findByVehicleNumber(vehicledto.getVehicleNumber())
                    .orElseThrow(() -> new VehicleNotFoundException("Vehicle details not found in the Department of Motor Traffic database"));

            // Ensure the vehicle is not already registered in the system
            vehicleRepository.findByVehicleNumber(vehicledto.getVehicleNumber())
                    .ifPresent(v -> {
                        throw new VehicleAlreadyRegisteredException("Vehicle already registered");
                    });

            // Verify that the owner name matches the DMT record
            if (!dmtVehicle.getOwnerName().equals(vehicledto.getOwnerName())) {
                throw new RuntimeException("Owner details do not match");
            }

            // Ensure the role exists
            Role role = roleRepository.findByName("vehicle")
                    .orElseThrow(() -> new RuntimeException("Role Not Found"));

            // Register the vehicle owner in the authentication system
            RegisterRequest vehicleLog = new RegisterRequest();
            vehicleLog.setUserName(vehicledto.getUserName());
            vehicleLog.setPassword(vehicledto.getPassword());
            vehicleLog.setRole(role.getName());

            ResponseEntity<?> registerResponse = authController.register(vehicleLog);
            if (!registerResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Error registering user");
            }

            UserLog registeredLog = (UserLog) registerResponse.getBody();

            // Create and populate the Vehicle object
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleNumber(vehicledto.getVehicleNumber());
            vehicle.setOwnerName(dmtVehicle.getOwnerName());
            vehicle.setFuelType(dmtVehicle.getFuelType());
            vehicle.setVehicleType(dmtVehicle.getVehicleType());
            vehicle.setOwnerIcNumber(dmtVehicle.getOwnerIcNumber());
            vehicle.setChassisNumber(dmtVehicle.getChassisNumber());
            vehicle.setOwnerLog(registeredLog);

            // Assign the fuel quota based on the vehicle type
            double fuelQuota = calculateFuelQuota(dmtVehicle.getVehicleType());
            vehicle.setFuelQuota(fuelQuota);

            // Generate a QR code that includes vehicle number and fuel quota
            String qrCode = generateQrCode(vehicle.getVehicleNumber(), fuelQuota);
            vehicle.setQrCode(qrCode);

            // Save the vehicle to the repository and return
            return vehicleRepository.save(vehicle);
        } catch (Exception e) {
            // Log the exception and rethrow
            System.err.println("Error registering vehicle: " + e.getMessage());
            throw new RuntimeException("Vehicle registration failed", e);
        }
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
    public double calculateFuelQuota(String vehicleType) {
        // Assign fuel quota based on vehicle type
        switch (vehicleType.toLowerCase()) {
            case "motorcycle", "two-wheeler" -> {
                return 15.0;
            }
            case "taxi" -> {
                return 40.0;
            }
            case "three-wheeler", "rickshaw" -> {
                return 30.0;
            }
            case "bus" -> {
                return 200.0;
            }
            case "commercial vehicle" -> {
                return 100.0;
            }
            case "van", "minivan" -> {
                return 60.0;
            }
            case "car", "four-wheeler" -> {
                return 50.0;
            }
            default -> {
                return 20.0; // Default quota for unknown vehicle types
            }
        }
    }

    // Generates a QR code string containing the vehicle number and fuel quota.
    public String generateQrCode(String vehicleNumber, double fuelQuota) {
        try {
            String qrContent = String.format("VehicleNumber:%s|FuelQuota:%.2f", vehicleNumber, fuelQuota);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BitMatrix matrix = new MultiFormatWriter().encode(qrContent, BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageWriter.writeToStream(matrix, "PNG", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error generating QR Code", e);
        }
    }

    @Override
    public Vehicle findVehicleByOwnerLog(Long loginid) {
        return vehicleRepository.findVehicleByOwnerLogId(loginid);
    }

    @Override
    public Optional<Vehicle> getVehicleById(Long vehicleId) {
        return vehicleRepository.findById(vehicleId); // Use findById, not getById
    }

    @Override
    public List<FuelTransaction> getFuelTransactions(Long vehicleId) {
        return fuelTransactionRepository.findByVehicleId(vehicleId);
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

        // Save the updated vehicle
        return vehicleRepository.save(existingVehicle);
    }


    @Override
    public String getFuelTypeByVehicleId(Long vehicleId) {
        return vehicleRepository.findFuelTypeByVehicleId(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found with id: " + vehicleId));
    }


    //To calculate and save remaining quota for the vehicle
    @Override
    @Transactional
    public void updateVehicleFuelQuota(Long vehicleId, double amount) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        if (amount > 0 && vehicle.getRemainingQuota() >= amount) {
            // Proceed with transaction
            vehicle.setRemainingQuota((vehicle.getRemainingQuota() - amount));
            vehicleRepository.save(vehicle);


        } else {
            throw new InsufficientQuotaException("Quota exceeded!");
        }
    }


}