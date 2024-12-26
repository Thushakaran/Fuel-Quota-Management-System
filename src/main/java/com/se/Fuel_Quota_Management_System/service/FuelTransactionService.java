package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FuelTransactionService {
  public FuelTransaction saveTransaction(FuelTransaction fuelTransaction);

  public List<FuelTransaction> getAllTransaction();
}
