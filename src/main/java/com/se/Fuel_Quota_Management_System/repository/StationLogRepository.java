package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.OwnerLog;
import com.se.Fuel_Quota_Management_System.model.StationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationLogRepository extends JpaRepository<StationLog,Long> {

    Optional<OwnerLog> findByStationUserName(String username);
    boolean existsByStationUserName(String stationusername);
}
