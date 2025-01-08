import React, { useState } from "react";
import "../css/Login.css"; 
import { useNavigate , Link} from "react-router-dom";


const LoginOwner = () => {
  const [loginData, setLoginData] = useState({
    ownerUserName: "",
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
    // l
    if (loginData.username === "gamini" && loginData.password === 12345) {
      alert("Login successful!");
    } else {
      alert("Invalid credentials. Please try again.");
    }
  };

  return (
    <div className="login-container">
      <h2>FuelStation Owner Login</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="ownerUserName">Username:</label>
          <input
            type="text"
            id="ownerUserName"
            name="ownerUserName"
            placeholder="Enter your username"
            value={loginData.ownerUserName}
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
        Not registered? <Link to="/ownerreg">Register</Link>
      </p>
    </div>
  );
};

export default LoginOwner;
