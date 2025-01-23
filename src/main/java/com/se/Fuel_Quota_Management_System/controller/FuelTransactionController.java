package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.exception.InsufficientQuotaException;
import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import com.se.Fuel_Quota_Management_System.service.FuelTransactionServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class FuelTransactionController {


   private FuelTransactionServiceImpl fuelTransactionService;



    //initiate a new fuel transaction
    @PostMapping("/start")
   public ResponseEntity<FuelTransaction> startTransaction(@RequestParam Long vehicleId,
                                                           @RequestParam double amount,
                                                           @RequestParam Long stationId) {
       try {
           FuelTransaction fuelTransaction = fuelTransactionService.startTransaction(vehicleId, amount, stationId);
           return ResponseEntity.ok(fuelTransaction);
       } catch (IllegalArgumentException e) {
           return ResponseEntity.badRequest().body(null);
       }
   }




    // get all transactions for a specific vehicle
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<FuelTransaction>> getTransactionsByVehicleId(@PathVariable Long vehicleId) {
        try {
            List<FuelTransaction> transactions = fuelTransactionService.getTransactionsByVehicleId(vehicleId);
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/updateFuelQuota")
    public ResponseEntity<String> updateFuelInventory(@RequestParam Long stationId,
                                                      @RequestParam Long vehicleId,
                                                      @RequestParam double amount)
    {
        try {
            // Call the service method to update the fuel inventory
            fuelTransactionService.DeductFuelQuotaWhenPumpFuel(stationId, amount, vehicleId);
            return ResponseEntity.ok("Fuel  updated successfully.");
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
