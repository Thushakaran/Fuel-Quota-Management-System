package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuelStationRepository extends JpaRepository<FuelStation, Long> {
    Optional<FuelStation> findByOwnerId(Long owner_id);

    Optional<FuelStation> findByLocation(String location);

    Optional <FuelStation> findByStationName(String stationName);

    Optional<FuelStation> findByRegistrationNumber(String registrationNumber);
}
