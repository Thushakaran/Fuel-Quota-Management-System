package com.se.Fuel_Quota_Management_System.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Entity
@Data
public class CPST_Stations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stationName;

    private String registrationNumber;

    private String location;

    private String owner;

}

