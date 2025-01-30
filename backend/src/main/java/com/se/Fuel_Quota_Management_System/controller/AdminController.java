package com.se.Fuel_Quota_Management_System.controller;

import com.se.Fuel_Quota_Management_System.DTO.DashboardData;
import com.se.Fuel_Quota_Management_System.DTO.auth.RegisterRequest;
import com.se.Fuel_Quota_Management_System.model.FuelStation;
import com.se.Fuel_Quota_Management_System.model.FuelTransaction;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.se.Fuel_Quota_Management_System.security.JwtUtil;
import com.se.Fuel_Quota_Management_System.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    /** VEHICLE MANAGEMENT */
    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        logger.info("Fetching all vehicles");
        return ResponseEntity.ok(adminService.getAllVehicles());
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        logger.info("Fetching vehicle with ID: {}", id);
        return ResponseEntity.ok(adminService.getVehicleById(id));
    }

    @GetMapping("/vehicles/type/{vehicleType}")
    public ResponseEntity<List<Vehicle>> getVehiclesByType(@PathVariable String vehicleType) {
        logger.info("Fetching vehicles of type: {}", vehicleType);
        return ResponseEntity.ok(adminService.getVehiclesByType(vehicleType));
    }

    @GetMapping("/vehicles/owner/{ownerName}")
    public ResponseEntity<Vehicle> getVehiclesByOwner(@PathVariable String ownerName) {
        logger.info("Fetching vehicle owned by: {}", ownerName);
        return ResponseEntity.ok(adminService.getVehiclesByOwner(ownerName)
                .orElseThrow(() -> new RuntimeException("No vehicle found for owner: " + ownerName)));
    }

    @PutMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle updatedVehicle) {
        logger.info("Updating vehicle with ID: {}", id);
        return ResponseEntity.ok(adminService.updateVehicle(id, updatedVehicle));
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long id) {
        logger.info("Deleting vehicle with ID: {}", id);
        adminService.deleteVehicle(id);
        return ResponseEntity.ok("Vehicle with ID " + id + " deleted successfully.");
    }

    /**  FUEL STATION MANAGEMENT  */
    @GetMapping("/stations")
    public ResponseEntity<List<FuelStation>> getAllFuelStations() {
        logger.info("Fetching all fuel stations");
        return ResponseEntity.ok(adminService.getAllFuelStations());
    }

    @GetMapping("/stations/{id}")
    public ResponseEntity<FuelStation> getFuelStationById(@PathVariable Long id) {
        logger.info("Fetching fuel station with ID: {}", id);
        return ResponseEntity.ok(adminService.getFuelStationById(id));
    }

    @GetMapping("/station/owner/{ownerId}")
    public ResponseEntity<FuelStation> getFuelStationByOwnerId(@PathVariable Long ownerId) {
        logger.info("Fetching fuel station owned by ID: {}", ownerId);
        return ResponseEntity.ok(adminService.getFuelStationByOwnerId(ownerId));
    }

    @GetMapping("/station/location/{location}")
    public ResponseEntity<FuelStation> getFuelStationByLocation(@PathVariable String location) {
        logger.info("Fetching fuel station at location: {}", location);
        return ResponseEntity.ok(adminService.getFuelStationByLocation(location));
    }

    @PutMapping("/station/{id}")
    public ResponseEntity<FuelStation> updateFuelStation(@PathVariable Long id, @RequestBody FuelStation updatedFuelStation) {
        logger.info("Updating fuel station with ID: {}", id);
        return ResponseEntity.ok(adminService.updateFuelStation(id, updatedFuelStation));
    }

    @DeleteMapping("/station/{id}")
    public ResponseEntity<String> deleteFuelStation(@PathVariable Long id) {
        logger.info("Deleting fuel station with ID: {}", id);
        adminService.deleteFuelStation(id);
        return ResponseEntity.ok("Fuel station with ID " + id + " deleted successfully.");
    }


    /** TRANSACTION MANAGEMENT */
    @GetMapping("/transactions")
    public ResponseEntity<List<FuelTransaction>> getFuelTransactions() {
        logger.info("Fetching all fuel transactions");
        return ResponseEntity.ok(adminService.getFuelTransactions());
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<FuelTransaction> getTransactionById(@PathVariable Long id) {
        logger.info("Fetching transaction with ID: {}", id);
        return ResponseEntity.ok(adminService.getTransactionById(id));
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<FuelTransaction> updateFuelTransaction(@PathVariable Long id, @RequestBody FuelTransaction updatedFuelTransaction) {
        logger.info("Updating transaction with ID: {}", id);
        return ResponseEntity.ok(adminService.updateFuelTransaction(id, updatedFuelTransaction));
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        logger.info("Deleting transaction with ID: {}", id);
        adminService.deleteTransaction(id);
        return ResponseEntity.ok("Fuel transaction with ID " + id + " deleted successfully.");
    }


    /** DASHBOARD DATA */

    @GetMapping("/dashboard-data")
    public ResponseEntity<DashboardData> getDashboardData() {
        logger.info("Fetching dashboard data");
        return ResponseEntity.ok(adminService.getDashboardData());
    }

    /** ADMIN REGISTRATION */
    //for if we want to add new admin, but not through website
    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@Validated @RequestBody RegisterRequest registerRequest) {
        logger.info("Registering new admin: {}", registerRequest.getUserName());
        try {
            ResponseEntity<?> response = adminService.registerAdmin(registerRequest);
            String token = jwtUtil.generateToken(registerRequest.getUserName());
            return ResponseEntity.ok(Map.of("ADMIN", response.getBody(), "token", token));
        } catch (Exception e) {
            logger.error("Error during admin registration: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}