import React, { useState } from 'react';
import { ownerregister } from '../Services/FuelStationService';
import '../css/Registration.css';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

const FuelOwnerRegistration = () => {

  const [path,setPath] = useState(1);
  const navigate = useNavigate();

  //for saving Owner details
  const [ownerData, setOwnerData] = useState({
    ownerName: '',
    nicNo: '',
    phoneNumber: '',
    email: '',
    userName: '',
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
        if (!ownerData.userName || !ownerData.password || ownerData.password !== rePassword) {
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
   {/* temporay Nav bar */}
    <Navbar/>
   
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
          <h2 className="text-center mb-4">Register Fuel Station Owner</h2>
          {error && <div className="alert alert-danger">{error}</div>}
          <div className="mb-3">
            <label className="form-label" htmlFor="username">Create Username:</label>
            <input
              type="text"
              id="username"
              value={ownerData.userName}
              onChange={handleOwnerChange}
              name="userName"
              placeholder="Enter your username"
              className="form-control"
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
              placeholder="Enter your password"
              onChange={handleOwnerChange}
              className="form-control"
              required
            />
          </div>
          <div className="mb-3">
            <label className="form-label" htmlFor="rePassword">ReEnter Password:</label>
            <input
              type="password"
              id="rePassword"
              name="rePassword"
              value={rePassword}
              placeholder="Re-enter your password"
              onChange={(e) => setRePassword(e.target.value)}
              className="form-control"
              required
            />
          </div>
          <button type="submit" className="btn btn-primary w-100">Submit</button>
        </form>
      </div>
    )}
    {/* temporay Footer*/}
    <Footer/>
    </>
  );
};

export default FuelOwnerRegistration;
