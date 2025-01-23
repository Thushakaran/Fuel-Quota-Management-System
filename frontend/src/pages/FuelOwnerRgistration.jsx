import React, { useState } from 'react';
import { ownerregister } from '../api/FuelStationOwnerServiceApi.js';
import { useNavigate } from 'react-router-dom';
import '../css/Registration.css';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import { FaUser, FaPhoneAlt, FaEnvelope, FaKey } from 'react-icons/fa';

const FuelOwnerRegistration = () => {
  const navigate = useNavigate();
  const [path, setPath] = useState(1); // Tracks the form step

  const [ownerData, setOwnerData] = useState({
    ownerName: '',
    nicNo: '',
    phoneNumber: '',
    email: '',
    userName: '',
    password: '',
  });

  const [rePassword, setRePassword] = useState('');
  const [error, setError] = useState({});

  const handleOwnerChange = (e) => {
    const { name, value } = e.target;
    setOwnerData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const validateStep1 = () => {
    const errors = {};
    if (!ownerData.ownerName) errors.ownerName = 'Owner name is required.';
    if (!ownerData.nicNo) errors.nicNo = 'NIC number is required.';
    if (!ownerData.phoneNumber) errors.phoneNumber = 'Phone number is required.';
    if (!ownerData.email) errors.email = 'Email is required.';
    return errors;
  };

  const validateStep2 = () => {
    const errors = {};
    if (!ownerData.userName) errors.userName = 'Username is required.';
    if (!ownerData.password) errors.password = 'Password is required.';
    if (ownerData.password !== rePassword) errors.rePassword = 'Passwords do not match.';
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
      ownerregister(ownerData)
        .then((response) => {
          const id = response.data.owner.id;
          alert('Fuel owner registered successfully!');
          navigate(`/owner/${id}`);
        })
        .catch((error) => {
          console.error('Error registering owner:', error);
          alert('Registration failed. Please try again.');
        });
    }
  };

  return (
    <>
      <Navbar />

      {path === 1 && (
        <div className="register-container container mt-5">
          <form onSubmit={handleSubmit} className="p-4 border rounded bg-light shadow-sm">
            <h2 className="text-center mb-4">Register Fuel Station Owner</h2>

            <div className="mb-3">
              <label htmlFor="ownerName" className="form-label">Owner Name:</label>
              <div className="input-group">
                <span className="input-group-text"><FaUser /></span>
                <input
                  type="text"
                  id="ownerName"
                  name="ownerName"
                  placeholder="Enter Owner Name"
                  value={ownerData.ownerName}
                  onChange={handleOwnerChange}
                  className={`form-control ${error.ownerName ? 'is-invalid' : ''}`}
                />
              </div>
              {error.ownerName && <div className="invalid-feedback">{error.ownerName}</div>}
            </div>

            <div className="mb-3">
              <label htmlFor="nicNo" className="form-label">NIC Number:</label>
              <div className="input-group">
                <span className="input-group-text"><FaKey /></span>
                <input
                  type="text"
                  id="nicNo"
                  name="nicNo"
                  placeholder="Enter NIC Number"
                  value={ownerData.nicNo}
                  onChange={handleOwnerChange}
                  className={`form-control ${error.nicNo ? 'is-invalid' : ''}`}
                />
              </div>
              {error.nicNo && <div className="invalid-feedback">{error.nicNo}</div>}
            </div>

            <div className="mb-3">
              <label htmlFor="phoneNumber" className="form-label">Phone Number:</label>
              <div className="input-group">
                <span className="input-group-text"><FaPhoneAlt /></span>
                <input
                  type="text"
                  id="phoneNumber"
                  name="phoneNumber"
                  placeholder="Enter Phone Number"
                  value={ownerData.phoneNumber}
                  onChange={handleOwnerChange}
                  className={`form-control ${error.phoneNumber ? 'is-invalid' : ''}`}
                />
              </div>
              {error.phoneNumber && <div className="invalid-feedback">{error.phoneNumber}</div>}
            </div>

            <div className="mb-3">
              <label htmlFor="email" className="form-label">Email:</label>
              <div className="input-group">
                <span className="input-group-text"><FaEnvelope /></span>
                <input
                  type="email"
                  id="email"
                  name="email"
                  placeholder="Enter Email"
                  value={ownerData.email}
                  onChange={handleOwnerChange}
                  className={`form-control ${error.email ? 'is-invalid' : ''}`}
                />
              </div>
              {error.email && <div className="invalid-feedback">{error.email}</div>}
            </div>

            <button type="submit" className="btn btn-primary w-100">Next</button>
          </form>
        </div>
      )}

      {path === 2 && (
        <div className="register-container container mt-5">
          <form onSubmit={handleSubmit} className="p-4 border rounded bg-light shadow-sm">
            <a
              href="#"
              onClick={(e) => {
                e.preventDefault();
                setPath(1);
              }}
              className="text-decoration-none mb-3 d-block"
            >
              <strong>Back</strong>
            </a>

            <h2 className="text-center mb-4">Register Fuel Station Owner</h2>

            <div className="mb-3">
              <label htmlFor="userName" className="form-label">Username:</label>
              <div className="input-group">
                <span className="input-group-text"><FaUser /></span>
                <input
                  type="text"
                  id="userName"
                  name="userName"
                  placeholder="Enter Username"
                  value={ownerData.userName}
                  onChange={handleOwnerChange}
                  className={`form-control ${error.userName ? 'is-invalid' : ''}`}
                />
              </div>
              {error.userName && <div className="invalid-feedback">{error.userName}</div>}
            </div>

            <div className="mb-3">
              <label htmlFor="password" className="form-label">Password:</label>
              <div className="input-group">
                <span className="input-group-text"><FaKey /></span>
                <input
                  type="password"
                  id="password"
                  name="password"
                  placeholder="Enter Password"
                  value={ownerData.password}
                  onChange={handleOwnerChange}
                  className={`form-control ${error.password ? 'is-invalid' : ''}`}
                />
              </div>
              {error.password && <div className="invalid-feedback">{error.password}</div>}
            </div>

            <div className="mb-3">
              <label htmlFor="rePassword" className="form-label">Confirm Password:</label>
              <input
                type="password"
                id="rePassword"
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

      <br />
      <br />
      <div style={{ position: 'relative', bottom: '0', display: 'block', width: '100%' }}>
        <Footer />
      </div>
    </>
  );
};

export default FuelOwnerRegistration;
