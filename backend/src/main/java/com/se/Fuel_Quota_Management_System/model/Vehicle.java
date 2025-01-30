package com.se.Fuel_Quota_Management_System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String vehicleNumber;

    @Column(unique = true, nullable = false)
    private String chassisNumber;

    private String vehicleType;

    private String ownerName;

    private String ownerIcNumber;

    private String fuelType;

    private double fuelQuota;

    private double remainingQuota;

    @Column(length = 1024)
    private String qrCode;

    @Column(unique = true, nullable = false)
    private String qrCodeId;

    private String phoneNumber;

    private String email;

    @OneToOne(cascade = CascadeType.MERGE) // Avoid accidental deletion of UserLog
    @JoinColumn(name = "loginid", nullable = false)
    private UserLog ownerLog;
}
