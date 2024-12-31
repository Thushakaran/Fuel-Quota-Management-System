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
    @Column(name = "station_id")
    private Long stationId;

    private String stationName;

    private String registrationNumber;

    private String address;

    // Store fuel types as a list of strings
    @OneToMany
    private List<FuelType> fuelTypes;

    // fuel station have only one owner
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private FuelStationOwner owner;

}
