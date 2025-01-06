package com.se.Fuel_Quota_Management_System.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardData {
    private long totalVehicles;
    private long totalStations;
    private double totalFuelDistributed;
    private long activeTransactions;
}
