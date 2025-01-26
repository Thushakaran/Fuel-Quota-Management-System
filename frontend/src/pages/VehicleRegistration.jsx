import React, { useState } from "react";
import { registerVehicle } from "../api/vehicleApi";
import "bootstrap/dist/css/bootstrap.min.css"; // Import Bootstrap CSS
import "../css/VehicleRegistration.css"; // Import custom CSS
import Navbar from "../components/Navbar";

// Reusable form input component
const FormInput = ({ label, type, name, value, onChange, error }) => (
  <div className="form-group">
    <label>{label}</label>
    <input
      type={type}
      className="form-control"
      name={name}
      value={value}
      onChange={onChange}
    />
    {error && <div className="text-danger">{error}</div>}
  </div>
);

const VehicleRegistration = () => {
  const [formData, setFormData] = useState({
    vehicleNumber: "",
    ownerName: "",
    ownerIcNumber: "",
    phoneNumber: "",
    email: "",
    vehicleType: "",
    chassisNumber: "",
    fuelType: "",
    userName: "",
    password: "",
  });
  const [path, setPath] = useState("1");

  const [rePassword, setRePassword] = useState("");
  const [errorMessages, setErrorMessages] = useState({});
  const [successMessage, setSuccessMessage] = useState("");
  const [qrCode, setQrCode] = useState("");
  const [loading, setLoading] = useState(false); // Loading state

  // Helper function to validate form
  const validateForm = (formData) => {
    const errors = {};
    Object.keys(formData).forEach((key) => {
      if (!formData[key] && key !== "rePassword") {
        errors[key] = `${key} is required!`;
      }
    });

    if (formData.password !== rePassword) {
      errors.rePassword = "Passwords do not match!";
    }

    return errors;
  };

  // Reset form data
  const resetForm = () => {
    setFormData({
      vehicleNumber: "",
      ownerName: "",
      ownerIcNumber: "",
      phoneNumber: "",
    email: "",
      vehicleType: "",
      chassisNumber: "",
      fuelType: "",
      userName: "",
      password: "",
    });
    setRePassword("");
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true); // Set loading to true while submitting

    const errors = validateForm(formData);
    if (Object.keys(errors).length > 0) {
      setErrorMessages(errors);
      setLoading(false); // Set loading to false if validation fails
      return;
    }

    setErrorMessages({});
    setSuccessMessage("");

    try {
      const result = await registerVehicle(formData);
      setSuccessMessage("Vehicle registered successfully!");
      if (result.qrCode) setQrCode(result.qrCode);
      localStorage.setItem("token", response.data.token);
      resetForm();
    } catch (error) {
      const errorMessage =
        error.response?.data?.message ||
        "Failed to register vehicle. Please try again.";
      setErrorMessages({ general: errorMessage });
    } finally {
      setLoading(false); // Reset loading state after request
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
    const updatedErrors = validateForm({ ...formData, [name]: value });
    setErrorMessages(updatedErrors);
  };

  return (
    <div>
      <Navbar />
      <div className="container mt-5 bg-light p-4 rounded">
        <div className="header text-center mb-4 text-primary">
          <h2>Vehicle Registration</h2>
        </div>

        {errorMessages.general && (
          <div className="text-danger mt-3" style={{ fontSize: "1.2rem" }}>
            {errorMessages.general}
          </div>
        )}
        {successMessage && (
          <div className="text-success mt-3" style={{ fontSize: "1.2rem" }}>
            {successMessage}
          </div>
        )}

        {qrCode && (
          <div className="mt-3 text-center">
            <h5>Your QR Code:</h5>
            <img
              src={`data:image/png;base64,${qrCode}`}
              alt="Vehicle QR Code"
              onError={() => setQrCode("")} // Handle image loading error
            />
            <a
              href={`data:image/png;base64,${qrCode}`}
              download="vehicle_qr.png"
              className="btn btn-success mt-2"
            >
              Download QR Code
            </a>
          </div>
        )}

        <form onSubmit={handleSubmit} className="form-container">
          <FormInput
            label="Vehicle Number"
            type="text"
            name="vehicleNumber"
            value={formData.vehicleNumber}
            onChange={handleChange}
            error={errorMessages.vehicleNumber}
          />
          <FormInput
            label="Owner Name"
            type="text"
            name="ownerName"
            value={formData.ownerName}
            onChange={handleChange}
            error={errorMessages.ownerName}
          />
          <FormInput
            label="Owner IC Number"
            type="text"
            name="ownerIcNumber"
            value={formData.ownerIcNumber}
            onChange={handleChange}
            error={errorMessages.ownerIcNumber}
          />
          <FormInput
            label="Phone Number"
            type="text"
            name="phoneNumber"
            value={formData.phoneNumber}
            onChange={handleChange}
            error={errorMessages.phoneNumber}
          />
          <FormInput
            label="Email"
            type="text"
            name="email"
            value={formData.email}
            onChange={handleChange}
            error={errorMessages.email}
          />
          <FormInput
            label="Chassis Number"
            type="text"
            name="chassisNumber"
            value={formData.chassisNumber}
            onChange={handleChange}
            error={errorMessages.chassisNumber}
          />

          <div className="form-group">
            <label>Vehicle Type</label>
            <select
              className="form-control"
              name="vehicleType"
              value={formData.vehicleType}
              onChange={handleChange}
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
            {errorMessages.vehicleType && (
              <div className="text-danger">{errorMessages.vehicleType}</div>
            )}
          </div>

          <div className="form-group">
            <label>Fuel Type</label>
            <select
              className="form-control"
              name="fuelType"
              value={formData.fuelType}
              onChange={handleChange}
            >
              <option value="">Select Fuel Type</option>
              <option value="92-Octane">92-Octane</option>
              <option value="95-Octane">95-Octane</option>
              <option value="Auto Diesel">Auto Diesel</option>
              <option value="Super Diesel">Super Diesel</option>
            </select>
            {errorMessages.fuelType && (
              <div className="text-danger">{errorMessages.fuelType}</div>
            )}
          </div>

          <FormInput
            label="Username"
            type="text"
            name="userName"
            value={formData.userName}
            onChange={handleChange}
            error={errorMessages.userName}
          />
          <FormInput
            label="Password"
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            error={errorMessages.password}
          />
          <FormInput
            label="Confirm Password"
            type="password"
            name="rePassword"
            value={rePassword}
            onChange={(e) => setRePassword(e.target.value)}
            error={errorMessages.rePassword}
          />

          <button
            type="submit"
            className="btn btn-primary btn-block w-100 mt-3"
            disabled={loading}
          >
            {loading ? "Registering..." : "Register"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default VehicleRegistration;

