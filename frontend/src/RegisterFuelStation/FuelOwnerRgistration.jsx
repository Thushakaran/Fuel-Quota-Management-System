import React, { useState } from 'react';
import {ownerregister} from '../Services/FuelStationService'
import "./FuelStationRegistration.css";

const FuelOwnerRegistration = () => {
  const [ownerData, setOwnerData] = useState({
    name: '',
    nicNo: '',
    phoneNumber: '',
    email: '',
  });

  const handleOwnerChange = (e) => {
    const { name, value } = e.target;
    setOwnerData({
      ...ownerData,
      [name]: value,
    });
  };

  const reset = (e) =>{
    setOwnerData({
          name: '',
          nicNo: '',
          phoneNumber: '',
          email: '',
        });
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    ownerregister(ownerData).then(
        (response) => 
            {console.log("Succes");
              reset()               
          }
    ).catch((error) => {
        console.error('Error registering fuel station:', error);
        alert('Registration failed. Please try again.');
      });     
    }


  return (
    <div className="register-container">
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
        <button type="submit">Register</button>
        </form>
    </div>
  );
};

export default FuelOwnerRegistration;
