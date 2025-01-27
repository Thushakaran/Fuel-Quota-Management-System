import React from 'react';
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Link , useNavigate} from 'react-router-dom';

const AdminNavbar = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    const userConfirmed = window.confirm("Are you sure you want to log out?");
    if (userConfirmed) {
      localStorage.removeItem("token");

      // Show toast first, then navigate after a short delay
      toast.success("Logged out successfully!");
      setTimeout(() => navigate("/"), 1000);
    } else {
      toast.info("Logout canceled.");
    }
  };
  return (
    <>
    <ToastContainer position="top-center" autoClose={5000} />
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-lg">
      <div className="container-fluid">
        <a className="navbar-brand" href="#">
          <strong>Admin Dashboard</strong>
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
              <Link className="nav-link" to="/adminDashboard">
                <i className="bi bi-house-door me-2"></i>Home
              </Link>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/vehicleManagement">
                <i className="bi bi-car-front me-2"></i>Vehicle Management
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/fuelStationManagement">
                <i className="bi bi-fuel-pump me-2"></i>Fuel Station Management
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/transactionManagement">
                <i className="bi bi-credit-card-2-front me-2"></i>Transactions
              </a>
            </li>
            {/* Logout option */}
            <li className="nav-item">
              <a className="nav-link" onClick={handleLogout} >
                <i className="bi bi-box-arrow-right me-2"></i>Logout
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    </>
  );
};

export default AdminNavbar;
