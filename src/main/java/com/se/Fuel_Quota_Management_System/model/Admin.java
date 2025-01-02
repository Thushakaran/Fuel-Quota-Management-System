package com.se.Fuel_Quota_Management_System.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password; // Store hashed passwords

    private String email;

    @Column(nullable = false)
    private String role = "ROLE_ADMIN"; // Admin role

    private LocalDateTime timestamp;
}
