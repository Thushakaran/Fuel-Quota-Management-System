import React from 'react';
import { Link } from 'react-router-dom';

const AdminNavbar = () => {
  return (
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
              <Link className="nav-link active" to="/">
                <i className="bi bi-house-door me-2"></i>Home
              </Link>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#user-management">
                <i className="bi bi-person-lines-fill me-2"></i>User Management
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#transactions">
                <i className="bi bi-credit-card-2-front me-2"></i>Transactions
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#settings">
                <i className="bi bi-gear me-2"></i>Settings
              </a>
            </li>
            {/* Logout option */}
            <li className="nav-item">
              <a className="nav-link" href="#logout">
                <i className="bi bi-box-arrow-right me-2"></i>Logout
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default AdminNavbar;
