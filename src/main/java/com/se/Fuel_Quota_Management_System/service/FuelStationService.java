package com.se.Fuel_Quota_Management_System.service;


import com.se.Fuel_Quota_Management_System.model.FuelStation;

import com.se.Fuel_Quota_Management_System.repository.CPST_StationsRepository;
import com.se.Fuel_Quota_Management_System.repository.FuelStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FuelStationService {
    @Autowired
    private FuelStationRepository fuelStationRepository;
    @Autowired
    private CPST_StationsRepository cpstStationsRepository;
    public ResponseEntity<?> registerFuelStation(FuelStation fuelStation) {

        if(fuelStationRepository.existsByRegistrationNumber(fuelStation.getRegistrationNumber())) {
            return ResponseEntity.ok("Already Registered");
        }else {
            if (cpstStationsRepository.existsByOwnerNicNo(fuelStation.getRegistrationNumber())) {
                FuelStation registeredStation = fuelStationRepository.save(fuelStation);
                return ResponseEntity.ok(registeredStation);
            } else {
                return ResponseEntity.ok("Registrarion Number not found in database");
            }
        }
    }

    public boolean existsByRegistrationNumber(String registrationNumber) {
        return fuelStationRepository.existsByRegistrationNumber(registrationNumber);
    }



}
