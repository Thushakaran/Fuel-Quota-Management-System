package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLogRepository extends JpaRepository<UserLog,Long> {
    Optional<UserLog> findByUserName(String username);

    boolean existsByUserName(String ownerUserName);
}
