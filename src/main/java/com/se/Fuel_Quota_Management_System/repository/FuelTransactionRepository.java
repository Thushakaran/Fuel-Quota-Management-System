package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuelTransactionRepository extends JpaRepository<FuelTransaction, Long> {
    List<FuelTransaction> findByVehicleId(Long vehicleId);

    @Query("SELECT SUM(v.amount) FROM FuelTransaction v")
    double sumAmount();
}
