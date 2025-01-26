package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface FuelStationOwnerRepository extends JpaRepository<FuelStationOwner,Long> {

    FuelStationOwner findByNicNoOrEmail(String nicNo, String email);


    Optional<FuelStationOwner> findFuelStationOwnerById(Long id);

    boolean existsByNicNo(String nicNo);

    @Query("SELECT o FROM FuelStationOwner o WHERE o.ownerLog.id = :loginId")
    FuelStationOwner findFuelStationOwnerByOwnerLogId(@Param("loginId") Long loginId);
}
