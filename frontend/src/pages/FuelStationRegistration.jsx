import React, { useState } from 'react';
import { fuelstationregister } from '../Services/FuelStationService';
import { useParams, useNavigate } from 'react-router-dom';
import '../css/Registration.css';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

const FuelStationRegistration = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [path, setPath] = useState(1);
  const [fuelStationData, setFuelStationData] = useState({
    stationName: '',
    registrationNumber: '',
    location: '',
    ownerId: '',
    fuelTypes: {}, // Object to hold fuel types as keys and balances as values
    userName: '',
    password: ''
  });

  const [rePassword, setRePassword] = useState('');
  const [error, setError] = useState({});

  const handleFuelChange = (fuelType) => {
    setFuelStationData((prevData) => {
      const updatedFuelTypes = { ...prevData.fuelTypes };
      if (fuelType in updatedFuelTypes) {
        delete updatedFuelTypes[fuelType]; // Remove fuel type if unchecked
      } else {
        updatedFuelTypes[fuelType] = ''; // Add fuel type with an empty balance
      }
      return { ...prevData, fuelTypes: updatedFuelTypes };
    });
  };

  const handleBalanceChange = (fuelType, balance) => {
    setFuelStationData((prevData) => ({
      ...prevData,
      fuelTypes: { ...prevData.fuelTypes, [fuelType]: balance },
    }));
  };

  const handleStationChange = (e) => {
    const { name, value } = e.target;
    setFuelStationData((prev) => ({ ...prev, [name]: value }));
  };

  const validateStep1 = () => {
    const errors = {};
    if (!fuelStationData.stationName) errors.stationName = 'Station name is required.';
    if (!fuelStationData.registrationNumber) errors.registrationNumber = 'Registration number is required.';
    if (!fuelStationData.location) errors.location = 'Location is required.';
    if (Object.keys(fuelStationData.fuelTypes).length === 0) errors.fuelTypes = 'At least one fuel type must be selected.';
    return errors;
  };

  const validateStep2 = () => {
    const errors = {};
    if (!fuelStationData.userName) errors.userName = 'Username is required.';
    if (!fuelStationData.password) errors.password = 'Password is required.';
    if (fuelStationData.password !== rePassword) errors.rePassword = 'Passwords do not match.';
    return errors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (path === 1) {
      const errors = validateStep1();
      if (Object.keys(errors).length > 0) {
        setError(errors);
        return;
      }

      setError({});
      setPath(2);
    } else if (path === 2) {
      const errors = validateStep2();
      if (Object.keys(errors).length > 0) {
        setError(errors);
        return;
      }

      setError({});
      fuelstationregister({ ...fuelStationData, ownerId: id })
        .then((response) => {
          const station_id = response.data;
          alert('Fuel station registered successfully!');
          navigate(`/station/${station_id}`);
        })
        .catch((error) => {
          console.error('Error registering fuel station:', error);
          alert('Registration failed. Please try again.');
        });
    }
  };

  return (
    <>
      <Navbar />
      {path === 1 && (
        <div className="register-container container mt-5">
          <form onSubmit={handleSubmit} className="p-4 border rounded bg-light">
            <h2 className="text-center mb-4">Register Fuel Station</h2>

            <div className="mb-3">
              <label htmlFor="stationName" className="form-label">Station Name:</label>
              <input
                type="text"
                id="stationName"
                name="stationName"
                placeholder="Enter Station Name"
                value={fuelStationData.stationName}
                onChange={handleStationChange}
                className={`form-control ${error.stationName ? 'is-invalid' : ''}`}
              />
              {error.stationName && <div className="invalid-feedback">{error.stationName}</div>}
            </div>

            <div className="mb-3">
              <label htmlFor="registrationNumber" className="form-label">Registration Number:</label>
              <input
                type="text"
                id="registrationNumber"
                name="registrationNumber"
                placeholder="Enter Registration Number"
                value={fuelStationData.registrationNumber}
                onChange={handleStationChange}
                className={`form-control ${error.registrationNumber ? 'is-invalid' : ''}`}
              />
              {error.registrationNumber && <div className="invalid-feedback">{error.registrationNumber}</div>}
            </div>

            <div className="mb-3">
              <label htmlFor="location" className="form-label">Location:</label>
              <input
                type="text"
                id="location"
                name="location"
                placeholder="Enter Location"
                value={fuelStationData.location}
                onChange={handleStationChange}
                className={`form-control ${error.location ? 'is-invalid' : ''}`}
              />
              {error.location && <div className="invalid-feedback">{error.location}</div>}
            </div>

            <div className="mb-3">
              <label className="form-label">Fuel Inventory:</label>
              {['92-Octane', '95-Octane', 'Auto Diesel', 'Super Diesel'].map((fuelType) => (
                <div key={fuelType} className="mb-2">
                  <div className="form-check">
                    <input
                      type="checkbox"
                      value={fuelType}
                      onChange={() => handleFuelChange(fuelType)}
                      className="form-check-input"
                      checked={fuelType in fuelStationData.fuelTypes}
                    />
                    <label className="form-check-label">{fuelType}</label>
                  </div>
                  {fuelType in fuelStationData.fuelTypes && (
                    <input
                      type="number"
                      placeholder="Balance"
                      value={fuelStationData.fuelTypes[fuelType]}
                      onChange={(e) => handleBalanceChange(fuelType, e.target.value)}
                      className="form-control mt-2"
                      style={{ width: '100px' }}
                    />
                  )}
                </div>
              ))}
              {error.fuelTypes && <div className="text-danger">{error.fuelTypes}</div>}
            </div>

            <button type="submit" className="btn btn-primary w-100">Next</button>
          </form>
        </div>
      )}

      {path === 2 && (
        <div className="register-container container mt-5">
          <form onSubmit={handleSubmit} className="p-4 border rounded bg-light">
            <a
              href="#"
              onClick={(e) => {
                e.preventDefault();
                setPath(1);
              }}
              className="text-decoration-none"
            >
              Back
            </a>
            <h2 className="text-center mb-4">Register Fuel Station</h2>

            <div className="mb-3">
              <label htmlFor="userName" className="form-label">Station Username:</label>
              <input
                type="text"
                id="userName"
                name="userName"
                placeholder="Enter Username"
                value={fuelStationData.userName}
                onChange={handleStationChange}
                className={`form-control ${error.userName ? 'is-invalid' : ''}`}
              />
              {error.userName && <div className="invalid-feedback">{error.userName}</div>}
            </div>

            <div className="mb-3">
              <label htmlFor="password" className="form-label">Password:</label>
              <input
                type="password"
                id="password"
                name="password"
                placeholder="Enter Password"
                value={fuelStationData.password}
                onChange={handleStationChange}
                className={`form-control ${error.password ? 'is-invalid' : ''}`}
              />
              {error.password && <div className="invalid-feedback">{error.password}</div>}
            </div>

            <div className="mb-3">
              <label htmlFor="rePassword" className="form-label">Confirm Password:</label>
              <input
                type="password"
                id="rePassword"
                name="rePassword"
                placeholder="Confirm Password"
                value={rePassword}
                onChange={(e) => setRePassword(e.target.value)}
                className={`form-control ${error.rePassword ? 'is-invalid' : ''}`}
              />
              {error.rePassword && <div className="invalid-feedback">{error.rePassword}</div>}
            </div>

            <button type="submit" className="btn btn-primary w-100">Submit</button>
          </form>
        </div>
      )}

      <Footer />
    </>
  );
};

export default FuelStationRegistration;
