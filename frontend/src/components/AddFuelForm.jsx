import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { addFuel } from '../api/FuelStationServiceApi';

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
        console.log(response.data);
        setFuelType('');
        setQuantity('');
        navigate(`/station/${id}`);
      })
      .catch((error) => {
        console.error('Error adding fuel:', error);
        setError('Failed to add fuel. Please try again.');
      })
      .finally(() => setIsLoading(false));
  };

  return (
    <div className="container mt-4">
      <div className="card shadow-sm p-4">
        <h2 className="text-center mb-4">Add Fuel</h2>
        {error && <div className="alert alert-danger">{error}</div>}
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="fuelType" className="form-label">Fuel Type</label>
            <select
              id="fuelType"
              className="form-select"
              value={fuelType}
              onChange={(e) => setFuelType(e.target.value)}
            >
              <option value="">Select a fuel type</option>
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
