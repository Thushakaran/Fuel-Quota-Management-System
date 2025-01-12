import React, { useState, useEffect } from "react";
import axios from "../api/axiosInstance";
import FuelStationTable from "./FuelStationTable";
import AdminNavbar from "./AdminNavbar";
import AdminFooter from "./AdminFooter";
// import EditFuelStationModal from "./EditFuelStationModal";

const FuelStationManagement = () => {
    const [fuelStations, setFuelStations] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [editingFuelStation, setEditingFuelStation] = useState(null);

    useEffect(() => {
        loadFuelStations();
    }, []);

    const loadFuelStations = () => {
        setLoading(true);
        axios
            .get("/admin/station") // Update to match your backend route
            .then((response) => {
                setFuelStations(response.data || []);
                setLoading(false);
            })
            .catch(() => {
                setError("Failed to load fuel stations");
                setLoading(false);
            });
    };

    const handleEdit = (fuelStation) => {
        setEditingFuelStation(fuelStation);
    };

    const handleCloseModal = () => {
        setEditingFuelStation(null);
    };

    const handleUpdate = () => {
        loadFuelStations(); // Reload fuel stations after an update
    };

    const deleteFuelStation = (fuelStationId) => {
        setLoading(true);
        axios
            .delete(`/admin/station/${fuelStationId}`) // Backend delete endpoint
            .then(() => {
                setFuelStations((prevStations) =>
                    prevStations.filter((station) => station.id !== fuelStationId)
                );
                setLoading(false);
                alert("Fuel station deleted successfully");
            })
            .catch(() => {
                setError("Failed to delete fuel station");
                setLoading(false);
            });
    };

    if (loading) return <div>Loading...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div>
            <AdminNavbar/>
            <div className="container mt-5">
                <h2>Manage Fuel Stations</h2>
                <FuelStationTable
                    fuelStations={fuelStations}
                    onEdit={handleEdit} // Pass edit handler to the table
                    onDelete={deleteFuelStation} // Pass delete handler to the table
                />
                {/* {editingFuelStation && (
                    <EditFuelStationModal
                        fuelStation={editingFuelStation}
                        onClose={handleCloseModal}
                        onUpdate={handleUpdate}
                    />
                )} */}
            </div>
            <AdminFooter/>
        </div>
    );
};

export default FuelStationManagement;
