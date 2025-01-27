import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useParams, useNavigate } from "react-router-dom";
import { editStationDetails, getStationDetailById } from "../api/FuelStationServiceApi";

const DEFAULT_FUEL_TYPES = ["92-Octane", "95-Octane", "Super Diesel", "Auto Diesel"];

const StationProfile = () => {
  const [station, setStation] = useState({
    stationName: "",
    location: "",
    registrationNumber: "",
    fuelInventory: {},
    owner: {},
    contactNumber: "",
  });

  const { id } = useParams();
  const navigate = useNavigate();
  const [isEditing, setIsEditing] = useState(false);
  const [originalStation, setOriginalStation] = useState({});
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const fetchStationData = async () => {
    setIsLoading(true);
    try {
      const response = await getStationDetailById(id);
      const data = response.data;

      // Filter out any fuel types with a quantity of 0 or undefined
      const fuelInventory = Object.fromEntries(
        Object.entries(data.fuelInventory).filter(([_, quantity]) => quantity > 0)
      );

      setStation({ ...data, fuelInventory });
      setOriginalStation({ ...data, fuelInventory });
      setError(null);
    } catch (error) {
      console.error("Error fetching station details:", error);
      setError("Failed to fetch station details.");
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchStationData();
  }, [id]);

  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleSaveClick = async () => {
    if (!station.stationName || !station.location || !station.registrationNumber) {
      alert("Please fill in all required fields before saving.");
      return;
    }

    try {
      if (window.confirm("Are you sure you want to update the station profile?")) {
        // Filter out any fuel types with a quantity of 0 before saving
        const fuelInventory = Object.fromEntries(
          Object.entries(station.fuelInventory).filter(([_, quantity]) => quantity > 0)
        );

        await editStationDetails(id, { ...station, fuelInventory });
        alert("Station profile updated successfully!");
        setIsEditing(false);
        setOriginalStation(station);
      }
    } catch (error) {
      console.error("Error updating station profile:", error);
      setError("Failed to update station profile.");
    }
  };

  const handleCancelClick = () => {
    setStation({ ...originalStation });
    setIsEditing(false);
  };

  const handleFuelCheckboxChange = (fuelType) => {
    setStation((prevState) => {
      const updatedFuelInventory = { ...prevState.fuelInventory };
      if (updatedFuelInventory[fuelType]) {
        // If already selected, unselect it (remove fuel type from inventory)
        delete updatedFuelInventory[fuelType];
      } else {
        // If not selected, add it with a default quantity (e.g., 1000 liters)
        updatedFuelInventory[fuelType] = 1000;
      }
      return { ...prevState, fuelInventory: updatedFuelInventory };
    });
  };

  const handleFuelQuantityChange = (fuelType, value) => {
    setStation((prevState) => {
      const updatedFuelInventory = { ...prevState.fuelInventory };
      if (value <= 0) {
        // If value is less than or equal to 0, remove the fuel type from inventory
        delete updatedFuelInventory[fuelType];
      } else {
        // Otherwise, update the fuel quantity
        updatedFuelInventory[fuelType] = Number(value);
      }
      return { ...prevState, fuelInventory: updatedFuelInventory };
    });
  };

  const isSaveDisabled = JSON.stringify(station) === JSON.stringify(originalStation);

  return (
    <div className="container mt-5">
      <div className="text-center mb-4">
        <h1 className="fw-bold">Station Profile</h1>
        <p className="text-muted">Update the station's details</p>
      </div>

      {isLoading ? (
        <div className="text-center">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
          <p>Loading station details...</p>
        </div>
      ) : (
        <div className="row justify-content-center">
          <div className="col-md-6">
            <div className="card shadow-sm">
              <div className="card-header bg-primary text-white">
                <h3>Station Details</h3>
              </div>
              <div className="card-body">
                {error && <div className="alert alert-danger">{error}</div>}

                <form>
                  <div className="mb-3">
                    <label htmlFor="stationName" className="form-label">Station Name</label>
                    <input
                      type="text"
                      id="stationName"
                      className={`form-control ${!isEditing && "bg-light"}`}
                      value={station.stationName}
                      onChange={(e) =>
                        setStation({ ...station, stationName: e.target.value })
                      }
                      readOnly={!isEditing}
                    />
                  </div>

                  <div className="mb-3">
                    <label htmlFor="location" className="form-label">Location</label>
                    <input
                      type="text"
                      id="location"
                      className={`form-control ${!isEditing && "bg-light"}`}
                      value={station.location}
                      onChange={(e) =>
                        setStation({ ...station, location: e.target.value })
                      }
                      readOnly={!isEditing}
                    />
                  </div>

                  <div className="mb-3">
                    <label htmlFor="registrationNumber" className="form-label">Registration Number</label>
                    <input
                      type="text"
                      id="registrationNumber"
                      className={`form-control ${!isEditing && "bg-light"}`}
                      value={station.registrationNumber}
                      onChange={(e) =>
                        setStation({ ...station, registrationNumber: e.target.value })
                      }
                      readOnly={!isEditing}
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Fuel Inventory</label>
                    {Object.keys(station.fuelInventory).length === 0 ? (
                      <p>No fuel types available for this station.</p>
                    ) : (
                      Object.keys(station.fuelInventory).map((fuelType) => (
                        <div className="form-check mb-2" key={fuelType}>
                          <input
                            className="form-check-input"
                            type="checkbox"
                            id={fuelType}
                            checked={station.fuelInventory[fuelType] > 0}
                            onChange={() => handleFuelCheckboxChange(fuelType)}
                            disabled={!isEditing}
                          />
                          <label
                            className="form-check-label d-flex justify-content-between align-items-center"
                            htmlFor={fuelType}
                          >
                            {fuelType}
                            <input
                              type="number"
                              className="form-control ms-3"
                              style={{ maxWidth: "100px" }}
                              value={station.fuelInventory[fuelType] || ""}
                              onChange={(e) =>
                                handleFuelQuantityChange(fuelType, e.target.value)
                              }
                              disabled={!isEditing || station.fuelInventory[fuelType] === 0}
                            />
                          </label>
                        </div>
                      ))
                    )}
                  </div>

                  <div className="mt-4">
                    {isEditing ? (
                      <>
                        <button
                          type="button"
                          className="btn btn-success w-100 mb-2"
                          onClick={handleSaveClick}
                          disabled={isSaveDisabled}
                        >
                          Save Changes
                        </button>
                        <button
                          type="button"
                          className="btn btn-secondary w-100"
                          onClick={handleCancelClick}
                        >
                          Cancel
                        </button>
                      </>
                    ) : (
                      <>
                        <button
                          type="button"
                          className="btn btn-primary w-100 mb-2"
                          onClick={handleEditClick}
                        >
                          Edit Profile
                        </button>
                        <button
                          type="button"
                          className="btn btn-secondary w-100"
                          onClick={() => navigate(-1)}
                        >
                          Back
                        </button>
                      </>
                    )}
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default StationProfile;
