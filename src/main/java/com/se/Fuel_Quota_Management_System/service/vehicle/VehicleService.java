package com.se.Fuel_Quota_Management_System.service.vehicle;

import com.se.Fuel_Quota_Management_System.model.Vehicle;

public interface VehicleService {
    Vehicle registerVehicle(Vehicle vehicle);

    Vehicle getVehicleByNumber(String vehicleNumber);

    Vehicle updateVehicle(Vehicle vehicle);

    double calculateFuelQuota(String vehicleType);

    String generateQrCode(String vehicleNumber, double fuelQuota);

}