import React, { useState, useEffect } from "react";
import axios from "../api/axiosInstance";
import FuelStationTable from "../components/FuelStationTable";
import AdminNavbar from "../components/AdminNavbar";
import AdminFooter from "../components/AdminFooter";
import EditFuelStationModal from "../components/EditFuelStationModal";

const FuelStationManagement = () => {
  const [fuelStations, setFuelStations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [editingStation, setEditingStation] = useState(null); // Track the station being edited

  useEffect(() => {
    loadFuelStations();
  }, []);

  // Load fuel stations from the backend
  const loadFuelStations = () => {
    setLoading(true);
    axios
      .get("/admin/station")
      .then((response) => {
        setFuelStations(response.data || []);
        setLoading(false);
      })
      .catch(() => {
        setError("Failed to load fuel stations");
        setLoading(false);
      });
  };

  const updateFuelStation = (updatedStation) => {
    setLoading(true);
    axios
      .put(`/admin/station/${updatedStation.id}`, updatedStation)
      .then(() => {
        setFuelStations((prevStations) =>
          prevStations.map((station) =>
            station.id === updatedStation.id ? updatedStation : station
          )
        );
        setEditingStation(null); // Close the modal after a successful update
        setLoading(false);
      })
      .catch(() => {
        setError("Failed to update fuel station");
        setLoading(false);
      });
  };

  // Delete a fuel station
  const deleteFuelStation = (fuelStationId) => {
    setLoading(true);
    axios
      .delete(`/admin/station/${fuelStationId}`)
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
      <AdminNavbar />
      <div className="container mt-5">
        <h2>Manage Fuel Stations</h2>
        <FuelStationTable
          fuelStations={fuelStations}
          onDelete={deleteFuelStation}
          onEdit={setEditingStation} // Pass edit handler to the table
        />
        {editingStation && (
          <EditFuelStationModal
            station={editingStation}
            onSave={updateFuelStation}
            onCancel={() => setEditingStation(null)}
          />
        )}
      </div>
      <AdminFooter />
    </div>
  );
};

export default FuelStationManagement;
