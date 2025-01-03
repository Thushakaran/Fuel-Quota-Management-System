package com.se.Fuel_Quota_Management_System.Configuration;

import com.se.Fuel_Quota_Management_System.model.FuelType;
import com.se.Fuel_Quota_Management_System.repository.FuelTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class FuelTypeConfig {

    @Bean
    CommandLineRunner initFuelTypes(FuelTypeRepository fuelTypeRepository) {
        // default inputs for the FuelType Table
        return args -> {
            List<FuelType> fuelTypes = Arrays.asList(
                    new FuelType("Petrol 92-Octane"),
                    new FuelType("Petrol 95-Octane"),
                    new FuelType("Auto Diesel"),
                    new FuelType("Super Disel")
            );

            // Save the fuel type, if no data in FuelType Table
            if (fuelTypeRepository.count() == 0) {
                fuelTypeRepository.saveAll(fuelTypes);
            }
        };
    }
}
