package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.exception.FuelStationNotFoundException;
import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.repository.FuelStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuelStationService {

    @Autowired
    private FuelStationRepository fuelStationRepository;
    public FuelStation registerFuelStation(FuelStation fuelStation) {
        return fuelStationRepository.save(fuelStation);
    }

    public Optional<FuelStation> findByRegistrationNumber(String registrationNumber) {
        return fuelStationRepository.findByRegistrationNumber(registrationNumber);
    }



}
