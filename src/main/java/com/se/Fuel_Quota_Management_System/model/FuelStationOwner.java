package com.se.Fuel_Quota_Management_System.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


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
    @JoinColumn(name = "loginid", nullable = false)
    @JsonIgnore
    private UserLog ownerLog;

}