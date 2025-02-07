package com.se.Fuel_Quota_Management_System.DTO.logs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuelStationLogDTO {
    private String stationName;
    private String registrationNumber;
    private String location;
    private Long ownerId;
    private Map<String,Double> fuelTypes;
    private String userName;
    private String password;
}
