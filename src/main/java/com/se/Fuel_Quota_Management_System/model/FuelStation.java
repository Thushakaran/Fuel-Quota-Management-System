package com.se.Fuel_Quota_Management_System.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FuelStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Long stationId;

    private String stationName;

    private String registrationNumber;

    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loginid")
    private StationLog stationLog;

    // Store fuel types as a list of strings
    @OneToMany
    private List<FuelType> fuelTypes;

//    // One-to-Many relationship with FuelInventory
//    @OneToMany(mappedBy = "fuelStation", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<FuelInventory> fuelInventories = new ArrayList<>();

    // fuel station have only one owner
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private FuelStationOwner owner;


}
