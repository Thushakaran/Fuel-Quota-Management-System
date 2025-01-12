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

    public FuelTransaction() {
    }

    public FuelTransaction(Long id, Vehicle vehicle, double pumpedLiters, double amount, double remainingQuota, LocalDateTime transactionDate, FuelStation station) {
        this.id = id;
        this.vehicle = vehicle;
        this.pumpedLiters = pumpedLiters;
        this.amount = amount;
        this.remainingQuota = remainingQuota;
        this.transactionDate = transactionDate;
        this.station = station;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public double getPumpedLiters() {
        return pumpedLiters;
    }

    public void setPumpedLiters(double pumpedLiters) {
        this.pumpedLiters = pumpedLiters;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRemainingQuota() {
        return remainingQuota;
    }

    public void setRemainingQuota(double remainingQuota) {
        this.remainingQuota = remainingQuota;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public FuelStation getStation() {
        return station;
    }

    public void setStation(FuelStation station) {
        this.station = station;
    }
}
