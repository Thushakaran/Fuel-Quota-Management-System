package com.se.Fuel_Quota_Management_System.model;

import jakarta.persistence.*;


@Entity
public class DmtVehicle {
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

    public Long getId() {
        return id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
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

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

//    public DmtVehicle(Long id, String vehicleNumber, String chassisNumber, String vehicleType, String ownerName, String ownerIcNumber, String fuelType) {
//        this.id = id;
//        this.vehicleNumber = vehicleNumber;
//        this.chassisNumber = chassisNumber;
//        this.vehicleType = vehicleType;
//        this.ownerName = ownerName;
//        this.ownerIcNumber = ownerIcNumber;
//        this.fuelType = fuelType;
//    }

}
