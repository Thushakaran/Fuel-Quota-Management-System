package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.DTO.fuel.FuelQuotaUpdateRequest;
import com.se.Fuel_Quota_Management_System.DTO.fuel.FuelTransactionDTO;
import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import com.se.Fuel_Quota_Management_System.service.FuelTransactionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class FuelTransactionController {


    @Autowired
    private FuelTransactionServiceImpl fuelTransactionService;

    // Starts a new fuel transaction.
    @PostMapping("/startTransaction")
    public ResponseEntity<?> startTransaction(
            @RequestParam String qrCodeId,
            @RequestParam double amount,
            @RequestParam Long stationId) {
        try {
            FuelTransaction transaction = fuelTransactionService.startTransaction(qrCodeId, amount, stationId);
            return ResponseEntity.ok(transaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Transaction failed: " + e.getMessage());
        }
    }

    //Retrieves all transactions for a specific vehicle.
    @Transactional
    @GetMapping("/vehicle/qrCode/{qrCodeId}")
    public ResponseEntity<?> getTransactionsByQrCodeId(@PathVariable String qrCodeId) {
        try {
            List<FuelTransactionDTO> transactions = fuelTransactionService.getTransactionsByQrCodeId(qrCodeId);
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Updates fuel quota after a fuel transaction.
    @PostMapping("/updateFuelQuota")
    public ResponseEntity<String> updateFuelInventory(@RequestBody FuelQuotaUpdateRequest request) {
        try {
            fuelTransactionService.deductFuelQuotaWhenPumpingFuel(request.getStationId(), request.getAmount(), request.getQrCodeId());
            return ResponseEntity.ok("Fuel updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Unexpected error: " + e.getMessage());
        }
    }
}
