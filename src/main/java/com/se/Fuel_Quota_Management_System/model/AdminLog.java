package com.se.Fuel_Quota_Management_System.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
public class AdminLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String Password;

    private LocalDateTime timestamp;

    private String role; // Role of the admin (e.g., "super admin", "fuel station manager").
}
