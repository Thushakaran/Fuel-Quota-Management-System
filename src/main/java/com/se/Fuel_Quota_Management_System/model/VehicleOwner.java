package com.se.Fuel_Quota_Management_System.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class VehicleOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password; // It should be encrypted in production

    private String fullName;

    private String icNumber;

    private int phoneNumber;

    private String email;

    @OneToMany(mappedBy = "vehicleOwner")
    private List<Vehicle> vehicles; // A vehicle owner can have multiple vehicles
}
