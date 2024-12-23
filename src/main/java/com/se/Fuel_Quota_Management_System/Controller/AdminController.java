package com.se.Fuel_Quota_Management_System.Controller;

import com.se.Fuel_Quota_Management_System.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AdminController
{
    @Autowired
    private AdminService adminService;



    /*@PostMapping(\"/add\")
            public Admin_addAdmin(@RequestBody Admin_admin) {
            return adminService.addAdmin(admin);
            }

            @GetMapping(\"/list\")
                    public List<Admin> getAllAdmins() {
                    return adminService.getAllAdmins();
                    }

                    @DeleteMapping(\"/delete/{id}\")
                            public String deleteAdmin(@PathVariable Long id) {
                            adminService.deleteAdmin(id);
                            return \"Admin deleted successfully.\";
                            }*/







}
