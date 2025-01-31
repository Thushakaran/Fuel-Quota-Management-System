package com.se.Fuel_Quota_Management_System.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuelFilling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private FuelStation fuelStation;

    @Column(nullable = false)
    private double quantityAdded;

    @Column(nullable = false)
    private String fuelType;

    @Column(nullable = false)
    private LocalDateTime fillingTime;


    public FuelFilling(FuelStation fuelStation,
                       String fuelType,
                       Double quantityToAdd,
                       LocalDateTime now) {
        this.fuelStation = fuelStation;
        this.fuelType = fuelType;
        this.quantityAdded = quantityToAdd;
        this.fillingTime=now;
    }
}
