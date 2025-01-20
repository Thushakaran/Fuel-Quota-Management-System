import React, { useState } from "react";
import "../css/Login.css";
import { useNavigate, Link } from "react-router-dom";
import { login, getownerid, getstationid } from "../Services/FuelStationService";

const LoginComponent = ({ heading, registrationLink, registrationText }) => {
  const [loginData, setLoginData] = useState({ userName: "", password: "" });
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  // Handle input field changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginData((prevData) => ({ ...prevData, [name]: value }));
  };

  // Handle fetching owner details and navigation
  const fetchOwnerDetails = async (loginId) => {
    try {
      const response = await getownerid(loginId);
      const ownerId = response.data; // Assuming response.data contains the owner ID
      navigate(`/owner/${ownerId}`);
    } catch (error) {
      console.error(error);
      setError("Failed to fetch owner details.");
    }
  };

  // Handle fetching station details and navigation
  const fetchStationDetails = async (loginId) => {
    try {
      const response = await getstationid(loginId);
      const stationId = response.data; // Assuming response.data contains the station ID
      navigate(`/station/${stationId}`);
    } catch (error) {
      console.error(error);
      setError("Failed to fetch station details.");
    }
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null); // Clear previous errors

    try {
      const response = await login(loginData);
      const { token, role: { name: role }, id: loginId } = response.data;

      localStorage.setItem("token", token);

      switch (role) {
        case "admin":
          navigate("/admin-dashboard");
          break;
        case "stationowner":
          await fetchOwnerDetails(loginId);
          break;
        case "station":
          await fetchStationDetails(loginId);
          break;
        default:
          setError("Unknown role, cannot navigate.");
      }
    } catch (error) {
      console.error(error);
      setError("Login failed. Please check your credentials.");
    }
  };

  return (
    <div className="login-container">
      <h2>{heading}</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="userName">Username:</label>
          <input
            type="text"
            id="userName"
            name="userName"
            placeholder="Enter your username"
            value={loginData.userName}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            name="password"
            placeholder="Enter your password"
            value={loginData.password}
            onChange={handleChange}
            required
          />
        </div>
        {error && <p className="error-message">{error}</p>}
        <button type="submit">Submit</button>
      </form>
      <p>
        Not registered? <Link to={registrationLink}>{registrationText}</Link>
      </p>
    </div>
  );
};

export const OwnerLogin = () => (
  <LoginComponent
    heading="FuelStation Owner Login"
    registrationLink="/ownerreg"
    registrationText="Owner Registration"
  />
);

export const StationLogin = () => (
  <LoginComponent
    heading="FuelStation Login"
    registrationLink="/stationreg"
    registrationText="Station Registration"
  />
);
