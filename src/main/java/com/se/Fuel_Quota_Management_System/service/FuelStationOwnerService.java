package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.DTO.FuelStationOwnerLogDTO;
import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.model.OwnerLog;
import com.se.Fuel_Quota_Management_System.controller.OwnerLogController;
import com.se.Fuel_Quota_Management_System.repository.FuelStationOwnerRepository;
import com.se.Fuel_Quota_Management_System.repository.OwnerLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class FuelStationOwnerService {
    @Autowired
    private FuelStationOwnerRepository fuelStationOwnerRepository;

    @Autowired
    private OwnerLogController ownerLogController;
    @Autowired
    private OwnerLogRepository ownerLogRepository;

    public FuelStationOwner registerOwner(FuelStationOwnerLogDTO fuelStationOwnerlog) throws Exception {
        if(fuelStationOwnerRepository.existsByNicNo(fuelStationOwnerlog.getNicNo())) {
            throw new Exception("NIC number alrady registered");
        }

        // Check if username already exists
        if (ownerLogRepository.existsByOwnerUserName(fuelStationOwnerlog.getOwnerUserName())) {
            throw new Exception("Username already exists");
        }
        {
            // Create OwnerLog
            OwnerLog ownerLog = new OwnerLog();
            ownerLog.setOwnerUserName(fuelStationOwnerlog.getOwnerUserName());
            ownerLog.setPassword(fuelStationOwnerlog.getPassword());

            // Save OwnerLog
            OwnerLog registeredLog = ownerLogController.signup(ownerLog);

            // Create FuelStationOwner
            FuelStationOwner owner = new FuelStationOwner();
            owner.setName(fuelStationOwnerlog.getOwnerName());
            owner.setNicNo(fuelStationOwnerlog.getNicNo());
            owner.setPhoneNumber(fuelStationOwnerlog.getPhoneNumber());
            owner.setEmail(fuelStationOwnerlog.getEmail());

            owner.setOwnerLog(registeredLog); // Link OwnerLog

            // Save FuelStationOwner
            return fuelStationOwnerRepository.save(owner);

        }
    }

//    public List<FuelStationOwner> findAllOwners() {
//        return fuelStationOwnerRepository.findAll();
//    }


    public FuelStationOwner findAllByNicOrEmail(String nicNo, String email) {
        return fuelStationOwnerRepository.findByNicNoOrEmail(nicNo,email);
    }


    public FuelStationOwner findFuelStationOwnerById(Long Id) {
        return fuelStationOwnerRepository.findFuelStationOwnerById(Id);
    }


}
