package com.se.Fuel_Quota_Management_System.service;


import com.se.Fuel_Quota_Management_System.exception.InsufficientQuotaException;
import com.se.Fuel_Quota_Management_System.exception.VehicleNotFoundException;
import com.se.Fuel_Quota_Management_System.model.*;
import com.se.Fuel_Quota_Management_System.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class FuelTransactionServiceImpl implements FuelTransactionService {

    @Autowired
    private FuelTransactionRepository fuelTransactionRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private FuelStationRepository fuelStationRepository;

    private FuelStationService fuelStationService;


    private VehicleService vehicleService;





    // Method to initiate a new fuel transaction
    @Override
    @Transactional
    public FuelTransaction startTransaction(Long vehicleId, double amount, Long stationId) {

        // Check if the amount in liters is valid
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        // Retrieve the vehicle entity by its ID
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + vehicleId));

        // Retrieve the fuel station entity by its ID
        FuelStation station = fuelStationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Fuel Station not found with ID: " + stationId));

        // Create a new FuelTransaction object
        FuelTransaction fuelTransaction = new FuelTransaction();
        fuelTransaction.setVehicle(vehicle);
        fuelTransaction.setStation(station);
        fuelTransaction.setTransactionDate(LocalDateTime.now());
        fuelTransaction.setAmount(amount);

        return fuelTransactionRepository.save(fuelTransaction);

    }






    // Method to fetch details of a specific transaction
    @Override
    @Transactional
    public List<FuelTransaction> getTransactionsByVehicleId(Long vehicleId) {
        List<FuelTransaction> transactions = fuelTransactionRepository.findByVehicleId(vehicleId);

        if (transactions.isEmpty()) {
            throw new IllegalArgumentException("No transactions found for vehicle with id: " + vehicleId);
        }

        return transactions;
    }


    public void DeductFuelQuotaWhenPumpFuel(Long stationId, double amount, Long vehicleId) {
            fuelStationService.updateFuelInventory(stationId,amount,vehicleId);
            vehicleService.updateVehicleFuelQuota(vehicleId,amount);
            startTransaction(vehicleId,amount,stationId);


    }


}









