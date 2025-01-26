import React, { useState } from "react";

const FuelTransactionEditForm = ({ transaction, onSave, onClose }) => {
    const [formData, setFormData] = useState({
        vehicleNumber: transaction.vehicle?.vehicleNumber || "",
        amount: transaction.amount || "",
        transactionDate: new Date(transaction.transactionDate)
            .toISOString()
            .slice(0, 16),
        stationId: transaction.station?.id || "", // Ensure stationId is passed correctly
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSave({
            id: transaction.id,
            amount: parseFloat(formData.amount),
            transactionDate: new Date(formData.transactionDate).toISOString(),
            station: { id: parseInt(formData.stationId, 10) }, // Ensure station is structured as expected
            vehicle: { vehicleNumber: formData.vehicleNumber },
        });
    };

    return (
        <div className="modal d-block" style={{ background: "rgba(0,0,0,0.5)" }}>
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Edit Transaction</h5>
                        <button type="button" className="btn-close" onClick={onClose}></button>
                    </div>
                    <div className="modal-body">
                        <form onSubmit={handleSubmit}>
                            <div className="mb-3">
                                <label className="form-label">Vehicle Number</label>
                                <input
                                    type="text"
                                    name="vehicleNumber"
                                    className="form-control"
                                    value={formData.vehicleNumber}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <label className="form-label">Amount (liters)</label>
                                <input
                                    type="number"
                                    name="amount"
                                    className="form-control"
                                    value={formData.amount}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <label className="form-label">Transaction Date</label>
                                <input
                                    type="datetime-local"
                                    name="transactionDate"
                                    className="form-control"
                                    value={formData.transactionDate}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <label className="form-label">Station ID</label>
                                <input
                                    type="number"
                                    name="stationId"
                                    className="form-control"
                                    value={formData.stationId}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                            <button type="submit" className="btn btn-success me-2">
                                Save
                            </button>
                            <button type="button" className="btn btn-secondary" onClick={onClose}>
                                Cancel
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default FuelTransactionEditForm;
