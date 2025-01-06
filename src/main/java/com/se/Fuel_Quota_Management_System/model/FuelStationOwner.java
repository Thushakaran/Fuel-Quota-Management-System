package com.se.Fuel_Quota_Management_System.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuelStationOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nicNo;
    private String phoneNumber;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loginid")
    @JsonManagedReference
    @JsonIgnore
    private OwnerLog ownerLog;

    @OneToMany
    private List<FuelStation> fuelStations;

}