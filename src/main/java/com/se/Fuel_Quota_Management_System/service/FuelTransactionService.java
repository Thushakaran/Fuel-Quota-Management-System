package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FuelTransactionService {
  public FuelTransaction saveTransaction(FuelTransaction fuelTransaction);

  public List<FuelTransaction> getAllTransaction();

  public String scanVehicleQR(String qrCode);

  public double fetchQuotaByVehicleId(Long vehicleId);

  public FuelTransaction pumpFuel(Long vehicleId, double amount);


}
