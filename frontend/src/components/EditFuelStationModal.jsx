import React, { useState } from "react";

const EditFuelStationModal = ({ station, onSave, onCancel }) => {
    const [formData, setFormData] = useState({ ...station });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleFuelInventoryChange = (fuelType, value) => {
        setFormData((prev) => ({
            ...prev,
            fuelInventory: {
                ...prev.fuelInventory,
                [fuelType]: value,
            },
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSave(formData); // Pass the updated data back to the parent
    };

    return (
        <div className="modal show d-block" style={{ backgroundColor: "rgba(0, 0, 0, 0.5)" }}>
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Edit Fuel Station</h5>
                        <button
                            type="button"
                            className="btn-close"
                            onClick={onCancel}
                        ></button>
                    </div>
                    <form onSubmit={handleSubmit}>
                        <div className="modal-body">
                            {/* Location */}
                            <div className="mb-3">
                                <label className="form-label">Location</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="location"
                                    value={formData.location}
                                    onChange={handleChange}
                                />
                            </div>

                            {/* Station Name */}
                            <div className="mb-3">
                                <label className="form-label">Station Name</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="stationName"
                                    value={formData.stationName}
                                    onChange={handleChange}
                                />
                            </div>

                            {/* Registration Number */}
                            <div className="mb-3">
                                <label className="form-label">Registration Number</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="registrationNumber"
                                    value={formData.registrationNumber}
                                    onChange={handleChange}
                                />
                            </div>

                            {/* Fuel Inventory */}
                            <div className="mb-3">
                                <label className="form-label">Fuel Inventory</label>
                                {formData.fuelInventory &&
                                    Object.entries(formData.fuelInventory).map(([fuelType, qty]) => (
                                        <div key={fuelType} className="input-group mb-2">
                                            <span className="input-group-text">{fuelType}</span>
                                            <input
                                                type="number"
                                                className="form-control"
                                                min="0"
                                                value={qty}
                                                onChange={(e) =>
                                                    handleFuelInventoryChange(
                                                        fuelType,
                                                        e.target.value
                                                    )
                                                }
                                            />
                                        </div>
                                    ))}
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

export default EditFuelStationModal;
