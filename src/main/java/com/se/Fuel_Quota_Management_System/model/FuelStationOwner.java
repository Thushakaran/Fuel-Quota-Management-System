package com.se.Fuel_Quota_Management_System.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class FuelStationOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String password;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<FuelStation> registeredStations;

}
