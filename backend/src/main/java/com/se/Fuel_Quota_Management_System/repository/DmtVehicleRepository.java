package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.DmtVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DmtVehicleRepository extends JpaRepository<DmtVehicle, Long> {
    Optional<DmtVehicle> findByVehicleNumber(String vehicleNumber);
}

