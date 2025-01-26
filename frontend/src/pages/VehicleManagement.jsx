import React, { useState, useEffect } from "react";
import axios from "../api/axiosInstance";
import VehicleTable from "../components/VehicleTable";
import EditVehicleModal from "../components/EditVehicleModal";
import AdminNavbar from "../components/AdminNavbar";
import AdminFooter from "../components/AdminFooter";

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
            .get("/admin/vehicles") // API call to fetch vehicle data
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
        setEditingVehicle(vehicle); // Open modal with selected vehicle data
    };

    const handleCloseModal = () => {
        setEditingVehicle(null); // Close the modal
    };

    const handleUpdate = (updatedVehicle) => {
        setLoading(true);
        axios
            .put(`/admin/vehicles/${updatedVehicle.id}`, updatedVehicle) // API call to update the vehicle
            .then(() => {
                setVehicles((prevVehicles) =>
                    prevVehicles.map((vehicle) =>
                        vehicle.id === updatedVehicle.id ? updatedVehicle : vehicle
                    )
                );
                setEditingVehicle(null); // Close modal after updating
                setLoading(false);
            })
            .catch(() => {
                setError("Failed to update vehicle");
                setLoading(false);
            });
    };

    const deleteVehicle = (vehicleId) => {
        setLoading(true);
        axios
            .delete(`/admin/vehicles/${vehicleId}`) // API call to delete vehicle
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
        <div>
            <AdminNavbar />
            <div className="container mt-5">
                <h2>Manage Vehicles</h2>
                <VehicleTable
                    vehicles={vehicles}
                    onEdit={handleEdit} // Pass edit handler to table
                    onDelete={deleteVehicle} // Pass delete handler to table
                />
                {editingVehicle && (
                    <EditVehicleModal
                        vehicle={editingVehicle}
                        onSave={handleUpdate} // Use handleUpdate to save changes
                        onCancel={handleCloseModal} // Close modal on cancel
                    />
                )}
            </div>
            <AdminFooter />
        </div>
    );
};

export default VehicleManagement;
