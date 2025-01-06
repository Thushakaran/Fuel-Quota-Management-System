import React, { useState } from "react";
import axios from "../api/axiosInstance";

const EditVehicleModal = ({ vehicle, onClose, onUpdate }) => {
  const [updatedVehicle, setUpdatedVehicle] = useState({
    vehicleNumber: vehicle.vehicleNumber || "",
    ownerName: vehicle.ownerName || "",
    vehicleType: vehicle.vehicleType || "",
    fuelType: vehicle.fuelType || "",
    fuelQuota: vehicle.fuelQuota || "",
    fuelQuota: vehicle.chassisNumber || "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUpdatedVehicle({ ...updatedVehicle, [name]: value });
  };

  // const handleSubmit = () => {
  //     // Validate required fields
  //     if (!updatedVehicle.vehicleNumber || !updatedVehicle.ownerName) {
  //         alert("Vehicle number and owner name are required.");
  //         return;
  //     }

  //     axios
  //         .put(/admin/vehicles/${vehicle.id}, updatedVehicle)
  //         .then(() => {
  //             alert("Vehicle updated successfully!");
  //             onUpdate(); // Refresh the vehicle list
  //             onClose(); // Close the modal
  //         })
  //         .catch((error) => {
  //             console.error("Error updating vehicle:", error);
  //             alert("Failed to update vehicle.");
  //         });
  // };

  const handleSubmit = () => {
    axios
      .put(`/admin/vehicles/${vehicle.id}`, updatedVehicle)
      .then((response) => {
        alert("Vehicle details updated successfully!");
        onUpdate();
        onClose();
      })
      .catch((error) => {
        console.error("Error updating vehicle:", error);
        if (error.response) {
          alert(`Failed to update vehicle: ${error.response.data.message}`);
        } else {
          alert("Failed to connect to the server.");
        }
      });
  };

  return (
    <div className="modal show d-block" tabIndex="-1">
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">Edit Vehicle</h5>
            <button
              type="button"
              className="btn-close"
              onClick={onClose}
            ></button>
          </div>
          <div className="modal-body">
            <div className="mb-3">
              <label>Vehicle Number</label>
              <input
                type="text"
                name="vehicleNumber"
                className="form-control"
                value={updatedVehicle.vehicleNumber}
                onChange={handleChange}
              />
            </div>
            <div className="mb-3">
              <label>Chassis Number</label>
              <input
                type="text"
                name="chassisNumber"
                className="form-control"
                value={updatedVehicle.chassisNumber}
                onChange={handleChange}
              />
            </div>

            <div className="mb-3">
              <label>Owner Name</label>
              <input
                type="text"
                name="ownerName"
                className="form-control"
                value={updatedVehicle.ownerName}
                onChange={handleChange}
              />
            </div>
            <div className="mb-3">
              <label>Vehicle Type</label>
              <input
                type="text"
                name="vehicleType"
                className="form-control"
                value={updatedVehicle.vehicleType}
                onChange={handleChange}
              />
            </div>
            <div className="mb-3">
              <label>Fuel Type</label>
              <input
                type="text"
                name="fuelType"
                className="form-control"
                value={updatedVehicle.fuelType}
                onChange={handleChange}
              />
            </div>
            <div className="mb-3">
              <label>Fuel Quota</label>
              <input
                type="number"
                name="fuelQuota"
                className="form-control"
                value={updatedVehicle.fuelQuota}
                onChange={handleChange}
              />
            </div>
          </div>
          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              onClick={onClose}
            >
              Close
            </button>
            <button
              type="button"
              className="btn btn-primary"
              onClick={handleSubmit}
            >
              Save Changes
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EditVehicleModal;