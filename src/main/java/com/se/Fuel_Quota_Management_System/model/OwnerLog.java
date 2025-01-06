package com.se.Fuel_Quota_Management_System.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ownerUserName;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "ownerLog", cascade = CascadeType.ALL)
    @JsonIgnore

    @JsonBackReference
    private FuelStationOwner owner; // Reverse mapping
}

