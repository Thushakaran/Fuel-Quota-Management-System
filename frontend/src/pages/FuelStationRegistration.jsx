import React, { useState } from 'react';
import { fuelstationregister } from '../api/FuelStationServiceApi.js';
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
    fuelTypes: {},
    userName: '',
    password: '',
  });

  const [rePassword, setRePassword] = useState('');
  const [error, setError] = useState({});

  const handleFuelChange = (fuelType) => {
    setFuelStationData((prevData) => {
      const updatedFuelTypes = { ...prevData.fuelTypes };
      if (fuelType in updatedFuelTypes) {
        delete updatedFuelTypes[fuelType];
      } else {
        updatedFuelTypes[fuelType] = '';
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
    } else {
      const errors = validateStep2();
      if (Object.keys(errors).length > 0) {
        setError(errors);
        return;
      }
      setError({});
      fuelstationregister({ ...fuelStationData, ownerId: id })
        .then(() => {
          alert('Fuel station registered successfully!');
          navigate(`/station/${id}`);
        })
        .catch(() => alert('Registration failed. Try again.'));
    }
  };

  return (
    <>
      <Navbar />
      <div className="container mt-5">
        <div className="progress mb-4">
          <div
            className={`progress-bar ${path === 1 ? 'bg-primary' : 'bg-success'}`}
            role="progressbar"
            style={{ width: path === 1 ? '50%' : '100%' }}
            aria-valuenow={path === 1 ? '50' : '100'}
            aria-valuemin="0"
            aria-valuemax="100"
          >
            {path === 1 ? 'Step 1 of 2' : 'Step 2 of 2'}
          </div>
        </div>

        {path === 1 && (
          <form onSubmit={handleSubmit} className="p-4 border rounded bg-light shadow">
            <h3 className="text-center mb-4">Register Fuel Station</h3>

            <div className="mb-3">
              <label className="form-label">Station Name</label>
              <input
                type="text"
                name="stationName"
                className={`form-control ${error.stationName ? 'is-invalid' : ''}`}
                placeholder="Enter Station Name"
                onChange={handleStationChange}
              />
              {error.stationName && <div className="invalid-feedback">{error.stationName}</div>}
            </div>

            <div className="mb-3">
              <label className="form-label">Registration Number</label>
              <input
                type="text"
                name="registrationNumber"
                className={`form-control ${error.registrationNumber ? 'is-invalid' : ''}`}
                placeholder="Enter Registration Number"
                onChange={handleStationChange}
              />
              {error.registrationNumber && <div className="invalid-feedback">{error.registrationNumber}</div>}
            </div>

            <div className="mb-3">
              <label className="form-label">Location</label>
              <input
                type="text"
                name="location"
                className={`form-control ${error.location ? 'is-invalid' : ''}`}
                placeholder="Enter Location"
                onChange={handleStationChange}
              />
              {error.location && <div className="invalid-feedback">{error.location}</div>}
            </div>

            <div className="accordion mb-4" id="fuelAccordion">
              <div className="accordion-item">
                <h2 className="accordion-header" id="fuelHeading">
                  <button
                    className="accordion-button"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#fuelCollapse"
                    aria-expanded="true"
                    aria-controls="fuelCollapse"
                  >
                    Select Fuel Types
                  </button>
                </h2>
                <div
                  id="fuelCollapse"
                  className="accordion-collapse collapse show"
                  aria-labelledby="fuelHeading"
                  data-bs-parent="#fuelAccordion"
                >
                  <div className="accordion-body">
                    {['92-Octane', '95-Octane', 'Auto Diesel', 'Super Diesel'].map((fuelType) => (
                      <div key={fuelType} className="form-check mb-2">
                        <input
                          className="form-check-input"
                          type="checkbox"
                          onChange={() => handleFuelChange(fuelType)}
                        />
                        <label className="form-check-label">{fuelType}</label>
                        {fuelType in fuelStationData.fuelTypes && (
                          <input
                            type="number"
                            className="form-control mt-2"
                            placeholder="Enter Balance"
                            onChange={(e) => handleBalanceChange(fuelType, e.target.value)}
                          />
                        )}
                      </div>
                    ))}
                    {error.fuelTypes && <div className="text-danger">{error.fuelTypes}</div>}
                  </div>
                </div>
              </div>
            </div>

            <button type="submit" className="btn btn-primary w-100">
              Next
            </button>
          </form>
        )}

        {path === 2 && (
          <form onSubmit={handleSubmit} className="p-4 border rounded bg-light shadow">
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
            <h3 className="text-center mb-4">Station Login Details</h3>

            <div className="mb-3">
              <label className="form-label">Username</label>
              <input
                type="text"
                name="userName"
                className={`form-control ${error.userName ? 'is-invalid' : ''}`}
                placeholder="Enter Username"
                onChange={handleStationChange}
              />
              </div>

            <div className="mb-3">
              <label className="form-label">Password</label>
              <input
                type="password"
                name="password"
                className="form-control"
                placeholder="Enter Password"
                onChange={handleStationChange}
              />
            </div>

            <div className="mb-3">
              <label className="form-label">Confirm Password</label>
              <input
                type="password"
                className="form-control"
                placeholder="Confirm Password"
                onChange={(e) => setRePassword(e.target.value)}
              />
            </div>

            <button type="submit" className="btn btn-success w-100">
              Register
            </button>
          </form>
        )}
      </div>

      <Footer />
    </>
  );
};

export default FuelStationRegistration;
