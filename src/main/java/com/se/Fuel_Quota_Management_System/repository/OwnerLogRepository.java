package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.OwnerLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerLogRepository extends JpaRepository<OwnerLog, Long> {
    Optional<OwnerLog> findByOwnerUserName(String username);

    boolean existsByOwnerUserName(String ownerUserName);
}

