import React from "react";
import { Link } from "react-router-dom";
import "../css/Navbar.css";

const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
      <a
        className="navbar-brand d-flex align-items-center ps-3 ms-3"
        href="/"
        style={{ fontSize: "1.5rem" }}
      >
        <i
          className="bi bi-fuel-pump-fill me-2"
          style={{ color: "#ffdd57" }}
        ></i>
        Fuel Quota System
      </a>
      <button
        className="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span className="navbar-toggler-icon"></span>
      </button>
      <div className="collapse navbar-collapse" id="navbarNav">
        <ul className="navbar-nav ms-auto">
          <li className="nav-item">
            <Link className="nav-link d-flex align-items-center ms-3" to="/">
              <i
                className="bi bi-house-door-fill me-2"
                style={{ color: "#ffc107" }}
              ></i>
              Home
            </Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link d-flex align-items-center ms-3" to="/about">
              <i
                className="bi bi-info-circle-fill me-2"
                style={{ color: "#007bff" }}
              ></i>
              About
            </Link>
          </li>
          <li className="nav-item dropdown">
            <a
              className="nav-link dropdown-toggle d-flex align-items-center ms-3"
              href="#"
              id="registerDropdown"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <i
                className="bi bi-person-plus-fill me-2"
                style={{ color: "#28a745" }}
              ></i>
              Register
            </a>
            <ul className="dropdown-menu" aria-labelledby="registerDropdown">
              <li>
                <Link className="dropdown-item" to="/vehicle-registration">
                  Vehicle Registration
                </Link>
              </li>
              <li>
                <Link className="dropdown-item" to="/ownerreg">
                  Fuel Station Owner Registration
                </Link>
              </li>
            </ul>
          </li>
          <li className="nav-item dropdown">
            <a
              className="nav-link dropdown-toggle d-flex align-items-center ms-3"
              href="#"
              id="loginDropdown"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <i
                className="bi bi-box-arrow-in-right me-2"
                style={{ color: "#ff5733" }}
              ></i>
              Login
            </a>
            <ul className="dropdown-menu" aria-labelledby="loginDropdown">
              <li>
                <Link className="dropdown-item" to="/vehiclelogin">
                  Vehicle Owner 
                </Link>
              </li>
              <li>
                <Link className="dropdown-item" to="/ownerlogin">
                  Station Owner 
                </Link>
              </li>
              <li>
                <Link className="dropdown-item" to="/stationlogin">
                  Station 
                </Link>
              </li>
              <li>
                <Link className="dropdown-item" to="/adminlogin">
                  Admin 
                </Link>
              </li>
            </ul>
          </li>
          <li className="nav-item ms-5"> {/* Added ms-5 for more space after the Login link */}
            {/* Additional content can go here if needed */}
          </li>
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;
