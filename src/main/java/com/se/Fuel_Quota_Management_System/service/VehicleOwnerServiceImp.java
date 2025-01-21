package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.model.VehicleOwner;
import com.se.Fuel_Quota_Management_System.repository.VehicleOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleOwnerServiceImp implements VehicleOwnerService { // Implementing the interface
    @Autowired
    private VehicleOwnerRepository vehicleOwnerRepository;

    @Override
    public VehicleOwner getOwnerById(Long id) {
        return vehicleOwnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found with ID: " + id));
    }
}
