import React, { useState } from "react";
import "./Login.css"; 
import { useNavigate , Link} from "react-router-dom";


const LoginOwner = () => {
  const [loginData, setLoginData] = useState({
    username: "",
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
    if (loginData.username === "admin" && loginData.password === "password") {
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
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            name="username"
            placeholder="Enter your username"
            value={loginData.username}
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
