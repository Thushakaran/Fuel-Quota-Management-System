package com.se.Fuel_Quota_Management_System.model;

import jakarta.persistence.*;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FuelStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String stationName;
@Column
    private String registrationNumber;

    private String address;

    // Store fuel types as a list of strings
    private List<String> fuelTypes;

    // government or private 
    private String ownedType;

    // fuel station have only one owner
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private FuelStationOwner owner;

    public FuelStationOwner getOwner() {
        return owner;
    }

    public void setOwner(FuelStationOwner owner) {
        this.owner = owner;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getStationName() {
        return stationName;
    }
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

// Repeat for other fields

    @ElementCollection
    @CollectionTable(name = "fuel_station_inventory", joinColumns = @JoinColumn(name = "fuel_station_id"))
    @MapKeyColumn(name = "fuel_type")
    @Column(name = "available_fuel")
    private Map<String, Double> fuelInventory; // Key: Fuel type (e.g., Petrol, Diesel), Value: Quantity available

}
