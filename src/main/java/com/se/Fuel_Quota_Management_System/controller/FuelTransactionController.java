package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import com.se.Fuel_Quota_Management_System.service.FuelTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class FuelTransactionController {

    private FuelTransactionService fuelTransactionService;
    @PostMapping("/update")
    public String update(@RequestBody FuelTransaction fuelTransaction){
        fuelTransactionService.saveTransaction(fuelTransaction);
        return "Fuel transaction is successfully!!!";
    }


    @GetMapping("/getTransactionDetails")
    public List<FuelTransaction> getTransactionDetails(){
        return fuelTransactionService.getAllTransaction();
    }

    //scan QR code and convert to VehicleId
    @PostMapping("/scan-qr")
    public ResponseEntity<String> scanVehicleQR(@RequestParam("file") MultipartFile qrFile) {
        try {
            // Save the uploaded file temporarily
            File tempFile = File.createTempFile("qr-code", ".png");
            qrFile.transferTo(tempFile);

            // Decode the QR code
            String decodedText = fuelTransactionService.scanVehicleQR(tempFile.getAbsolutePath());

            // Delete the temporary file
            tempFile.delete();

            return ResponseEntity.ok(decodedText);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to process the QR code file.");
        }
    }



    @GetMapping("/fuel-quota")
    public double getFuelQuotaByVehicleId(@PathVariable Long vehicleId) {
        return fuelTransactionService.fetchQuotaByVehicleId(vehicleId);
    }


    @PostMapping("/pump")
    public FuelTransaction pumpFuel(@PathVariable Long vehicleId, @RequestParam double amount) {
        return fuelTransactionService.pumpFuel(vehicleId, amount);
    }




}
