package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.CPST_Stations;
import org.springframework.data.jpa.repository.JpaRepository;

// Ceylon Petrolium Storage Terimial
public interface CPST_StationsRepository extends JpaRepository<CPST_Stations, Long> {

    boolean findByRegistrationNumber(String registrationNumber);

    boolean findByOwnerNicNo(String nicNo);

    boolean existsByRegistrationNumber(String registrationNumber);

    boolean existsByOwnerNicNo(String nicNo);
}
