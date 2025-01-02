// src/components/Navbar.js
import React from "react";

const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <a
        className="navbar-brand d-flex align-items-center ps-3"
        href="/"
      >
        <i className="bi bi-fuel-pump-fill me-2" style={{ color: "#ffdd57" }}></i>
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
            <a className="nav-link d-flex align-items-center" href="/login">
              <i className="bi bi-box-arrow-in-right me-2" style={{ color: "#ff5733" }}></i>
              Login
            </a>
          </li>
          <li className="nav-item">
            <a className="nav-link d-flex align-items-center" href="/register">
              <i className="bi bi-person-plus-fill me-2" style={{ color: "#28a745" }}></i>
              Register
            </a>
          </li>
          <li className="nav-item">
            <a className="nav-link d-flex align-items-center" href="/about">
              <i className="bi bi-info-circle-fill me-2" style={{ color: "#007bff" }}></i>
              About Us
            </a>
          </li>
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;

/*
// src/components/Navbar.js
import React from "react";
import { Link } from "react-router-dom";
// import "../css/Navbar.css"

const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
      <a
        className="navbar-brand d-flex align-items-center ps-3"
        href="/"
        style={{ fontSize: "1.5rem" }}
      >
        <i className="bi bi-fuel-pump-fill me-2" style={{ color: "#ffdd57" }}></i>
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
            <Link
              className="nav-link d-flex align-items-center"
              to="/login"
              style={{ fontSize: "1.1rem" }}
            >
              <i className="bi bi-box-arrow-in-right me-2" style={{ color: "#ff5733" }}></i>
              Login
            </Link>
          </li>
          <li className="nav-item">
            <Link
              className="nav-link d-flex align-items-center"
              to="/register"
              style={{ fontSize: "1.1rem" }}
            >
              <i className="bi bi-person-plus-fill me-2" style={{ color: "#28a745" }}></i>
              Register
            </Link>
          </li>
          <li className="nav-item">
            <Link
              className="nav-link d-flex align-items-center"
              to="/about"
              style={{ fontSize: "1.1rem" }}
            >
              <i className="bi bi-info-circle-fill me-2" style={{ color: "#007bff" }}></i>
              About Us
            </Link>
          </li>
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;
*/
