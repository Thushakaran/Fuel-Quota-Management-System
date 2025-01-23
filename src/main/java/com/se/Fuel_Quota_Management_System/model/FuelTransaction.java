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
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    //    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "vehicle_id")
    private Long vehicleId;


    private double amount;


    private LocalDateTime transactionDate = LocalDateTime.now();

//    @ManyToMany
//    @JoinColumn(name = "station_id")


    private Long stationId;

    public FuelTransaction() {
    }


    public FuelTransaction(Long id, Long vehicleId, double amount, LocalDateTime transactionDate, Long stationId) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.stationId = stationId;
    }

    public FuelTransaction(Long stationId, Long vehicleId, double amount, LocalDateTime now) {
        this.vehicleId = vehicleId;
        this.amount = amount;
        this.transactionDate = now;
        this.stationId = stationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }
}
