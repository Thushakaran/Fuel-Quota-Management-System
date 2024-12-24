package com.se.Fuel_Quota_Management_System.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Entity
public class FuelStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stationName;

    private String registrationNumber;

    private String location;

    @ManyToOne
    private FuelStationOwner owner;

    @OneToMany(mappedBy = "fuelStation")
    private List<Vehicle> vehicles;

    @ElementCollection
    @CollectionTable(name = "fuel_station_inventory", joinColumns = @JoinColumn(name = "fuel_station_id"))
    @MapKeyColumn(name = "fuel_type")
    @Column(name = "available_fuel")
    private Map<String, Double> fuelInventory; // Key: Fuel type (e.g., Petrol, Diesel), Value: Quantity available
}
