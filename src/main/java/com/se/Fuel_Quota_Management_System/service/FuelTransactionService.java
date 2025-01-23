package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FuelTransactionService {


  public FuelTransaction startTransaction(Long vehicleId, double amount, Long stationId);

  public List<FuelTransaction> getTransactionsByVehicleId(Long vehicleId);

//  public void updateFuelInventory(Long stationId, double amount, Long vehicleId);


//  public void updateVehicleFuelQuota(Long vehicleId,double amount);







}