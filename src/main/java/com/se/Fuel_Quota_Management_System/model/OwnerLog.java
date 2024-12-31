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

    @Column(nullable = true, unique = true) // Unique username
    private String ownerUserName;

    @Column(nullable = true)
    private String password;
}
