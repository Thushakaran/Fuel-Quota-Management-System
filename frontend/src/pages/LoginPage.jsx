import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { getstationid } from "../api/FuelStationServiceApi.js";
import { login } from "../api/CommonApi.js";
import { getownerid } from "../api/FuelStationOwnerServiceApi.js";
import { getvehicleid } from "../api/vehicleApi.js";
import fuelStationOwnerImage from "../Photo/fuelstationOwner.png";
import fuelStationImage from "../Photo/fuelstation.webp";
import vehicleImage from "../Photo/vehiclelogin.jpeg";
import adminImage from "../Photo/admin.png"

import Navbar from "../components/Navbar";

const LoginComponent = ({ heading, registrationLink, registrationText, image, Notregistered }) => {
  const [loginData, setLoginData] = useState({ userName: "", password: "" });
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginData((prevData) => ({ ...prevData, [name]: value }));
  };

  const fetchOwnerDetails = async (loginId) => {
    try {
      const response = await getownerid(loginId);
      navigate(`/owner/${response.data}`);
    } catch (error) {
      toast.error("Failed to fetch owner details!");
    }
  };

  const fetchStationDetails = async (loginId) => {
    try {
      const response = await getstationid(loginId);
      navigate(`/station/${response.data}`);
    } catch (error) {
      toast.error("Failed to fetch station details!");
    }
  };

  const fetchVehicleDetails = async (loginId) => {
    try {
      const response = await getvehicleid(loginId);
      navigate(`/vehicle/${response.data}`);
    } catch (error) {
      toast.error("Failed to fetch vehicle details!");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await login(loginData);
      const { token, role: { name: role }, id: loginId } = response.data;

      localStorage.setItem("token", token);

      switch (role) {
        case "ADMIN":
          navigate("/adminDashboard");
          break;
        case "STATIONOWNER":
          await fetchOwnerDetails(loginId);
          break;
        case "STATION":
          await fetchStationDetails(loginId);
          break;
        case "VEHICLE":
          await fetchVehicleDetails(loginId);
          break;
        default:
          toast.error("Unknown role, cannot navigate!");
      }
    } catch (error) {
      toast.error("Login failed. Please check your credentials!");
    }
  };

  return (
    <>
      <Navbar />
      <ToastContainer position="top-center" autoClose={10000} />
      <div className="container d-flex justify-content-center align-items-center min-vh-100">
        <div className="card shadow-lg border-0 col-md-10 col-lg-8 p-5">
          <div className="row g-0">
            <div
              className="col-md-6 d-none d-md-block"
              style={{
                backgroundImage: `url('${image}')`,
                backgroundSize: "cover",
                backgroundPosition: "center",
                borderRadius: "10px 0 0 10px",
              }}
            ></div>
            <div className="col-md-6 p-5">
              <h2 className="text-center mb-4">{heading}</h2>
              <form onSubmit={handleSubmit}>
                <div className="mb-4">
                  <label htmlFor="userName" className="form-label">
                    <i className="fas fa-user me-2"></i>Username
                  </label>
                  <input
                    type="text"
                    id="userName"
                    name="userName"
                    placeholder="Enter your username"
                    value={loginData.userName}
                    onChange={handleChange}
                    required
                    className="form-control rounded-pill"
                  />
                </div>
                <div className="mb-4">
                  <label htmlFor="password" className="form-label">
                    <i className="fas fa-lock me-2"></i>Password
                  </label>
                  <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="Enter your password"
                    value={loginData.password}
                    onChange={handleChange}
                    required
                    className="form-control rounded-pill"
                  />
                </div>
                <button
                  type="submit"
                  className="btn btn-primary w-100 py-3 rounded-pill shadow-sm"
                >
                  Login
                </button>
              </form>
              {Notregistered && (
                <p className="mt-4 text-center">
                  {Notregistered}
                  <Link to={registrationLink} className="text-primary fw-bold">
                    {registrationText}
                  </Link>
                </p>
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export const OwnerLogin = () => (
  <LoginComponent
    heading="FuelStation Owner Login"
    image={fuelStationOwnerImage}
    Notregistered="Not registered yet? "
    registrationLink="/ownerreg"
    registrationText="Register as Owner"
  />
);

export const StationLogin = () => (
  <LoginComponent
    heading="FuelStation Login"
    image={fuelStationImage}
  />
);

export const VehicleLogin = () => (
  <LoginComponent
    heading="Vehicle Login"
    image={vehicleImage}
    Notregistered="Not registered yet? "
    registrationLink="/vehicle-registration"
    registrationText="Register your Vehicle"
  />
);

export const AdminLogin = () => (
  <LoginComponent
    heading="Admin Login"
    image={adminImage}
  />
);
