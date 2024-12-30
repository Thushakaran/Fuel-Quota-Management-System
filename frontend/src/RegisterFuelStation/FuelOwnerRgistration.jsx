import React, { useState } from 'react';
import { ownerregister } from '../Services/FuelStationService';
import './FuelStationRegistration.css';
import { useNavigate } from 'react-router-dom';

const FuelOwnerRegistration = () => {

  const [path,setPath] = useState(1);
  const navigate = useNavigate();

  //for saving Owner details
  const [ownerData, setOwnerData] = useState({
    name: '',
    nicNo: '',
    phoneNumber: '',
    email: '',
  });

  const [error, setError] = useState('');

  //storing the owner values
  const handleOwnerChange = (e) => {
    const { name, value } = e.target;
    setOwnerData({
      ...ownerData,
      [name]: value,
    });
  };


  const reset = () => {
    setOwnerData({
      name: '',
      nicNo: '',
      phoneNumber: '',
      email: '',
    });
  };

  const handlePathChange = (e) => {
    e.preventDefault();
    setPath(2);
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    setError(''); // Clear any previous error message

    ownerregister(ownerData)
      .then((response) => {
        console.log('Success:', response.data);
        reset(); // Reset the form
        // Navigate to the owner's page using the returned owner ID
        setPath(2);
        const ownerId = response.data.id; // Assuming response includes an ID

        // navigate(`/owner/${ownerId}`);
      })
      .catch((error) => {
        console.error('Error registering fuel station owner:', error);
        setError('Registration failed. Please check your details and try again.');
      });
  };

  return (
    <>
    <br/>
    <br/>
    
     {path === 1 && (<div className="register-container">
      <form onSubmit={handleSubmit}>
        <h2>Register Fuel Station Owner</h2>
        {error && <p className="error-message">{error}</p>}
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
    )}

    {path === 2 && (
      <div>
        <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="username">Create Username:</label>
          <input
            type="text"
            id="username"
            name="username"
            placeholder="Enter your username"
            required
          />
        </div>
        <div>
          <label htmlFor="password">Enter Password:</label>
          <input
            type="password"
            id="password"
            name="password"
            placeholder="Enter your password"
            required
          />
        </div>
        <div>
          <label htmlFor="password">ReEnter Password:</label>
          <input
            type="password"
            id="password"
            name="password"
            placeholder="Enter your password"
            required
          />
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
    )}
    </>
  );
};

export default FuelOwnerRegistration;
