package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.DTO.fuel.FuelTransactionDTO;
import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FuelTransactionService {

  public FuelTransaction startTransaction(String qrCodeId, double amount, Long stationId);

  public List<FuelTransactionDTO> getTransactionsByQrCodeId(String qrCodeId);

  public void deductFuelQuotaWhenPumpingFuel(Long stationId, double amount, String qrCodeId);


//  public void updateFuelInventory(Long stationId, double amount, Long vehicleId);
//  public void updateVehicleFuelQuota(Long vehicleId,double amount);

}