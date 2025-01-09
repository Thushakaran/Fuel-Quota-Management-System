import React, { useState } from 'react';
import { fuelstationregister } from "../Services/FuelStationService";
import { useParams , useNavigate} from 'react-router-dom';
import '../css/Registration.css';
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";


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
  const navigate = useNavigate();

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
          const station_id = response.data;
          console.log('Success:', response.data);
          alert('Fuel station registered successfully!');
          navigate(`/station/${station_id}`);
        })
        .catch((error) => {
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
            <h2 className="text-center mb-4">Register Fuel Station</h2>
            <div className="mb-3">
              <label htmlFor="stationName" className="form-label">Station Name:</label>
              <input
                type="text"
                id="stationName"
                name="stationName"
                placeholder="Enter Station Name"
                value={fuelStationData.stationName}
                onChange={handleStationChange}
                className="form-control"
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="registrationNumber" className="form-label">Registration Number:</label>
              <input
                type="text"
                id="registrationNumber"
                name="registrationNumber"
                placeholder="Enter Registration Number"
                value={fuelStationData.registrationNumber}
                onChange={handleStationChange}
                className="form-control"
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="location" className="form-label">Location:</label>
              <input
                type="text"
                id="location"
                name="location"
                placeholder="Enter Location"
                value={fuelStationData.location}
                onChange={handleStationChange}
                className="form-control"
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="fuelInventory" className="form-label">Fuel Inventory:</label>
              <div className='adjust'>
                <div className="form-check">
                  <input
                    type="checkbox"
                    value="92-Octane"
                    onChange={(e) => handleFuelChange(e.target.value)}
                    className="form-check-input"
                  />
                  <label className="form-check-label">92-Octane</label>
                </div>
                <div className="form-check">
                  <input
                    type="checkbox"
                    value="95-Octane"
                    onChange={(e) => handleFuelChange(e.target.value)}
                    className="form-check-input"
                  />
                  <label className="form-check-label">95-Octane</label>
                </div>
                <div className="form-check">
                  <input
                    type="checkbox"
                    value="Auto Diesel"
                    onChange={(e) => handleFuelChange(e.target.value)}
                    className="form-check-input"
                  />
                  <label className="form-check-label">Auto Diesel</label>
                </div>
                <div className="form-check">
                  <input
                    type="checkbox"
                    value="Super Diesel"
                    onChange={(e) => handleFuelChange(e.target.value)}
                    className="form-check-input"
                  />
                  <label className="form-check-label">Super Diesel</label>
                </div>
              </div>
            </div>
            {error && <div className="alert alert-danger">{error}</div>}
            <button type="submit" className="btn btn-primary w-100">Next</button>
          </form>
        </div>
      )}

      {path === 2 && (
        <div className="register-container container mt-5">
          <form onSubmit={handleSubmit} className="p-4 border rounded bg-light">
            <h2 className="text-center mb-4">Register Fuel Station</h2>
            <div className="mb-3">
              <label htmlFor="stationUserName" className="form-label">Station UserName:</label>
              <input
                type="text"
                id="stationUserName"
                name="stationUserName"
                placeholder="Enter Station Name"
                value={fuelStationData.stationUserName}
                onChange={handleStationChange}
                className="form-control"
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="password" className="form-label">Password:</label>
              <input
                type="password"
                id="password"
                name="password"
                placeholder="Enter Password"
                value={fuelStationData.password}
                onChange={handleStationChange}
                className="form-control"
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="rePassword" className="form-label">Confirm Password:</label>
              <input
                type="password"
                id="rePassword"
                name="rePassword"
                placeholder="Confirm Password"
                value={rePassword}
                onChange={(e) => setRePassword(e.target.value)}
                className="form-control"
                required
              />
            </div>
            {error && <div className="alert alert-danger">{error}</div>}
            <button type="submit" className="btn btn-primary w-100">Submit</button>
          </form>
        </div>
      )}
      {/* temporayFooter  */}
      <Footer/>
    </>
  );
};

export default FuelStationRegistration;
