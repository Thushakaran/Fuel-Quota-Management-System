package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.repository.CPST_StationsRepository;
import com.se.Fuel_Quota_Management_System.service.FuelStationOwnerService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("api/owner")
public class FuelStationOwnerController {
    @Autowired
    private FuelStationOwnerService fuelStationOwnerService;
    @Autowired
    private CPST_StationsRepository cpstStationsRepository;

    // for register StationOwner
    @PostMapping("/register")
    public ResponseEntity<FuelStationOwner> registerFuelStationOwner(@RequestBody @NotNull FuelStationOwner fuelStationOwner){
        //check already registered or not
        FuelStationOwner registeredOwner = null;
        if(findByNicNo(fuelStationOwner.getNicNo())){
            return ResponseEntity.ok(registeredOwner);
        }else {
            //check fuel owner NIC in the database
            if (cpstStationsRepository.existsByOwnerNicNo(fuelStationOwner.getNicNo())) {
                registeredOwner = fuelStationOwnerService.registerOwner(fuelStationOwner);
                return ResponseEntity.ok(registeredOwner);
            } else {
                return ResponseEntity.ok(registeredOwner);
            }
        }
    }


    @GetMapping
    public List<FuelStationOwner> findall(){
        return fuelStationOwnerService.findAllOwners();
    }
    // find anyone Registered on this NIC
    @GetMapping("searchnic/{nicNo}")
    public boolean findByNicNo(@PathVariable("nicNo") String nicNo){
        Optional<FuelStationOwner> fuelStationOwner = fuelStationOwnerService.findByNicNo(nicNo);
        return fuelStationOwner.isPresent();
    }


    @GetMapping("search")
    public FuelStationOwner findOwnerByNicOrEmail(@RequestParam(value="nicNo",required = false) String nicNo,
                                                             @RequestParam(value= "email",required = false) String email){
        return fuelStationOwnerService.findAllByNicOrEmail(nicNo,email);

    }
}
