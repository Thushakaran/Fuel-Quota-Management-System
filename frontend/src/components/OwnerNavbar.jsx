import React from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const OwnerNavbar = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  //clear token and navigate to home page
  const handleLogout = () => {
    const userConfirmed = window.confirm("Are you sure you want to log out?");
    if (userConfirmed) {
      localStorage.removeItem("token");
      navigate("/"); // Navigate first
      toast.success("Logged out successfully!"); // Show toast after navigation
    } else {
      toast.info("Logout canceled.");
    }
  };
  
  return (
    <>
      <ToastContainer position="top-center" autoClose={5000} />
      <nav
        className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm"
        style={{ position: "relative" }}
      >
        <div className="container-fluid">
          <Link
            className="navbar-brand d-flex align-items-center ps-3 ms-3"
            to="/"
            style={{ fontSize: "1.5rem" }}
          >
            <i
              className="bi bi-fuel-pump-fill me-2"
              style={{ color: "#ffdd57" }}
            ></i>
            Fuel Quota System
          </Link>

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
                  <Link to={`/owner/${id}/stationreg`} className="dropdown-item">
                    Register Station
                  </Link>
                </li>
                <li>
                  <hr className="dropdown-divider" />
                </li>
                <li>
                  <button onClick={handleLogout} className="dropdown-item text-danger">
                    Logout
                  </button>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </nav>
    </>
  );
};

export default OwnerNavbar;
