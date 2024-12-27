package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuelStationOwnerRepository extends JpaRepository<FuelStationOwner,Long> {
}
