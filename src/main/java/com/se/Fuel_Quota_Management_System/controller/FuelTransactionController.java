package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import com.se.Fuel_Quota_Management_System.service.FuelTransactionService;
import org.springframework.web.bind.annotation.*;

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

}
