import React, { useState } from 'react';
import { fuelstationregister } from '../api/FuelStationServiceApi.js';
import { useParams, useNavigate } from 'react-router-dom';
import '../css/Registration.css';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

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
    const customRegex = /^FSS-\d{2}-\d{7}$/; // Correct regex declaration
    const errors = {};

    if (!fuelStationData.stationName) {
        errors.stationName = 'Station name is required.';
    }

    if (!fuelStationData.registrationNumber) {
        errors.registrationNumber = 'Registration number is required.';
    } else if (!customRegex.test(fuelStationData.registrationNumber)) {
        errors.registrationNumber = 'Registration number must be in the format of "FSS-XX-XXXXXXX".';
    }

    if (!fuelStationData.location) {
        errors.location = 'Location is required.';
    }

    if (Object.keys(fuelStationData.fuelTypes).length === 0) {
        errors.fuelTypes = 'At least one fuel type must be selected.';
    }

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
      setError({}); // Clear previous errors
      const errors = validateStep1();
      console.log('Validation Errors:', errors); // Debugging
      if (Object.keys(errors).length > 0) {
        setError(errors);
        console.log(errors)
        return;
      }
      setPath(2);
    } else {
      const errors = validateStep2();
      if (Object.keys(errors).length > 0) {
        setError(errors);
        return;
      }
      setError({});
      fuelstationregister({ ...fuelStationData, ownerId: id })
        .then((response) => {
          console.log(response);
          localStorage.setItem("token", response.data.token);
          const stationId = response.data.id;       
          toast.success('Fuel station registered successfully!');
          navigate(`/station/${stationId}`);
        })
        .catch((error) => {
          const errorMessage = error.response?.data || "An unexpected error occurred.";
          toast.error(errorMessage);
        });
    }
  };
  

  return (
    <>
      <Navbar />
      <ToastContainer position="top-center" autoClose={10000} />
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
                value={fuelStationData.stationName}
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
                value={fuelStationData.registrationNumber}
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
                value={fuelStationData.location}
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
                value={fuelStationData.userName}
                placeholder="Enter Username"
                onChange={handleStationChange}
              />
              {error.userName && <div className="text-danger">{error.userName}</div>}
            </div>

            <div className="mb-3">
              <label className="form-label">Password</label>
              <input
                type="password"
                name="password"
                value={fuelStationData.password}
                className="form-control"
                placeholder="Enter Password"
                onChange={handleStationChange}
              />
              {error.password && <div className="text-danger">{error.password}</div>}
            </div>

            <div className="mb-3">
              <label className="form-label">Confirm Password</label>
              <input
                type="password"
                className="form-control"
                value={rePassword}
                placeholder="Confirm Password"
                onChange={(e) => setRePassword(e.target.value)}
              />
              {error.rePassword && <div className="text-danger">{error.rePassword}</div>}
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
