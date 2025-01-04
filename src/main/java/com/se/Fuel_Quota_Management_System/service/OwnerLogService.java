package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.model.OwnerLog;
import com.se.Fuel_Quota_Management_System.repository.OwnerLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerLogService {
    @Autowired
    private OwnerLogRepository ownerlogrepository;

    public OwnerLog register(OwnerLog ownerLog) {
        ownerLog.setPassword(new BCryptPasswordEncoder().encode(ownerLog.getPassword()));
        return ownerlogrepository.save(ownerLog);
    }

    public ResponseEntity<?> authenticate(String username, String password) {
        Optional<OwnerLog> user = ownerlogrepository.findByOwnerUserName(username);
        if(user.isPresent() && new BCryptPasswordEncoder().matches(password, user.get().getPassword())){
            return ResponseEntity.ok(user);
        }else {
            return ResponseEntity.ok("UserName or PAssword incorrect");
        }
    }


}
