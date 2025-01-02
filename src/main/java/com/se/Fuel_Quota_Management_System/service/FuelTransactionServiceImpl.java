package com.se.Fuel_Quota_Management_System.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.se.Fuel_Quota_Management_System.exception.InsufficientQuotaException;
import com.se.Fuel_Quota_Management_System.exception.ResourceNotFoundException;
import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.repository.FuelTransactionRepository;
import com.se.Fuel_Quota_Management_System.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class FuelTransactionServiceImpl implements FuelTransactionService {
    @Autowired
    public FuelTransactionRepository fuelTransactionRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public FuelTransaction saveTransaction(FuelTransaction fuelTransaction) {
        return fuelTransactionRepository.save(fuelTransaction) ;
    }

    @Override
    public List<FuelTransaction> getAllTransaction() {
        return fuelTransactionRepository.findAll();
    }

    @Override
    public String scanVehicleQR(String qrCodeFilePath) {
        try {
            // Read the QR code image file
            BufferedImage bufferedImage = ImageIO.read(new File(qrCodeFilePath));
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            // Decode the QR code
            Result result = new MultiFormatReader().decode(bitmap);

            // Return the decoded text (e.g., vehicle ID)
            return result.getText();
        } catch (IOException e) {
            throw new RuntimeException("Error reading QR code image file", e);
        } catch (NotFoundException e) {
            throw new RuntimeException("No QR code found in the image", e);
        }
    }

    @Override
    public double fetchQuotaByVehicleId(Long vehicleId) {
        // Find vehicle by ID
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + vehicleId));

        // Return the fuel quota for the vehicle
        return vehicle.getFuelQuota();
    }

    @Override
    public FuelTransaction pumpFuel(Long vehicleId, double amount) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + vehicleId));

        if (vehicle.getFuelQuota() < amount) {
            throw new InsufficientQuotaException("Not enough quota for the requested fuel amount.");
        }

        vehicle.setFuelQuota(vehicle.getFuelQuota() - amount);
        vehicleRepository.save(vehicle);

        FuelTransaction transaction = new FuelTransaction();
        transaction.setVehicle(vehicle);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        return fuelTransactionRepository.save(transaction);
    }


}
