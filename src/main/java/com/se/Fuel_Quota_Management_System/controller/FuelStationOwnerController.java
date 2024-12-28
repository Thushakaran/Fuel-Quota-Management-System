package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.service.FuelStationOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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


    @GetMapping("search")
    public FuelStationOwner findOwnerByNicOrEmail(@RequestParam(value="nicNo",required = false) String nicNo,
                                                             @RequestParam(value= "email",required = false) String email){
        return fuelStationOwnerService.findAllByNicOrEmail(nicNo,email);

    }
}
