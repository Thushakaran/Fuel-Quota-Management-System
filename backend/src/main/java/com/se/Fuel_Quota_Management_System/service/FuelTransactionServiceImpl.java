package com.se.Fuel_Quota_Management_System.service;


import com.se.Fuel_Quota_Management_System.DTO.FuelTransactionDTO;
import com.se.Fuel_Quota_Management_System.model.*;
import com.se.Fuel_Quota_Management_System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuelTransactionServiceImpl implements FuelTransactionService {

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


    // Method to initiate a new fuel transaction
    @Override
    @Transactional
    public FuelTransaction startTransaction(String qrCodeId, double amount, Long stationId) {

        // Check if the amount in liters is valid
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        // Retrieve the vehicle entity by its QR Code ID
        Vehicle vehicle = vehicleRepository.findByQrCodeId(qrCodeId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with QR Code ID: " + qrCodeId));

        // Retrieve the fuel station entity by its ID
        FuelStation station = fuelStationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Fuel Station not found with ID: " + stationId));

        // Create a new FuelTransaction object
        FuelTransaction fuelTransaction = new FuelTransaction();
        fuelTransaction.setVehicle(vehicle);
        fuelTransaction.setStation(station);
        fuelTransaction.setTransactionDate(LocalDateTime.now());
        fuelTransaction.setAmount(amount);
        fuelTransaction.setSavedstationId(stationId);

        return fuelTransactionRepository.save(fuelTransaction);
    }



    // Method to fetch details of a specific transaction

    @Override
    @Transactional
    public List<FuelTransactionDTO> getTransactionsByQrCodeId(String qrCodeId) {
        // Retrieve the vehicle entity by its QR Code ID
        Vehicle vehicle = vehicleRepository.findByQrCodeId(qrCodeId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with QR Code ID: " + qrCodeId));

        // Fetch transactions using the vehicle's ID
        List<FuelTransaction> transactions = fuelTransactionRepository.findByVehicleId(vehicle.getId());

        if (transactions.isEmpty()) {
            throw new IllegalArgumentException("No transactions found for vehicle with QR Code ID: " + qrCodeId);
        }

        // Map entities to DTOs
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


    public void DeductFuelQuotaWhenPumpFuel(Long stationId, double amount, String qrCodeId) {
        // Deduct from the fuel inventory of the station
        fuelStationService.updateFuelInventory(stationId, amount, qrCodeId);

        // Deduct from the vehicle's remaining quota
        vehicleService.updateVehicleFuelQuota(qrCodeId, amount);

        // Start a fuel transaction
        startTransaction(qrCodeId, amount, stationId);
    }


}









