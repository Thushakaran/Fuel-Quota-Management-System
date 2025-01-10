import React, { useState } from 'react';
import { ownerregister } from '../Services/FuelStationService';
import '../css/FuelStationRegistration.css';
import { useNavigate } from 'react-router-dom';

const FuelOwnerRegistration = () => {

  const [path,setPath] = useState(1);
  const navigate = useNavigate();

  //for saving Owner details
  const [ownerData, setOwnerData] = useState({
    ownerName: '',
    nicNo: '',
    phoneNumber: '',
    email: '',
    ownerUSerName: '',
    password: '',
  });
  const [rePassword,setRePassword] = useState('');

  const [error, setError] = useState('');

  //storing the owner values
  const handleOwnerChange = (e) => {
    const { name, value } = e.target;
    setOwnerData({
      ...ownerData,
      [name]: value,
    });
  };

   
    const handleSubmit = (e) => {
      e.preventDefault();
  
      if (path === 1) {
        // Validate form 1
        if (!ownerData.ownerName|| !ownerData.nicNo || !ownerData.phoneNumber || !ownerData.email) {
          setError('Please fill out all required fields.');
          return;
        }
        setPath(2); // Go to the second form
      } else if (path === 2) {
        // Validate form 2 (ensure passwords match)
        if (!ownerData.ownerUserName || !ownerData.password || ownerData.password !== rePassword) {
          setError('Passwords must match.');
          return;
        }
  
        // Final submission logic
        setError('');
        ownerregister(ownerData).then((response) => {
          const id = response.data.id;
          console.log('Success:', response.data);
          alert('Fuel station registered successfully!'); 
          navigate(`/owner/${id}`);

        }).catch((error) => {
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
      <h2>Register Fuel Station Owner</h2>
        {error && <p className="error-message">{error}</p>}
        <div>
          <label>Name:</label>
          <input
            type="text"
            name="ownerName"
            value={ownerData.ownerName}
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
        <button type="submit">Next</button>
      </form>
    </ div>
    )}
    {path === 2 && (
      <div className="register-container">
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="username">Create Username:</label>
              <input
                type="text"
                id="username"
                value={ownerData.ownerUserName}
                onChange={handleOwnerChange}
                name="ownerUserName"
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
                value={ownerData.password}
                placeholder="Enter your password"
                onChange={handleOwnerChange}
                required
                />
          </div>
          <div>
            <label htmlFor="rePassword">ReEnter Password:</label>
              <input
                type="password"
                id="rePassword"
                name="rePassword"
                value={rePassword}
                placeholder="Re-enter your password"
                onChange={(e) => setRePassword(e.target.value)}
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
