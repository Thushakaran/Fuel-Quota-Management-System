package com.se.Fuel_Quota_Management_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleOwnerLogDTO {
    private String userName;

    private String password;
    private String vehicleNumber;
    private String chassisNumber;
    private String vehicleType;
    private String ownerName;
    private String ownerIcNumber;
    private String fuelType;


}