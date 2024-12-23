import React, { useState } from "react";
import { registerVehicle } from "../api/vehicleApi";
import "bootstrap/dist/css/bootstrap.min.css"; // Import Bootstrap CSS

const VehicleRegistration = () => {
  const [formData, setFormData] = useState({
    vehicleNumber: "",
    ownerName: "",
    ownerIcNumber: "",
    vehicleType: "",
    chassisNumber: "",
    fuelType: "",
  });

  const [errorMessages, setErrorMessages] = useState({});
  const [successMessage, setSuccessMessage] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();

    const { vehicleNumber, ownerName, ownerIcNumber, vehicleType, chassisNumber, fuelType } = formData;
    const errors = {};

    // Individual field validation
    if (!vehicleNumber) errors.vehicleNumber = "Vehicle Number is required!";
    if (!ownerName) errors.ownerName = "Owner Name is required!";
    if (!ownerIcNumber) errors.ownerIcNumber = "Owner IC Number is required!";
    if (!vehicleType) errors.vehicleType = "Vehicle Type is required!";
    if (!chassisNumber) errors.chassisNumber = "Chassis Number is required!";
    if (!fuelType) errors.fuelType = "Fuel Type is required!";

    if (Object.keys(errors).length > 0) {
      setErrorMessages(errors);
      return;
    }

    setErrorMessages({});
    setSuccessMessage("");

    try {
      const result = await registerVehicle(formData);
      alert("Vehicle registered successfully!");
      console.log(result);
      alert(`QR Code: ${result.qrCode}, Fuel Quota: ${result.fuelQuota}`);
    } catch (error) {
      console.error("Error response:", error.response); // Log detailed error response
      const errorMessage = error.response?.data?.message || "Failed to register vehicle. Please try again.";
      setErrorMessages({ general: errorMessage }); // Set general error message
    }
  };

  return (
    <div className="container mt-5 bg-light p-4 rounded">
      <div className="header text-center mb-4 text-primary">
        <h2>Vehicle Registration</h2>
      </div>
      <form onSubmit={handleSubmit} className="form-container">
        <div className="form-group">
          <label>Vehicle Number</label>
          <input
            type="text"
            className="form-control"
            placeholder="Vehicle Number"
            value={formData.vehicleNumber}
            onChange={(e) => setFormData({ ...formData, vehicleNumber: e.target.value })}
          />
          {errorMessages.vehicleNumber && <div className="text-danger">{errorMessages.vehicleNumber}</div>}
        </div>

        <div className="form-group">
          <label>Owner Name</label>
          <input
            type="text"
            className="form-control"
            placeholder="Owner Name"
            value={formData.ownerName}
            onChange={(e) => setFormData({ ...formData, ownerName: e.target.value })}
          />
          {errorMessages.ownerName && <div className="text-danger">{errorMessages.ownerName}</div>}
        </div>

        <div className="form-group">
          <label>Owner IC Number</label>
          <input
            type="text"
            className="form-control"
            placeholder="Owner IC Number"
            value={formData.ownerIcNumber}
            onChange={(e) => setFormData({ ...formData, ownerIcNumber: e.target.value })}
          />
          {errorMessages.ownerIcNumber && <div className="text-danger">{errorMessages.ownerIcNumber}</div>}
        </div>

        <div className="form-group">
          <label>Chassis Number</label>
          <input
            type="text"
            className="form-control"
            placeholder="Chassis Number"
            value={formData.chassisNumber}
            onChange={(e) => setFormData({ ...formData, chassisNumber: e.target.value })}
          />
          {errorMessages.chassisNumber && <div className="text-danger">{errorMessages.chassisNumber}</div>}
        </div>

        <div className="form-group">
          <label>Vehicle Type</label>
          <select
            className="form-control"
            value={formData.vehicleType}
            onChange={(e) => setFormData({ ...formData, vehicleType: e.target.value })}
          >
            <option value="">Select Vehicle Type</option>
            <option value="Car">Car</option>
            <option value="Bike">Bike</option>
            <option value="Van">Van</option>
            <option value="Motorcycle">Motorcycle</option>
            <option value="Taxi">Taxi</option>
            <option value="Three-wheeler">Three-wheeler</option>
            <option value="Bus">Bus</option>
            <option value="Commercial Vehicle">Commercial Vehicle</option>
            <option value="Heavy Vehicles">Heavy Vehicles</option>
            <option value="Special Vehicles">Special Vehicles</option>
          </select>
          {errorMessages.vehicleType && <div className="text-danger">{errorMessages.vehicleType}</div>}
        </div>

        <div className="form-group">
          <label>Fuel Type</label>
          <select
            className="form-control"
            value={formData.fuelType}
            onChange={(e) => setFormData({ ...formData, fuelType: e.target.value })}
          >
            <option value="">Select Fuel Type</option>
            <option value="Petrol">Petrol</option>
            <option value="Diesel">Diesel</option>
            <option value="CNG">CNG</option>
          </select>
          {errorMessages.fuelType && <div className="text-danger">{errorMessages.fuelType}</div>}
        </div>

        <button type="submit" className="btn btn-primary btn-block mt-3">Register</button>
      </form>

      {errorMessages.general && <div className="text-danger mt-3">{errorMessages.general}</div>}
      {successMessage && <div className="text-success mt-3">{successMessage}</div>}
    </div>
  );
};

export default VehicleRegistration;
