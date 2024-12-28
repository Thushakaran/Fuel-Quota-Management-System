package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.service.FuelStationOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/owner")
public class FuelStationOwnerController {
    @Autowired
    private FuelStationOwnerService fuelStationOwnerService;

    // for register StationOwner
    @PostMapping("/register")
    public String registerFuelStaionOwner(FuelStationOwner fuelStationOwner){
        FuelStationOwner registerOwner = fuelStationOwnerService.registerOwner(fuelStationOwner);
        return "Succesfully Registered";
    }

    public List<FuelStationOwner> findAllOwners(){
        return fuelStationOwnerService.findAllOwners();
    }
}
