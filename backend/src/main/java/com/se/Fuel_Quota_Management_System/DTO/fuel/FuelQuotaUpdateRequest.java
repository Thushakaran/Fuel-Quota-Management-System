package com.se.Fuel_Quota_Management_System.DTO.fuel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelQuotaUpdateRequest {
    private Long stationId;
    private double amount;
    private String qrCodeId;
}
