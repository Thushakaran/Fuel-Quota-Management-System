package com.se.Fuel_Quota_Management_System.DTO.auth;


import com.se.Fuel_Quota_Management_System.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Role role;

    private Long id;
}
