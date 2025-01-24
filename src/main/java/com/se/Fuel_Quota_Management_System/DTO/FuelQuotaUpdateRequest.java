package com.se.Fuel_Quota_Management_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuelQuotaUpdateRequest {
    private Long stationId;
    private Long vehicleId;
    private double amount;
}
