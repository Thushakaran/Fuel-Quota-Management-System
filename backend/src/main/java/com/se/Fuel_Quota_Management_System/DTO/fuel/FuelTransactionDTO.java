package com.se.Fuel_Quota_Management_System.DTO.fuel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuelTransactionDTO {
    private Long id;
    private Long vehicleId;
    private Double amount;
    private LocalDateTime transactionDate;
    private Long stationId;


}
