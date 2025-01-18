import React from "react";
import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";

const OwnerNavbar = () => {
  const { id } = useParams();

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
                  style={{ color: "#ffdd57", zoom:2}}
                ></i>
              </div>
            </button>
            <ul
              className="dropdown-menu dropdown-menu-end"
              aria-labelledby="profileDropdown"
            >
              <li>
                <Link to="/profile" className="dropdown-item">
                  Profile
                </Link>
              </li>
              <li>
                <Link to={`/owner/${id}/stationreg`} className="dropdown-item">
                  Register Station
                </Link>
              </li>
              <li>
                <hr className="dropdown-divider" />
              </li>
              <li>
                <Link to="/logout" className="dropdown-item">
                  Logout
                </Link>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default OwnerNavbar;
