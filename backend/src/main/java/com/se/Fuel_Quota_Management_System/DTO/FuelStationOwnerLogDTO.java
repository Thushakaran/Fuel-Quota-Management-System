package com.se.Fuel_Quota_Management_System.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuelStationOwnerLogDTO {

    private String UserName;
    private String password;
    private String ownerName;
    private String nicNo;
    private String phoneNumber;
    private String email;
    private String address;

}
