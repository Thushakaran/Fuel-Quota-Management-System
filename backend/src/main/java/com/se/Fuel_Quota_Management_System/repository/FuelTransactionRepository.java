package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelTransactionRepository extends JpaRepository<FuelTransaction, Long> {
    List<FuelTransaction> findByVehicleId(Long vehicleId);

    @Query("SELECT SUM(v.amount) FROM FuelTransaction v")
    double sumAmount();

    //    @Query("SELECT new com.se.Fuel_Quota_Management_System.dto.FuelTransactionDTO(" +
//            "ft.id, ft.vehicle.id, ft.amount, ft.transactionDate, ft.station.id) " +
//            "FROM FuelTransaction ft WHERE ft.vehicle.id = :vehicleId")
//    List<FuelTransactionDTO> findTransactionDetailsByVehicleId(Long vehicleId);
    @Modifying
    @Query("UPDATE FuelTransaction ft SET ft.station = NULL WHERE ft.station.id = :stationId")
    void detachTransactions(@Param("stationId") Long stationId);
}
