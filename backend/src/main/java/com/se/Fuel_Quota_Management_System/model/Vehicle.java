package com.se.Fuel_Quota_Management_System.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String qrCode;

    private String phoneNumber;

    private String email;


    private double remainingQuota;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fuel_station_id")
//    private FuelStation fuelStation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loginid", nullable = false)
    private UserLog ownerLog;

//    @ManyToOne
//    @JoinColumn(name = "vehicle_owner_id")
//    private VehicleOwner vehicleOwner; // Link to the VehicleOwner entity


}
