import React, { useState } from 'react';
import { fuelstationregister } from "../Services/FuelStationService";
import '../css/FuelStationRegistration.css';
import { useParams } from 'react-router-dom';

const FuelStationRegistration = () => {
  const { id } = useParams();
  const [path, setPath] = useState(1);
  const [fuelStationData, setFuelStationData] = useState({
    stationName: '',
    registrationNumber: '',
    location: '',
    ownerId: '',
    fuelType: [],
    stationUserName: '',
    password: ''
  });
  const [fuel, setFuel] = useState([]);
  const [rePassword, setRePassword] = useState('');
  const [error, setError] = useState('');

  const handleFuelChange = (value) => {
    setFuel((prevFuel) => {
      const updatedFuel = prevFuel.includes(value)
        ? prevFuel.filter((item) => item !== value)
        : [...prevFuel, value];
      return updatedFuel;
    });
  };

  const handleStationChange = (e) => {
    const { name, value } = e.target;
    setFuelStationData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (path === 1) {
      if (!fuelStationData.stationName || !fuelStationData.registrationNumber || !fuelStationData.location || fuel.length === 0) {
        setError('Please fill out all required fields.');
        return;
      }
      setFuelStationData((prev) => ({
        ...prev,
        ownerId: id,
        fuelType: fuel,
      }));
      setError('');
      setPath(2);
    } else if (path === 2) {
      if (!fuelStationData.stationUserName || !fuelStationData.password) {
        setError('Username and password are required.');
        return;
      }
      if (fuelStationData.password !== rePassword) {
        setError('Passwords do not match.');
        return;
      }
      setError('');
      fuelstationregister(fuelStationData)
        .then((response) => {
          console.log('Success:', response.data);
          alert('Fuel station registered successfully!');
        })
        .catch((error) => {
          console.error('Error registering fuel station:', error);
          alert('Registration failed. Please try again.');
        });
    }
  };

  return (
    <>
      <br />
      <br />
      {path === 1 && (
        <div className="register-container">
          <form onSubmit={handleSubmit}>
            <h2>Register Fuel Station</h2>
            <div>
              <label htmlFor="stationName">Station Name:</label>
              <input
                type="text"
                id="stationName"
                name="stationName"
                placeholder="Enter Station Name"
                value={fuelStationData.stationName}
                onChange={handleStationChange}
                required
              />
            </div>
            <div>
              <label htmlFor="registrationNumber">Registration Number:</label>
              <input
                type="text"
                id="registrationNumber"
                name="registrationNumber"
                placeholder="Enter Registration Number"
                value={fuelStationData.registrationNumber}
                onChange={handleStationChange}
                required
              />
            </div>
            <div>
              <label htmlFor="location">Location:</label>
              <input
                type="text"
                id="location"
                name="location"
                placeholder="Enter Location"
                value={fuelStationData.location}
                onChange={handleStationChange}
                required
              />
            </div>
            <div>
              <label htmlFor="fuelInventory">Fuel Inventory:</label>
              <div className="check-box">
                <input
                  type="checkbox"
                  value="92-Octane"
                  onChange={(e) => handleFuelChange(e.target.value)}
                />
                <div className="name">92-Octane</div>
                <input
                  type="checkbox"
                  value="95-Octane"
                  onChange={(e) => handleFuelChange(e.target.value)}
                />
                <div className="name">95-Octane</div>
                <input
                  type="checkbox"
                  value="Auto Diesel"
                  onChange={(e) => handleFuelChange(e.target.value)}
                />
                <div className="name">Auto Diesel</div>
                <input
                  type="checkbox"
                  value="Super Diesel"
                  onChange={(e) => handleFuelChange(e.target.value)}
                />
                <div className="name">Super Diesel</div>
              </div>
            </div>
            {error && <div className="error-message">{error}</div>}
            <button type="submit">Next</button>
          </form>
        </div>
      )}

      {path === 2 && (
        <div className="register-container">
          <form onSubmit={handleSubmit}>
            <h2>Register Fuel Station</h2>
            <div>
              <label htmlFor="stationUserName">Station UserName:</label>
              <input
                type="text"
                id="stationUserName"
                name="stationUserName"
                placeholder="Enter Station Name"
                value={fuelStationData.stationUserName}
                onChange={handleStationChange}
                required
              />
            </div>
            <div>
              <label htmlFor="password">Password:</label>
              <input
                type="password"
                id="password"
                name="password"
                placeholder="Enter Password"
                value={fuelStationData.password}
                onChange={handleStationChange}
                required
              />
            </div>
            <div>
              <label htmlFor="rePassword">Confirm Password:</label>
              <input
                type="password"
                id="rePassword"
                name="rePassword"
                placeholder="Confirm Password"
                value={rePassword}
                onChange={(e) => setRePassword(e.target.value)}
                required
              />
            </div>
            {error && <div className="error-message">{error}</div>}
            <button type="submit">Submit</button>
          </form>
        </div>
      )}
    </>
  );
};

export default FuelStationRegistration;
