package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository

public interface AdminRepository extends JpaRepository<Admin, Long> {

        Optional<Admin> findByUsername(String username);

    }


