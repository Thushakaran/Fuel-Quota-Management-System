package com.se.Fuel_Quota_Management_System.service;

//import com.se.Fuel_Quota_Management_System.model.AdminLog;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private VehicleRepository vehicleRepository;

//    @Autowired
//    private AdminLogRepository adminLogRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

////    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {
////        Vehicle existingVehicle = vehicleRepository.findById(id)
////                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
////
////        // Update vehicle details
////        existingVehicle.setQuota(updatedVehicle.getQuota());
////        vehicleRepository.save(existingVehicle);
////
////        // Log the action
////        AdminLog log = new AdminLog("Updated Vehicle Quota", LocalDateTime.now(),admin);
////        adminLogRepository.save(log);
////
////        return existingVehicle;
////    }
////
////    public List<AdminLog> getAdminLogs() {
////        return adminLogRepository.findAll();
////    }
}
