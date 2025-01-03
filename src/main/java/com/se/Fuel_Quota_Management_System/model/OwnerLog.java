package com.se.Fuel_Quota_Management_System.model;

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
    private FuelStationOwner owner; // Reverse mapping
}

