package com.se.Fuel_Quota_Management_System.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class FuelTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    private double pumpedLiters;

    private double amount;

    private double remainingQuota;

    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private FuelStation station;
}
