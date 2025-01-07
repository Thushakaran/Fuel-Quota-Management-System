package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.model.OwnerLog;
import com.se.Fuel_Quota_Management_System.model.StationLog;
import com.se.Fuel_Quota_Management_System.repository.StationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StationLogService {
    @Autowired
    private StationLogRepository stationlogrepository;

    public StationLog register(StationLog stationLog) {
        stationLog.setPassword(new BCryptPasswordEncoder().encode( stationLog.getPassword()));
        return stationlogrepository.save( stationLog);
    }

    public boolean authenticate(String username, String password) {
        Optional<OwnerLog> user = stationlogrepository.findByStationUserName(username);
        return user.isPresent() && new BCryptPasswordEncoder().matches(password, user.get().getPassword());
    }
}
