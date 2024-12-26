package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import com.se.Fuel_Quota_Management_System.repository.FuelTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelTransactionServiceImpl implements FuelTransactionService {
    public FuelTransactionRepository fuelTransactionRepository;


    @Override
    public FuelTransaction saveTransaction(FuelTransaction fuelTransaction) {
        return fuelTransactionRepository.save(fuelTransaction) ;
    }

    @Override
    public List<FuelTransaction> getAllTransaction() {
        return fuelTransactionRepository.findAll();
    }
}
