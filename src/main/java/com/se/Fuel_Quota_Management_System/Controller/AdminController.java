package com.se.Fuel_Quota_Management_System.Controller;


    // AdminController.java


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fuelallocation.model.Admin;
import com.fuelallocation.service.AdminService;

import java.util.List;

    @RestController
    @RequestMapping("/api/admin")
    public class AdminController {

        @Autowired
        private AdminService adminService;

        @PostMapping("/add")
        public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
            return ResponseEntity.ok(adminService.addAdmin(admin));
        }

        @GetMapping("/list")
        public ResponseEntity<List<Admin>> getAllAdmins() {
            return ResponseEntity.ok(adminService.getAllAdmins());
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
            adminService.deleteAdmin(id);
            return ResponseEntity.ok("Admin deleted successfully.");
        }

    }

// Admin.java
package com.fuelallocation.model;

import jakarta.persistence.*;

    @Entity
    public class Admin {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String email;
        private String password;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

// AdminRepository.java
package com.fuelallocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fuelallocation.model.Admin;

    @Repository
    public interface AdminRepository extends JpaRepository<Admin, Long> {
    }

// AdminService.java
package com.fuelallocation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fuelallocation.model.Admin;
import com.fuelallocation.repository.AdminRepository;

import java.util.List;

    @Service
    public class AdminService {

        @Autowired
        private AdminRepository adminRepository;

        public Admin addAdmin(Admin admin) {
            return adminRepository.save(admin);
        }

        public List<Admin> getAllAdmins() {
            return adminRepository.findAll();
        }

        public void deleteAdmin(Long id) {
            adminRepository.deleteById(id);
        }
    }

}
