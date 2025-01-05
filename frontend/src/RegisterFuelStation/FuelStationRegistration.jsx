import React, { useState } from 'react';
import { fuelstationregister } from "../Services/FuelStationService";
import './FuelStationRegistration.css';

const FuelStationRegistration = () => {
  const [fuelStationData, setFuelStationData] = useState({
    stationName: '',
    registrationNumber: '',
    location: '',
    fuelInventory: [],
  });

  const [fuel, setFuel] = useState([]);

  const handleFuelChange = (value) => {
    setFuel((prevFuel) => {
      const updatedFuel = prevFuel.includes(value)
        ? prevFuel.filter((item) => item !== value)
        : [...prevFuel, value];

      // Update fuel inventory in the main state
      setFuelStationData((prev) => ({ ...prev, fuelInventory: updatedFuel }));
      return updatedFuel;
    });
  };

  const handleStationChange = (e) => {
    const { name, value } = e.target;
    setFuelStationData({
      ...fuelStationData,
      [name]: value,
    });
  };

  const reset = () => {
    setFuelStationData({
      stationName: '',
      registrationNumber: '',
      location: '',
      fuelInventory: [],
    });
    setFuel([]);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
      fuelstationregister(fuelStationData).then((response) => {
        console.log('Success:', response.data);
        alert('Fuel station registered successfully!');
        reset();
      })
      .catch((error) => {
        console.error('Error registering fuel station:', error);
        alert('Registration failed. Please try again.');
      });
  };

  return (
    <>
      <br />
      <br />
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
                value="Petrol 92-Octane"
                onChange={(e) => handleFuelChange(e.target.value)}
              />
              <div className='name'>92-Octane</div>
              <input
                type="checkbox"
                value="Petrol 95-Octane"
                onChange={(e) => handleFuelChange(e.target.value)}
              />
              <div className='name'>95-Octane</div>
              <input
                type="checkbox"
                value="Auto Diesel"
                onChange={(e) => handleFuelChange(e.target.value)}
              />
              <div className='name'>Auto Diesel</div>
              <input
                type="checkbox"
                value="Super Diesel"
                onChange={(e) => handleFuelChange(e.target.value)}
              />
              <div className='name'>Super Diesel
            </div>
          </div>
          </div>
          <button type="submit">Submit</button>
        </form>
      </div>
    </>
  );
};

export default FuelStationRegistration;
