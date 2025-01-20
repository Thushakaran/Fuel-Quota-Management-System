package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.model.UserLog;
import com.se.Fuel_Quota_Management_System.repository.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private  UserLogRepository userlogRepository;


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserLog user = userlogRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Name Not Found"));

        // Wrap the single role in a SimpleGrantedAuthority
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                Collections.singletonList(authority) // Use a single-element list
        );
    }
}

