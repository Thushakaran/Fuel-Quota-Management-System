package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.DTO.VehicleOwnerLogDTO;
import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import com.se.Fuel_Quota_Management_System.model.Vehicle;

import java.util.List;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    Vehicle registerVehicle(VehicleOwnerLogDTO vehicleOwnerLogDTO);

    Vehicle getVehicleByNumber(String vehicleNumber);

    Vehicle updateVehicle(Vehicle vehicle);

    double calculateFuelQuota(String vehicleType);

    String generateQrCode(String vehicleNumber, double fuelQuota);



    String getFuelTypeByVehicleId(Long vehicleId);

    public void updateVehicleFuelQuota(Long vehicleId,double amount);




    Vehicle findVehicleByOwnerLog(Long loginid);

    Optional<Vehicle> getVehicleById(Long vehicleId);

    public List<FuelTransaction> getFuelTransactions(Long vehicleId);
}