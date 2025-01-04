package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.repository.CPST_StationsRepository;
import com.se.Fuel_Quota_Management_System.repository.FuelStationOwnerRepository;
import com.se.Fuel_Quota_Management_System.repository.FuelStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelStationOwnerService {
    @Autowired
    private FuelStationOwnerRepository fuelStationOwnerRepository;

    @Autowired
    private CPST_StationsRepository cpstStationsRepository;
    @Autowired
    private FuelStationRepository fuelStationRepository;

    public ResponseEntity<?> registerOwner(FuelStationOwner fuelStationOwner) {

        if(fuelStationOwnerRepository.existsByNicNo(fuelStationOwner.getNicNo())) {
            return ResponseEntity.ok("Already Registered");
        }else {
            if (cpstStationsRepository.existsByOwnerNicNo(fuelStationOwner.getNicNo())) {
                FuelStationOwner registeredOwner =fuelStationOwnerRepository.save(fuelStationOwner);
                return ResponseEntity.ok(registeredOwner);
            } else {
                return ResponseEntity.ok("NIC Number not found in database");
            }
        }
    }

    public List<FuelStationOwner> findAllOwners() {
        return fuelStationOwnerRepository.findAll();
    }


    public FuelStationOwner findAllByNicOrEmail(String nicNo, String email) {
        return fuelStationOwnerRepository.findByNicNoOrEmail(nicNo,email);
    }






}
