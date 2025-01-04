import React, { useState, useEffect } from "react";
import axios from "../api/axiosInstance";
import VehicleTable from "./VehicleTable";
import EditVehicleModal from "./EditVehicleModal";

const VehicleManagement = () => {
    const [vehicles, setVehicles] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [editingVehicle, setEditingVehicle] = useState(null);

    useEffect(() => {
        loadVehicles();
    }, []);

    const loadVehicles = () => {
        setLoading(true);
        axios
            .get("/admin/vehicles") // Update to match your backend route
            .then((response) => {
                setVehicles(response.data || []);
                setLoading(false);
            })
            .catch(() => {
                setError("Failed to load vehicles");
                setLoading(false);
            });
    };

    const handleEdit = (vehicle) => {
        setEditingVehicle(vehicle);
    };

    const handleCloseModal = () => {
        setEditingVehicle(null);
    };

    const handleUpdate = () => {
        loadVehicles(); // Reload vehicles after an update
    };

    const deleteVehicle = (vehicleId) => {
        setLoading(true);
        axios
            .delete(`/admin/vehicles/${vehicleId}`) // Backend delete endpoint
            .then(() => {
                setVehicles((prevVehicles) =>
                    prevVehicles.filter((vehicle) => vehicle.id !== vehicleId)
                );
                setLoading(false);
                alert("Vehicle deleted successfully");
            })
            .catch(() => {
                setError("Failed to delete vehicle");
                setLoading(false);
            });
    };

    if (loading) return <div>Loading...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div className="container mt-5 ">
            <h2>Manage Vehicles</h2>
            <VehicleTable
                vehicles={vehicles}
                onEdit={handleEdit} // Pass edit handler to the table
                onDelete={deleteVehicle} // Pass delete handler to the table
            />
            {editingVehicle && (
                <EditVehicleModal
                    vehicle={editingVehicle}
                    onClose={handleCloseModal}
                    onUpdate={handleUpdate}
                />
            )}
        </div>
    );
};

export default VehicleManagement;