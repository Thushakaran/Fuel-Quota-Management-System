import React, { useState } from 'react';
import { ownerregister } from '../Services/FuelStationService';
import '../css/Registration.css';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

const FuelOwnerRegistration = () => {
  const [path, setPath] = useState(1); // Tracks the current form step
  const navigate = useNavigate();

  // State for owner details
  const [ownerData, setOwnerData] = useState({
    ownerName: '',
    nicNo: '',
    phoneNumber: '',
    email: '',
    userName: '',
    password: '',
  });

  const [rePassword, setRePassword] = useState(''); // State for re-entered password
  const [error, setError] = useState(''); // State for error messages

  // Handles input changes for owner details
  const handleOwnerChange = (e) => {
    const { name, value } = e.target;
    setOwnerData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  // Handles form submission
  const handleSubmit = (e) => {
    e.preventDefault();

    if (path === 1) {
      // Validation for the first step
      if (!ownerData.ownerName || !ownerData.nicNo || !ownerData.phoneNumber || !ownerData.email) {
        setError('Please fill out all required fields.');
        return;
      }
      setPath(2); // Move to the second form
    } else if (path === 2) {
      // Validation for the second step
      if (!ownerData.userName || !ownerData.password || ownerData.password !== rePassword) {
        setError('Passwords must match.');
        return;
      }

      // Final submission
      setError('');
      ownerregister(ownerData)
        .then((response) => {
          console.log(response);
          const id = response.data.id;

          alert('Fuel station registered successfully!');
          navigate(`/owner/${id}`);
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
            <h2 className="text-center mb-4">Register Fuel Station Owner</h2>
            {error && <div className="alert alert-danger">{error}</div>}

            <div className="mb-3">
              <label className="form-label">Name:</label>
              <input
                type="text"
                name="ownerName"
                value={ownerData.ownerName}
                onChange={handleOwnerChange}
                className="form-control"
                required
              />
            </div>

            <div className="mb-3">
              <label className="form-label">NIC No:</label>
              <input
                type="text"
                name="nicNo"
                value={ownerData.nicNo}
                onChange={handleOwnerChange}
                className="form-control"
                required
              />
            </div>

            <div className="mb-3">
              <label className="form-label">Phone Number:</label>
              <input
                type="text"
                name="phoneNumber"
                value={ownerData.phoneNumber}
                onChange={handleOwnerChange}
                className="form-control"
                required
              />
            </div>

            <div className="mb-3">
              <label className="form-label">Email:</label>
              <input
                type="email"
                name="email"
                value={ownerData.email}
                onChange={handleOwnerChange}
                className="form-control"
                required
              />
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
            >
              Back
            </a>

            <h2 className="text-center mb-4">Register Fuel Station Owner</h2>
            {error && <div className="alert alert-danger">{error}</div>}

            <div className="mb-3">
              <label className="form-label" htmlFor="username">Create Username:</label>
              <input
                type="text"
                id="username"
                name="userName"
                value={ownerData.userName}
                onChange={handleOwnerChange}
                className="form-control"
                placeholder="Enter your username"
                required
              />
            </div>

            <div className="mb-3">
              <label className="form-label" htmlFor="password">Enter Password:</label>
              <input
                type="password"
                id="password"
                name="password"
                value={ownerData.password}
                onChange={handleOwnerChange}
                className="form-control"
                placeholder="Enter your password"
                required
              />
            </div>

            <div className="mb-3">
              <label className="form-label" htmlFor="rePassword">Re-enter Password:</label>
              <input
                type="password"
                id="rePassword"
                name="rePassword"
                value={rePassword}
                onChange={(e) => setRePassword(e.target.value)}
                className="form-control"
                placeholder="Re-enter your password"
                required
              />
            </div>

            <button type="submit" className="btn btn-primary w-100">Submit</button>
          </form>
        </div>
      )}

      <Footer />
    </>
  );
};

export default FuelOwnerRegistration;
