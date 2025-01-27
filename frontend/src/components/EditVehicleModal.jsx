import React, { useState, useEffect } from "react";

const EditVehicleModal = ({ vehicle, onSave, onCancel }) => {
    const [formData, setFormData] = useState({ ...vehicle });

    useEffect(() => {
        setFormData({ ...vehicle }); // Re-populate form data when vehicle changes
    }, [vehicle]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSave(formData); // Pass the updated form data to the parent
    };

    return (
        <div className="modal show d-block" style={{ backgroundColor: "rgba(0, 0, 0, 0.5)" }}>
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Edit Vehicle</h5>
                        <button
                            type="button"
                            className="btn-close"
                            onClick={onCancel}
                        ></button>
                    </div>
                    <form onSubmit={handleSubmit}>
                        <div className="modal-body">
                            {/* Vehicle Number */}
                            <div className="mb-3">
                                <label className="form-label">Vehicle Number</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="vehicleNumber"
                                    value={formData.vehicleNumber}
                                    onChange={handleChange}
                                />
                            </div>

                            {/* Owner Name */}
                            <div className="mb-3">
                                <label className="form-label">Owner</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="ownerName"
                                    value={formData.ownerName}
                                    onChange={handleChange}
                                />
                            </div>

                            {/* Vehicle Type */}
                            <div className="mb-3">
                                <label className="form-label">Vehicle Type</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="vehicleType"
                                    value={formData.vehicleType}
                                    onChange={handleChange}
                                />
                            </div>

                            {/* Fuel Type */}
                            <div className="mb-3">
                                <label className="form-label">Fuel Type</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="fuelType"
                                    value={formData.fuelType}
                                    onChange={handleChange}
                                />
                            </div>

                            {/* Fuel Quota */}
                            <div className="mb-3">
                                <label className="form-label">Fuel Quota</label>
                                <input
                                    type="number"
                                    className="form-control"
                                    name="fuelQuota"
                                    value={formData.fuelQuota}
                                    onChange={handleChange}
                                />
                            </div>

                            {/* Chassis Number */}
                            <div className="mb-3">
                                <label className="form-label">Chassis Number</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="chassisNumber"
                                    value={formData.chassisNumber}
                                    onChange={handleChange}
                                />
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button
                                type="button"
                                className="btn btn-secondary"
                                onClick={onCancel}
                            >
                                Cancel
                            </button>
                            <button type="submit" className="btn btn-primary">
                                Save Changes
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default EditVehicleModal;
