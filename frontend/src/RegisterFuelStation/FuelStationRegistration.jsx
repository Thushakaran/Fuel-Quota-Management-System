import React, { useState } from 'react';
import axios from 'axios';
import "./FuelStationRegistration.css"

const FuelStationRegistration = () => {
  const [fuelStationData, setFuelStationData] = useState({
    stationName: '',
    registrationNumber: '',
    location: '',
    fuelInventory: '',
  });

  const [ownerData, setOwnerData] = useState({
    name: '',
    nicNo: '',
    phoneNumber: '',
    email: '',
  });

   // Step 1: Fuel Station, Step 2: Owner
  const [step, setStep] = useState(1);

  const handleStationChange = (e) => {
    const { name, value } = e.target;
    setFuelStationData({
      ...fuelStationData,
      [name]: value,
    });
  };

  const handleOwnerChange = (e) => {
    const { name, value } = e.target;
    setOwnerData({
      ...ownerData,
      [name]: value,
    });
  };

  const handleContinue = (e) => {
    // check Station RegistationNo available
    

    setStep(2);
  };

  const checkDataMatchWithDB = (e) => {

  }

  const resetFuelStation = () =>{
    setFuelStationData({
      stationName: '',
      registrationNumber: '',
      location: '',
      fuelInventory: '',
    });
  }
  const resetOwner = () => {
    setOwnerData({
      name: '',
      nicNo: '',
      phoneNumber: '',
      email: '',
    });
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post('/api/fuel-station/register',fuelStationData).then(
        (response) => 
            {console.log(Succes);
                resetFuelStation;
        }
    ).catch((error) => {
        console.error('Error registering fuel station:', error);
        alert('Registration failed. Please try again.');
      });

    
    axios.post('/api/owner/register',ownerData).then(
        (response) => 
            {console.log(Succes);
                
                setStep(1); 
        }
    ).catch((error) => {
        console.error('Error registering fuel station:', error);
        alert('Registration failed. Please try again.');
      });

    
      
  };

  return (
    
    <div className="register-container">
        
      {step === 1 && (
       
            <form>
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
                <input
                type="number"
                id="fuelInventory"
                name="fuelInventory"
                placeholder="Enter Fuel Inventory"
                value={fuelStationData.fuelInventory}
                onChange={handleStationChange}
                />
            </div>
            <button type="button" onClick={handleContinue}>Continue</button>
            </form>
       
      )}

      {step === 2 && (
        <form onSubmit={handleSubmit}>
          <h2>Register Fuel Station Owner</h2>
          <div>
            <label>Name:</label>
            <input
              type="text"
              name="name"
              value={ownerData.name}
              onChange={handleOwnerChange}
              required
            />
          </div>
          <div>
            <label>NIC No:</label>
            <input
              type="text"
              name="nicNo"
              value={ownerData.nicNo}
              onChange={handleOwnerChange}
              required
            />
          </div>
          <div>
            <label>Phone Number:</label>
            <input
              type="text"
              name="phoneNumber"
              value={ownerData.phoneNumber}
              onChange={handleOwnerChange}
              required
            />
          </div>
          <div>
            <label>Email:</label>
            <input
              type="email"
              name="email"
              value={ownerData.email}
              onChange={handleOwnerChange}
              required
            />
          </div>
          <button type="submit" onClick={()=> re}>Register</button>
        </form>
      )}
    </div>
  );
};

export default FuelStationRegistration;
