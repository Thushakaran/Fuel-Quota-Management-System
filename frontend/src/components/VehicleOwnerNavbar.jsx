import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";

const VehicleOwnerNavbar = () => {
  const { id } = useParams();
  const navigate = useNavigate(); // Hook for navigation in React Router v6

  // Logout function to clear token and navigate to home page
  const handleLogout = () => {
    localStorage.removeItem('token'); // Remove token from localStorage
    navigate('/'); // Navigate to the home page
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
      <div className="container-fluid">
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

        <div className="d-flex align-items-center ms-auto">
          <div className="dropdown">
            <button
              className="btn btn-link p-0"
              type="button"
              id="profileDropdown"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <div
                className="rounded-circle bg-white d-flex justify-content-center align-items-center"
                style={{ width: "45px", height: "45px" }}
              >
                <i
                  className="bi bi-person-gear"
                  style={{ color: "#ffdd57", zoom: 2 }}
                ></i>
              </div>
            </button>
            <ul
              className="dropdown-menu dropdown-menu-end"
              aria-labelledby="profileDropdown"
            >
              <li>
                <Link to={`/owner/${id}/profile`} className="dropdown-item">
                  Profile
                </Link>
              </li>
              <li>
                <hr className="dropdown-divider" />
              </li>
              <li>
                <button onClick={handleLogout} className="dropdown-item">
                  Logout
                </button>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default VehicleOwnerNavbar;
