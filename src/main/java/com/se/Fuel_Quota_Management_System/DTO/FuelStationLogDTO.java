package com.se.Fuel_Quota_Management_System.DTO;

import com.se.Fuel_Quota_Management_System.model.FuelType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuelStationLogDTO {
    private String stationName;
    private String registrationNumber;
    private String location;
    private Long ownerId;
    private List<String> fuelTypes;

    private String stationUserName;
    private String password;
}
