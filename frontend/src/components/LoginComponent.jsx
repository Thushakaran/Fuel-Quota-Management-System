import React, { useState } from "react";
import "../css/Login.css"; 
import { useNavigate, Link } from "react-router-dom";
import { login } from "../Services/FuelStationService";

const LoginComponent = ({ heading, registrationLink, registrationText }) => {
  const [loginData, setLoginData] = useState({
    userName: "",
    password: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginData({
      ...loginData,
      [name]: value,
    });
  };

  const navigator = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Login Data Submitted:", loginData);
    login(loginData).then((response) => {
      console.log(response.data);
      const token = response.data.token;
      localStorage.setItem('jwtToken', token);
    }).catch((error)=>{
      console.error(error);
    })
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

