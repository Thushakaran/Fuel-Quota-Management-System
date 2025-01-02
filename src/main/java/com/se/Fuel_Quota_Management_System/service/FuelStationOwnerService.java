package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.repository.FuelStationOwnerRepository;
import com.se.Fuel_Quota_Management_System.repository.FuelStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelStationOwnerService {
    @Autowired
    private FuelStationOwnerRepository fuelStationOwnerRepository;
    @Autowired
    private FuelStationRepository fuelStationRepository;

    public FuelStationOwner registerOwner(FuelStationOwner owner) {
        return fuelStationOwnerRepository.save(owner);
    }

    public List<FuelStationOwner> findAllOwners() {
        return fuelStationOwnerRepository.findAll();
    }


    public FuelStationOwner findAllByNicOrEmail(String nicNo, String email) {
        return fuelStationOwnerRepository.findByNicNoOrEmail(nicNo,email);
    }
}
