package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface FuelStationOwnerRepository extends JpaRepository<FuelStationOwner,Long> {

    FuelStationOwner findByNicNoOrEmail(String nicNo, String email);


}
