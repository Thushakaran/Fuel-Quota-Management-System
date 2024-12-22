import React, { useState } from "react";
import { registerVehicle } from "../api/vehicleApi";

const VehicleRegistration = () => {
  const [formData, setFormData] = useState({
    vehicleNumber: "",
    ownerName: "",
    vehicleType: "",
    chassisNumber: "",
    fuelType: "",
  });

  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();

    // Basic client-side validation
    const { vehicleNumber, ownerName, vehicleType, chassisNumber, fuelType } =
      formData;
    if (
      !vehicleNumber ||
      !ownerName ||
      !vehicleType ||
      !chassisNumber ||
      !fuelType
    ) {
      setErrorMessage("All fields are required!");
      return;
    }

    setErrorMessage("");
    setSuccessMessage("");

    try {
      const result = await registerVehicle(formData);
      alert("Vehicle registered successfully!");
      console.log(result);
      alert(`QR Code: ${result.qrCode}, Fuel Quota: ${result.fuelQuota}`);
    } catch (error) {
      console.error("Error response:", error.response); // Log detailed error response
      const errorMessage =
        error.response?.data?.message ||
        "Failed to register vehicle. Please try again.";
      setErrorMessage(errorMessage); // Set error message in state
    }
  };

  return (
    <div className="vehicle-registration">
      <h2>Vehicle Registration</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Vehicle Number"
          value={formData.vehicleNumber}
          onChange={(e) =>
            setFormData({ ...formData, vehicleNumber: e.target.value })
          }
        />
        <input
          type="text"
          placeholder="Owner Name"
          value={formData.ownerName}
          onChange={(e) =>
            setFormData({ ...formData, ownerName: e.target.value })
          }
        />
        <input
          type="text"
          placeholder="Chassis Number"
          value={formData.chassisNumber}
          onChange={(e) =>
            setFormData({ ...formData, chassisNumber: e.target.value })
          }
        />
        <select
          value={formData.vehicleType}
          onChange={(e) =>
            setFormData({ ...formData, vehicleType: e.target.value })
          }
        >
          <option value="">Select Vehicle Type</option>
          <option value="Car">Car</option>
          <option value="Bike">Bike</option>
          <option value="Truck">Truck</option>
          <option value="Van">Van</option>
          <option value="Motorcycle">Motorcycle</option>
        </select>
        <select
          value={formData.fuelType}
          onChange={(e) =>
            setFormData({ ...formData, fuelType: e.target.value })
          }
        >
          <option value="">Select Fuel Type</option>
          <option value="Petrol">Petrol</option>
          <option value="Diesel">Diesel</option>
          <option value="CNG">CNG</option>
        </select>
        <button type="submit">Register</button>
      </form>

      {errorMessage && <div className="error-message">{errorMessage}</div>}
      {successMessage && (
        <div className="success-message">{successMessage}</div>
      )}
    </div>
  );
};

export default VehicleRegistration;
