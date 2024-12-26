package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.FuelStation;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FuelStationRepository extends CrudRepository<FuelStation, Long> {
    Optional<FuelStation> findByRegistrationNumber(String registrationNumber);
}
