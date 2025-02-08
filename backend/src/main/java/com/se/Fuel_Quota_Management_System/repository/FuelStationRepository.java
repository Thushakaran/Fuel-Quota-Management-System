package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.FuelStation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface FuelStationRepository extends JpaRepository<FuelStation, Long> {
    Optional<FuelStation> findByOwnerId(Long owner_id);

    @Query("SELECT fs FROM FuelStation fs LEFT JOIN FETCH fs.fuelInventory WHERE fs.owner.id = :id")
    List<FuelStation> getByOwnerId(@Param("id") Long id);

    Optional<FuelStation> findByLocation(String location);

    boolean existsByRegistrationNumber(String registrationNumber);

    Optional<FuelStation> findByStationName(String stationName);

    Optional<FuelStation> findByRegistrationNumber(String registrationNumber);

    @Query("SELECT o FROM FuelStation o WHERE o.stationLog.id = :loginId")
    FuelStation findFuelStationByStationLogId(@Param("loginId") Long loginId);

    @Override
    Optional<FuelStation> findById(Long id);

    @Query("SELECT fs FROM FuelStation fs LEFT JOIN FETCH fs.fuelInventory WHERE fs.id = :id")
    Optional<FuelStation> findByIdWithInventory(@Param("id") Long id);



}
