package com.se.Fuel_Quota_Management_System.service;


import com.se.Fuel_Quota_Management_System.model.Admin;
import com.se.Fuel_Quota_Management_System.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
    public class AdminService {
        @Autowired
        AdminRepository adminRepository;

    public Admin addAdmin(Admin admin) {
        return (Admin) adminRepository.save(admin);
    }
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    /*public Admin getAdminById(Long id) {

    }*/
    }

