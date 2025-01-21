package com.se.Fuel_Quota_Management_System.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.se.Fuel_Quota_Management_System.exception.InsufficientQuotaException;
import com.se.Fuel_Quota_Management_System.exception.ResourceNotFoundException;
import com.se.Fuel_Quota_Management_System.exception.VehicleNotFoundException;
import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import com.se.Fuel_Quota_Management_System.model.Quota;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.repository.FuelTransactionRepository;
import com.se.Fuel_Quota_Management_System.repository.QuotaRepository;
import com.se.Fuel_Quota_Management_System.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FuelTransactionServiceImpl implements FuelTransactionService {
    @Autowired
    public FuelTransactionRepository fuelTransactionRepository;

    @Autowired
    public QuotaRepository quotaRepository;


    @Autowired
    private VehicleRepository vehicleRepository;



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
    @Transactional
    public void pumpFuel(Long stationId, Long vehicleId, double amount) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        if (vehicle.getRemainingQuota() >= amount) {
            // Proceed with transaction
            vehicle.setRemainingQuota((vehicle.getRemainingQuota() - amount));
           vehicleRepository.save(vehicle);

            //Record the fuel transaction for the station and vehicle
            FuelTransaction fuelTransaction = new FuelTransaction(stationId, vehicleId, amount, LocalDateTime.now());
            fuelTransactionRepository.save(fuelTransaction);

        } else {
            throw new InsufficientQuotaException("Quota exceeded!");
        }


    }




    // Reset every Monday
    @Scheduled(cron = "0 0 0 * * MON")
    public void resetQuotas() {
        List<Quota> quotas = quotaRepository.findAll();
        for (Quota quota : quotas) {
            quota.setRemainingQuota(quota.getTotalQuota());
            quotaRepository.save(quota);
        }
    }

}



