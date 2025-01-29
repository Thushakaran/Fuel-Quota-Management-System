package com.se.Fuel_Quota_Management_System.Seeder;

import com.se.Fuel_Quota_Management_System.model.FuelType;
import com.se.Fuel_Quota_Management_System.repository.FuelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FuelTypeSeeder implements CommandLineRunner {
    @Autowired
    private FuelTypeRepository fuelTypeRepository;

    @Override
    public void run(String... args) {
        //input the default fuel types
        List<FuelType> fuelTypes = Arrays.asList(
                new FuelType("Petrol 92-Octane"),
                new FuelType("Petrol 95-Octane"),
                new FuelType("Auto Diesel"),
                new FuelType("Super Diesel")
        );

        //if the FuelType table is empty then save fuel types
        if (fuelTypeRepository.count() == 0) {
            fuelTypeRepository.saveAll(fuelTypes);
        }
    }
}
