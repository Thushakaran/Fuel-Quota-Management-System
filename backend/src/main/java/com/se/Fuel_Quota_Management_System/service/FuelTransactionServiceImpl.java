package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.DTO.fuel.FuelTransactionDTO;
import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.repository.FuelStationRepository;
import com.se.Fuel_Quota_Management_System.repository.FuelTransactionRepository;
import com.se.Fuel_Quota_Management_System.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuelTransactionServiceImpl implements FuelTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(FuelTransactionServiceImpl.class);

    @Autowired
    private FuelTransactionRepository fuelTransactionRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private FuelStationRepository fuelStationRepository;

    @Autowired
    private FuelStationService fuelStationService;

    @Autowired
    private VehicleService vehicleService;

    // Validates if the provided amount is valid (greater than zero).
    private void validateAmount(double amount) {
        if (amount <= 0) {
            logger.error("Invalid amount: {}", amount);
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
    }

    // Retrieves a vehicle by its QR Code ID.
    private Vehicle getVehicleByQrCodeId(String qrCodeId) {
        logger.info("Fetching vehicle with QR Code ID: {}", qrCodeId);
        return vehicleRepository.findByQrCodeId(qrCodeId)
                .orElseThrow(() -> {
                    logger.error("Vehicle not found with QR Code ID: {}", qrCodeId);
                    return new IllegalArgumentException("Vehicle not found with QR Code ID: " + qrCodeId);
                });
    }

    //Retrieves a fuel station by its ID.
    private FuelStation getFuelStationById(Long stationId) {
        logger.info("Fetching fuel station with ID: {}", stationId);
        return fuelStationRepository.findById(stationId)
                .orElseThrow(() -> {
                    logger.error("Fuel Station not found with ID: {}", stationId);
                    return new IllegalArgumentException("Fuel Station not found with ID: " + stationId);
                });
    }

    // Starts a fuel transaction after validating input data.
    @Override
    @Transactional
    public FuelTransaction startTransaction(String qrCodeId, double amount, Long stationId) {
        logger.info("Starting transaction for vehicle with QR Code ID: {}, amount: {}, station ID: {}", qrCodeId, amount, stationId);

        validateAmount(amount);

        Vehicle vehicle = getVehicleByQrCodeId(qrCodeId);
        FuelStation station = getFuelStationById(stationId);

        FuelTransaction fuelTransaction = new FuelTransaction();
        fuelTransaction.setVehicle(vehicle);
        fuelTransaction.setStation(station);
        fuelTransaction.setTransactionDate(LocalDateTime.now());
        fuelTransaction.setAmount(amount);
        fuelTransaction.setSavedstationId(stationId);

        logger.info("Transaction created successfully: {}", fuelTransaction);
        return fuelTransactionRepository.save(fuelTransaction);
    }

    // Fetches all fuel transactions related to a specific vehicle.
    @Override
    @Transactional(readOnly = true)
    public List<FuelTransactionDTO> getTransactionsByQrCodeId(String qrCodeId) {
        logger.info("Fetching transactions for vehicle with QR Code ID: {}", qrCodeId);

        Vehicle vehicle = getVehicleByQrCodeId(qrCodeId);

        List<FuelTransaction> transactions = fuelTransactionRepository.findByVehicleId(vehicle.getId());

        if (transactions.isEmpty()) {
            logger.warn("No transactions found for vehicle with QR Code ID: {}", qrCodeId);
            throw new IllegalArgumentException("No transactions found for vehicle with QR Code ID: " + qrCodeId);
        }

        logger.info("Found {} transactions for vehicle with QR Code ID: {}", transactions.size(), qrCodeId);

        return transactions.stream()
                .map(tx -> new FuelTransactionDTO(
                        tx.getId(),
                        tx.getVehicle().getId(),
                        tx.getAmount(),
                        tx.getTransactionDate(),
                        tx.getStation().getId()
                ))
                .collect(Collectors.toList());
    }

    // Deducts fuel quota from the vehicle and station and records the transaction.
    @Override
    @Transactional
    public void deductFuelQuotaWhenPumpingFuel(Long stationId, double amount, String qrCodeId) {
        logger.info("Deducting fuel quota when pumping fuel for vehicle with QR Code ID: {}, amount: {}, station ID: {}", qrCodeId, amount, stationId);

        validateAmount(amount);

        try {
            // Deduct fuel from the fuel station and vehicle
            fuelStationService.updateFuelInventory(stationId, amount, qrCodeId);
            vehicleService.updateVehicleFuelQuota(qrCodeId, amount);

            // Start the transaction only after the quota deduction is successful
            startTransaction(qrCodeId, amount, stationId);

            logger.info("Fuel quota deducted and transaction recorded successfully.");
        } catch (Exception e) {
            logger.error("Error occurred during fuel deduction or transaction: {}", e.getMessage());
            throw e; // Re-throw the exception after logging it
        }
    }
}
