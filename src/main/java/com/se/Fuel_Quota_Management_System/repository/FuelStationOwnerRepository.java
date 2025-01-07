package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelStationOwnerRepository extends JpaRepository<FuelStationOwner,Long> {

    FuelStationOwner findByNicNoOrEmail(String nicNo, String email);

    // Check if FuelStationOwner exists by NIC only
    boolean existsByNicNo(String nicNo);
}
