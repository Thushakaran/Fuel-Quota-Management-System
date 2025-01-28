package com.se.Fuel_Quota_Management_System.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
@Getter
@Setter
public class FuelTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id") // Foreign key in the FuelTransaction table
    private Vehicle vehicle;


    private double amount;


    private LocalDateTime transactionDate;

    private Long savedstationId;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = true) // Allow station_id to be NULL
    private FuelStation station;

    public FuelTransaction() {
    }


    public FuelTransaction(Long id, Vehicle vehicleId, double amount, LocalDateTime transactionDate, FuelStation stationId) {
        this.id = id;
        this.vehicle = vehicleId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.station = stationId;
    }

    public FuelTransaction(FuelStation stationId, Vehicle vehicleId, double amount, LocalDateTime now) {
        this.vehicle = vehicleId;
        this.amount = amount;
        this.transactionDate = now;
        this.station = stationId;
    }


}
