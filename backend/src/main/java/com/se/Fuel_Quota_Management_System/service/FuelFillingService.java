package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.model.FuelFilling;
import com.se.Fuel_Quota_Management_System.repository.FuelFillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelFillingService {

    @Autowired
    private FuelFillingRepository fuelFillingRepository;
    public List<FuelFilling> findFillingStationId(Long id) {
        return fuelFillingRepository.findByFuelStationId(id);
    }
}
