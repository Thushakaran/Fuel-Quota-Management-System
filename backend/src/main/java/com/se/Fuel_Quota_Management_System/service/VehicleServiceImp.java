package com.se.Fuel_Quota_Management_System.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.se.Fuel_Quota_Management_System.DTO.auth.RegisterRequest;
import com.se.Fuel_Quota_Management_System.DTO.logs.VehicleOwnerLogDTO;
import com.se.Fuel_Quota_Management_System.controller.AuthController;
import com.se.Fuel_Quota_Management_System.exception.InsufficientQuotaException;
import com.se.Fuel_Quota_Management_System.exception.VehicleAlreadyRegisteredException;
import com.se.Fuel_Quota_Management_System.exception.VehicleNotFoundException;
import com.se.Fuel_Quota_Management_System.model.*;
import com.se.Fuel_Quota_Management_System.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImp implements VehicleService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImp.class);

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

    public Vehicle registerVehicle(VehicleOwnerLogDTO vehicleOwnerLogDTO) {
        try {
            logger.info("Starting vehicle registration for vehicle number: {}", vehicleOwnerLogDTO.getVehicleNumber());

            // Validate vehicle details and ownership
            DmtVehicle dmtVehicle = validateVehicleDetails(vehicleOwnerLogDTO);
            validateOwnerDetails(dmtVehicle, vehicleOwnerLogDTO);

            // Ensure the vehicle is not already registered
            checkIfVehicleAlreadyRegistered(vehicleOwnerLogDTO);

            // Ensure the role exists
            Role role = getRoleByName("vehicle");

            // Register user and get the logged in UserLog
            UserLog registeredLog = registerUser(vehicleOwnerLogDTO, role);

            // Create and return the vehicle object
            Vehicle vehicle = createVehicle(vehicleOwnerLogDTO, dmtVehicle, registeredLog);
            logger.info("Vehicle registered successfully for vehicle number: {}", vehicleOwnerLogDTO.getVehicleNumber());

            return vehicleRepository.save(vehicle);
        } catch (RuntimeException e) {
            // Log specific exceptions
            logger.error("Error registering vehicle: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            // Log generic errors
            logger.error("Unexpected error during vehicle registration: {}", e.getMessage(), e);
            throw new RuntimeException("Vehicle registration failed", e);
        }
    }

    private DmtVehicle validateVehicleDetails(VehicleOwnerLogDTO vehicleOwnerLogDTO) {
        return dmtVehicleRepository
                .findByVehicleNumber(vehicleOwnerLogDTO.getVehicleNumber())
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle details not found in the Department of Motor Traffic database"));
    }

    private void validateOwnerDetails(DmtVehicle dmtVehicle, VehicleOwnerLogDTO vehicleOwnerLogDTO) {
        if (!dmtVehicle.getOwnerName().equals(vehicleOwnerLogDTO.getOwnerName())) {
            logger.warn("Owner details do not match for vehicle number: {}", vehicleOwnerLogDTO.getVehicleNumber());
            throw new RuntimeException("Owner details do not match");
        }
    }

    private void checkIfVehicleAlreadyRegistered(VehicleOwnerLogDTO vehicleOwnerLogDTO) {
        vehicleRepository.findByVehicleNumber(vehicleOwnerLogDTO.getVehicleNumber())
                .ifPresent(v -> {
                    logger.warn("Vehicle already registered for vehicle number: {}", vehicleOwnerLogDTO.getVehicleNumber());
                    throw new VehicleAlreadyRegisteredException("Vehicle already registered");
                });
    }

    private Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> {
                    logger.error("Role not found: {}", roleName);
                    return new RuntimeException("Role Not Found");
                });
    }


    private UserLog registerUser(VehicleOwnerLogDTO vehicleOwnerLogDTO, Role role) {
        RegisterRequest vehicleLog = new RegisterRequest();
        vehicleLog.setUserName(vehicleOwnerLogDTO.getUserName());
        vehicleLog.setPassword(vehicleOwnerLogDTO.getPassword());
        vehicleLog.setRole(role.getName());

        ResponseEntity<?> registerResponse = authController.register(vehicleLog);
        if (!registerResponse.getStatusCode().is2xxSuccessful()) {
            logger.error("Error registering user with username: {}", vehicleOwnerLogDTO.getUserName());
            throw new RuntimeException("Error registering user");
        }
        return (UserLog) registerResponse.getBody();
    }

    private Vehicle createVehicle(VehicleOwnerLogDTO vehicleOwnerLogDTO, DmtVehicle dmtVehicle, UserLog registeredLog) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(vehicleOwnerLogDTO.getVehicleNumber());
        vehicle.setPhoneNumber(vehicleOwnerLogDTO.getPhoneNumber());
        vehicle.setEmail(vehicleOwnerLogDTO.getEmail());
        vehicle.setOwnerName(dmtVehicle.getOwnerName());
        vehicle.setFuelType(vehicleOwnerLogDTO.getFuelType());
        vehicle.setVehicleType(dmtVehicle.getVehicleType());
        vehicle.setOwnerIcNumber(dmtVehicle.getOwnerIcNumber());
        vehicle.setChassisNumber(dmtVehicle.getChassisNumber());
        vehicle.setOwnerLog(registeredLog);
        vehicle.setActive(true);

        // Assign fuel quota and remaining quota
        double fuelQuota = calculateFuelQuota(dmtVehicle.getVehicleType());
        vehicle.setFuelQuota(fuelQuota);
        vehicle.setRemainingQuota(fuelQuota);

        // Generate QR code and ID
        String qrCodeId = generateQrCodeId(8);
        vehicle.setQrCodeId(qrCodeId);
        String qrCode = generateQrCode(qrCodeId);
        vehicle.setQrCode(qrCode);

        logger.info("Vehicle created with QR Code ID: {}", qrCodeId);

        return vehicle;
    }

    // QR code generation methods and others remain unchanged...

    @Override
    public Vehicle getVehicleByNumber(String vehicleNumber) {
        logger.info("Fetching vehicle details for vehicle number: {}", vehicleNumber);
        return vehicleRepository.findByVehicleNumber(vehicleNumber)
                .orElseThrow(() -> {
                    logger.error("Vehicle not found for vehicle number: {}", vehicleNumber);
                    return new RuntimeException("Vehicle not found with number: " + vehicleNumber);
                });
    }

    @Override
    public void updateVehicleFuelQuota(String qrCodeId, double amount) {
        Vehicle vehicle = vehicleRepository.findByQrCodeId(qrCodeId)
                .orElseThrow(() -> {
                    logger.error("Vehicle not found with QR Code ID: {}", qrCodeId);
                    return new VehicleNotFoundException("Vehicle not found with QR Code ID: " + qrCodeId);
                });

        if (amount > 0 && vehicle.getRemainingQuota() >= amount) {
            vehicle.setRemainingQuota(vehicle.getRemainingQuota() - amount);
            vehicleRepository.save(vehicle);
            logger.info("Fuel quota updated for vehicle with QR Code ID: {}", qrCodeId);
        } else {
            logger.warn("Quota exceeded or invalid amount for vehicle with QR Code ID: {}", qrCodeId);
            throw new InsufficientQuotaException("Quota exceeded or invalid amount!");
        }
    }

    public String generateQrCode(String qrCodeId) {
        try {
            logger.info("Generating QR Code for QR Code ID: {}", qrCodeId);

            BitMatrix matrix = new MultiFormatWriter().encode(qrCodeId, BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", baos);

            String qrCode = Base64.getEncoder().encodeToString(baos.toByteArray());
            logger.info("QR Code generated successfully for QR Code ID: {}", qrCodeId);

            return qrCode;
        } catch (Exception e) {
            logger.error("Error generating QR Code for QR Code ID: {}", qrCodeId, e);
            throw new RuntimeException("Error generating QR Code", e);
        }
    }

    @Override
    public Vehicle findVehicleByOwnerLog(Long loginId) {
        logger.info("Finding vehicle by Owner Log ID: {}", loginId);
        Vehicle vehicle = vehicleRepository.findVehicleByOwnerLogId(loginId);

        if (vehicle != null) {
            logger.info("Vehicle found for Owner Log ID: {}", loginId);
        } else {
            logger.warn("No vehicle found for Owner Log ID: {}", loginId);
        }

        return vehicle;
    }

    @Override
    public String getFuelTypeByVehicleId(Long vehicleId) {
        logger.info("Fetching fuel type for vehicle with ID: {}", vehicleId);

        return vehicleRepository.findFuelTypeByVehicleId(vehicleId)
                .orElseThrow(() -> {
                    logger.error("Vehicle not found with ID: {}", vehicleId);
                    return new VehicleNotFoundException("Vehicle not found with id: " + vehicleId);
                });
    }

    public double calculateFuelQuota(String vehicleType) {
        logger.info("Calculating fuel quota for vehicle type: {}", vehicleType);
        double fuelQuota = switch (vehicleType.toLowerCase()) {
            case "motorcycle", "two-wheeler" -> 15.0;
            case "taxi" -> 40.0;
            case "three-wheeler", "rickshaw" -> 30.0;
            case "bus" -> 200.0;
            case "commercial vehicle" -> 100.0;
            case "van", "minivan" -> 60.0;
            case "car", "four-wheeler" -> 50.0;
            default -> 20.0;
        };

        logger.info("Fuel quota calculated for vehicle type {}: {}", vehicleType, fuelQuota);
        return fuelQuota;
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    public void resetWeeklyFuelQuota() {
        logger.info("Scheduled task to reset weekly fuel quota for all vehicles started.");

        vehicleRepository.findAll().forEach(vehicle -> {
            double newQuota = calculateFuelQuota(vehicle.getVehicleType());
            vehicle.setRemainingQuota(newQuota);
            logger.info("Resetting fuel quota for vehicle {} to {}", vehicle.getVehicleNumber(), newQuota);
        });

        vehicleRepository.saveAll(vehicleRepository.findAll());
        logger.info("Weekly fuel quota reset completed for all vehicles.");
    }
          
    // Generates a QR code string containing the vehicle number and fuel quota.
    private String generateQrCodeId(int length) {
        logger.info("Generating QR Code ID with length: {}", length);

        String qrCodeId = RANDOM.ints(length, 0, CHARACTERS.length())
                .mapToObj(CHARACTERS::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        logger.info("Generated QR Code ID: {}", qrCodeId);
        return qrCodeId;
    }

    @Override
    public Optional<Vehicle> getVehicleById(Long vehicleId) {
        logger.info("Fetching vehicle details by ID: {}", vehicleId);
        return vehicleRepository.findById(vehicleId);
    }

    @Override
    public List<FuelTransaction> getFuelTransactions(Long vehicleId) {
        logger.info("Fetching fuel transactions for vehicle with ID: {}", vehicleId);
        return fuelTransactionRepository.findByVehicleId(vehicleId);
    }

    @Override
    @Transactional
    public Vehicle updateVehicle(Vehicle vehicle) {
        logger.info("Updating vehicle details for vehicle ID: {}", vehicle.getId());
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        logger.info("Vehicle updated successfully for vehicle ID: {}", vehicle.getId());
        return updatedVehicle;
    }
}