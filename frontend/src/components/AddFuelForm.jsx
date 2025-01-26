import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { addFuel } from '../api/FuelStationServiceApi';
import {ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const FUEL_TYPES = ["92-Octane", "95-Octane", "Auto Diesel", "Super Diesel"];

const AddFuelForm = ({ setFuels }) => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [fuelType, setFuelType] = useState('');
  const [quantity, setQuantity] = useState('');
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    setError(null);

    if (!fuelType || parseFloat(quantity) <= 0) {
      setError('Please fill in both fields with valid data.');
      return;
    }

    const fuelDetails = {
      [fuelType]: parseFloat(quantity),
    };

    setIsLoading(true);

    addFuel(id, fuelDetails)
      .then((response) => {
        toast.success(response.data.message);
        setFuelType('');
        setQuantity('');
        setError(null);
        setTimeout(() => {
          navigate(`/station/${id}`);
        }, 2000);
      })
      .catch((error) => {
        const errorMessage = error.response?.data?.message || "Failed to add fuel.";
        toast.error(errorMessage);
        setError(errorMessage);
      })
      .finally(() => setIsLoading(false));
  };

  return (
    <div className="container mt-4">
      <ToastContainer position="top-center" autoClose={10000} />
      <div className="card shadow-sm p-4">
        <h2 className="text-center mb-4">Add Fuel</h2>
        {error && (
          <div className="alert alert-danger" role="alert" aria-live="polite">
            {error}
          </div>
        )}
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="fuelType" className="form-label">Fuel Type</label>
            <select
              id="fuelType"
              className="form-select"
              value={fuelType}
              onChange={(e) => setFuelType(e.target.value)}
            >
              <option value="" disabled>Select a fuel type</option>
              {FUEL_TYPES.map((type) => (
                <option key={type} value={type}>{type}</option>
              ))}
            </select>
          </div>

          <div className="mb-3">
            <label htmlFor="quantity" className="form-label">Quantity (Liters)</label>
            <input
              type="number"
              id="quantity"
              className="form-control"
              value={quantity}
              onChange={(e) => setQuantity(e.target.value)}
              placeholder="Enter quantity"
              min="1"
            />
          </div>

          <button
            type="submit"
            className="btn btn-primary w-100"
            disabled={isLoading}
          >
            {isLoading ? 'Adding...' : 'Add Fuel'}
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddFuelForm;
