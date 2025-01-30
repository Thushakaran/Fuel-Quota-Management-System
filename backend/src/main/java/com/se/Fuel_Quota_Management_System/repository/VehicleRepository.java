package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.Vehicle;
//import com.se.Fuel_Quota_Management_System.model.VehicleOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByVehicleNumber(String vehicleNumber);

    Optional<Vehicle> findByOwnerName(String ownerName);

    Optional<Vehicle> findById(Long id);

    List<Vehicle> findByVehicleType(String vehicleType);

    Vehicle findVehicleByOwnerLogId(Long loginid);

    // Custom query to fetch fuel type by vehicleId
    @Query("SELECT v.fuelType FROM Vehicle v WHERE v.id = :vehicleId")
    Optional<String> findFuelTypeByVehicleId(@Param("vehicleId") Long vehicleId);

    Optional<Vehicle> findByQrCodeId(String qrCodeId);

}
