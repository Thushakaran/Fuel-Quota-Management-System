import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

import { getstationid} from "../api/FuelStationServiceApi.js";
import {login} from '../api/CommonApi.js'
import {getownerid }from '../api/FuelStationOwnerServiceApi.js'
import { getvehicleid } from "../api/vehicleApi.js";

import Navbar from "../components/Navbar";

const LoginComponent = ({ heading, registrationLink, registrationText, image, Notregistered }) => {
  const [loginData, setLoginData] = useState({ userName: "", password: "" });
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginData((prevData) => ({ ...prevData, [name]: value }));
  };

  const fetchOwnerDetails = async (loginId) => {
    try {
      const response = await getownerid(loginId);
      const ownerId = response.data;
      navigate(`/owner/${ownerId}`);
    } catch (error) {
      console.error("Error fetching owner details:", error);
      setError("Failed to fetch owner details.");
    }
  };

  const fetchStationDetails = async (loginId) => {
    try {
      const response = await getstationid(loginId);
      const stationId = response.data;
      navigate(`/station/${stationId}`);
    } catch (error) {
      console.error("Error fetching station details:", error);
      setError("Failed to fetch station details.");
    }
  };

  
  const fetchVehicleDetails = async (loginId) => {
    try {
      const response = await getvehicleid(loginId);
      const vehicleId = response.data; // Change variable name
      console.log(vehicleId);
      navigate(`/vehicle/${vehicleId}`);
    } catch (error) {
      console.error("Error fetching vehicle details:", error);
      setError("Failed to fetch vehicle details.");
    }
  };


  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    console.log(loginData);
    try {
      const response = await login(loginData);
      const {
        token,
        role: { name: role },
        id: loginId,
      } = response.data;

      localStorage.setItem("token", token);

      switch (role) {
        case "admin":
          navigate("/adminDashboard");
          break;
        case "stationowner":
          await fetchOwnerDetails(loginId);
          break;
        case "station":
          await fetchStationDetails(loginId);
          break;
        case "vehicle":
          await fetchVehicleDetails(loginId);


          break;
        default:
          setError("Unknown role, cannot navigate.");
      }
    } catch (error) {
      console.error("Login error:", error);
      setError("Login failed. Please check your credentials.");
    }
  };

  return (
    <>
    <Navbar/>
    <br/>
    <div className="container justify-content-center card shadow-sm col-md-4 col-lg-5">
            <div className="row g-0">
              <div
                className="col-md-6"
                style={{
                  backgroundImage: `url(${image})`,
                  backgroundRepeat: "no-repeat",
                  backgroundPosition: "center",
                  backgroundSize: "cover",
                }}
              ></div>
              <div className="col-md-6 p-4">
                <h2>{heading}</h2>
                <form onSubmit={handleSubmit}>
                  <div className="mb-3">
                    <label htmlFor="userName" className="form-label">
                      Username
                    </label>
                    <input
                      type="text"
                      id="userName"
                      name="userName"
                      placeholder="Enter your username"
                      value={loginData.userName}
                      onChange={handleChange}
                      required
                      className="form-control"
                    />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="password" className="form-label">
                      Password
                    </label>
                    <input
                      type="password"
                      id="password"
                      name="password"
                      placeholder="Enter your password"
                      value={loginData.password}
                      onChange={handleChange}
                      required
                      className="form-control"
                    />
                  </div>
                  {error && <p className="text-danger">{error}</p>}
                  <button
                    type="submit"
                    className="btn btn-primary w-100 py-2"
                  >
                    Submit
                  </button>
                </form>
                <p className="mt-3">
                  {Notregistered}
                  <Link to={registrationLink} className="text-primary">
                    {registrationText}
                  </Link>
                </p>
              </div>
            </div>
    </div>
    </>
  );
};

export const OwnerLogin = () => (
  <LoginComponent
    heading="FuelStation Owner Login"
    image="https://png.pngtree.com/png-clipart/20230914/original/pngtree-business-owner-vector-png-image_11243661.png"
    Notregistered = "Notregistered  "
    registrationLink="/ownerreg"
    registrationText="Owner Registration"
  />
);

export const StationLogin = () => (
  <LoginComponent
    heading="FuelStation Login"
    image="https://mummyfever.co.uk/wp-content/uploads/2020/07/crop-woman-taking-refueling-pistol-gun-4173096-1024x1536.jpg.webp"
    Notregistered = "Notregistered  "
    registrationLink="/stationreg"
    registrationText="Station Registration"
  />
);

export const VehicleLogin = () => (
  <LoginComponent
    heading="Vehicle Login"
    image="https://files.oaiusercontent.com/file-RBz1eqcSUumAXYaRYsapiq?se=2025-01-21T09%3A52%3A29Z&sp=r&sv=2024-08-04&sr=b&rscc=max-age%3D604800%2C%20immutable%2C%20private&rscd=attachment%3B%20filename%3D04800321-b05b-4039-a339-fe2f4c26d4ce.webp&sig=TyMooMIuoI9G5NCwrGZyqnh3M9Yei%2B9Byj3f%2BijyP/c%3D"
    Notregistered = "Notregistered  "
    registrationLink="/vehicle-registration"
    registrationText="Vehicle Registration"
  />
);

export const AdminLogin = () => (
  <LoginComponent 
    heading="Admin Login"
    image="https://thumbs.dreamstime.com/z/illustration-software-development-d-web-programming-art-programmer-eyeglasses-sitting-laptop-working-coding-278740366.jpg"
    Notregistered = " "
    registrationLink=" "
    registrationText=" "
    />
);
