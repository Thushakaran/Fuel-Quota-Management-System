package com.se.Fuel_Quota_Management_System.repository;

import com.se.Fuel_Quota_Management_System.model.FuelFilling;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelFillingRepository extends JpaRepository<FuelFilling , Long> {
    @Query("SELECT ff FROM FuelFilling ff WHERE ff.fuelStation.id = :fuelStationId")
    public List<FuelFilling> findByFuelStationId(@Param("fuelStationId") Long fuelStationId);

}
