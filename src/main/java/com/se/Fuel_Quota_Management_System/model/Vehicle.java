package com.se.Fuel_Quota_Management_System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


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

    private String notificationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fuel_station_id")
    private FuelStation fuelStation;

    @ManyToOne
    @JoinColumn(name = "vehicle_owner_id")
    private VehicleOwner vehicleOwner; // Link to the VehicleOwner entity


    public Long getId() {
        return id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerIcNumber() {
        return ownerIcNumber;
    }

    public String getFuelType() {
        return fuelType;
    }

    public double getFuelQuota() {
        return fuelQuota;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public FuelStation getFuelStation() {
        return fuelStation;
    }

    public VehicleOwner getVehicleOwner() {
        return vehicleOwner;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setOwnerIcNumber(String ownerIcNumber) {
        this.ownerIcNumber = ownerIcNumber;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setFuelQuota(double fuelQuota) {
        this.fuelQuota = fuelQuota;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public void setFuelStation(FuelStation fuelStation) {
        this.fuelStation = fuelStation;
    }

    public void setVehicleOwner(VehicleOwner vehicleOwner) {
        this.vehicleOwner = vehicleOwner;
    }
}
