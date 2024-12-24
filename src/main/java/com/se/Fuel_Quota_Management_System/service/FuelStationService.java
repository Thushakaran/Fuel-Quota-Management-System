package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.repository.FuelStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuelStationService {

    @Autowired
    private FuelStationRepository fuelStationRepository;
    public FuelStation registerFuelStation(FuelStation fuelStation) {
        return (FuelStation) fuelStationRepository.save(fuelStation);
    }
}
