package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.FuelStationOwner;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
//import com.se.Fuel_Quota_Management_System.model.VehicleOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByVehicleNumber(String vehicleNumber);

    Optional<Vehicle> findByOwnerName(String ownerName);

    Optional<Vehicle> findById(Long id);

    List<Vehicle> findByVehicleType(String vehicleType);

    @Query("SELECT SUM(v.fuelQuota) FROM Vehicle v")
    double sumFuelQuota();

    Vehicle findVehicleByOwnerLogId(Long loginid);

//    public List<Vehicle> findByOwner(VehicleOwner owner);

//    List<Vehicle> findByOwnerId(Long ownerId);


//    @Query("SELECT COUNT(v) FROM Transaction t WHERE t.status = 'ACTIVE'")
//    long countActiveTransactions();
}
