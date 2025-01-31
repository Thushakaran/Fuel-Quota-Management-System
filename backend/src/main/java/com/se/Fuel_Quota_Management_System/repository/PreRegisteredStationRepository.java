package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.PreRegisteredStation;
import org.springframework.data.jpa.repository.JpaRepository;

// Ceylon Petroleum Storage Terminal
public interface PreRegisteredStationRepository extends JpaRepository<PreRegisteredStation, Long> {

    PreRegisteredStation findByRegistrationNumber(String registrationNumber);


    boolean existsByRegistrationNumber(String registrationNumber);


}
