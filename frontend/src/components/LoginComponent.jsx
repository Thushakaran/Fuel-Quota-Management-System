import React, { useState } from "react";
import "../css/Login.css"; 
import { useNavigate, Link } from "react-router-dom";
import { login, getownerid, getstationid } from "../Services/FuelStationService";

const LoginComponent = ({ heading, registrationLink, registrationText }) => {
  const [loginData, setLoginData] = useState({
    userName: "",
    password: "",
  });
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginData({
      ...loginData,
      [name]: value,
    });
  };

  const navigate = useNavigate();

  const findownerid = (loginid) => {
    getownerid(loginid)
      .then((response) => {
        console.log(response.data)
        const ownerId = response.data; // Assuming response.data is the ID
        navigate(`/owner/${ownerId}`);
      })
      .catch((error) => {
        console.error(error);
        setError("Failed to fetch owner details.");
      });
  };

  const findstationid = (loginid) => {
    getstationid(loginid)
      .then((response) => {
        console.log(response.data)
        const stationId = response.data; // Assuming response.data is the ID
        navigate(`/station/${stationId}`);
      })
      .catch((error) => {
        console.error(error);
        setError("Failed to fetch station details.");
      });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setError(null); // Clear previous errors
    console.log("Login Data Submitted:", loginData);
    login(loginData)
      .then((response) => {
        console.log(response.data);
        const token = response.data.token;
        const role = response.data.role.name;
        const loginid = response.data.id;
        localStorage.setItem("jwtToken", token);

        switch (role) {
          case "admin":
            navigate("/admin-dashboard");
            break;
          case "stationowner":
            findownerid(loginid);
            break;
          case "station":
            findstationid(loginid);
            break;
          default:
            setError("Unknown role, cannot navigate.");
        }
      })
      .catch((error) => {
        console.error(error);
        setError("Login failed. Please check your credentials.");
      });
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
