package com.se.Fuel_Quota_Management_System.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DmtVehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String vehicleNumber;

    @Column(unique = true, nullable = false)
    private String chassisNumber;

    private String ownerName;

    private String ownerIcNumber;

    private String fuelType;
}
