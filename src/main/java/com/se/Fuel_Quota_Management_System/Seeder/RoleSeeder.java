package com.se.Fuel_Quota_Management_System.Seeder;

import com.se.Fuel_Quota_Management_System.model.Role;
import com.se.Fuel_Quota_Management_System.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeder implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(null, "admin"));
            roleRepository.save(new Role(null, "station"));
            roleRepository.save(new Role(null, "stationowner"));
        }
    }
}