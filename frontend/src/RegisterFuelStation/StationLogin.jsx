import React, { useState } from "react";
import "./Login.css"; 
import { Link } from "react-router-dom";

const StationLogin = () => {
  const [loginData, setLoginData] = useState({
    stationid: "",
    password: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginData({
      ...loginData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Login Data Submitted:", loginData);
    // l
    if (loginData.stationid === "admin" && loginData.password === "password") {
      alert("Login successful!");
    } else {
      alert("Invalid credentials. Please try again.");
    }
  };

  return (
    <div className="login-container">
      <h2>FuelStation Login</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="stationid">StationID:</label>
          <input
            type="text"
            id="stationid"
            name="stationid"
            placeholder="Enter your stationid"
            value={loginData.stationid}
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
        Not registered? <Link to={"/stationreg"}>Register</Link>
      </p>
    </div>
  );
};

export default StationLogin;
