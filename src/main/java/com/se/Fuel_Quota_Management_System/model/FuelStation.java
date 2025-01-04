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

//    // Helper methods for managing fuel inventories
//    public void addFuelInventory(FuelInventory inventory) {
//        fuelInventories.add(inventory);
//        inventory.setFuelStation(this);
//    }
//
//    public void removeFuelInventory(FuelInventory inventory) {
//        fuelInventories.remove(inventory);
//        inventory.setFuelStation(null);
//    }
//
//    public void fillFuelForType(FuelType fuelType, double quantity) {
//        for (FuelInventory inventory : fuelInventories) {
//            if (inventory.getFuelType().equals(fuelType)) {
//                inventory.fillFuel(quantity);
//                return;
//            }
//        }
//        throw new IllegalArgumentException("Fuel type not found in inventory.");
//    }
//
//    public void distributeFuelForType(FuelType fuelType, double quantity) {
//        for (FuelInventory inventory : fuelInventories) {
//            if (inventory.getFuelType().equals(fuelType)) {
//                inventory.distributeFuel(quantity);
//                return;
//            }
//        }
//        throw new IllegalArgumentException("Fuel type not found in inventory.");
//    }

}
