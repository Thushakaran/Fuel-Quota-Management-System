import React, { useState, useEffect } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import AdminNavbar from "../components/AdminNavbar";
import AdminFooter from "../components/AdminFooter";
import axios from "../api/axiosInstance";


const AdminDashboard = () => {
  const [data, setData] = useState({
    vehicles: 0,
    stations: 0,
    fuelDistributed: 0,
    transactions: 0,
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get("/admin/dashboard-data");
        setData({
          vehicles: response.data.totalVehicles,
          stations: response.data.totalStations,
          fuelDistributed: response.data.totalFuelDistributed,
          transactions: response.data.totalTransactions,
        });
      } catch (error) {
        console.error("Error fetching dashboard data:", error);
        setError("Unable to load dashboard data. Please try again later.");
      }
    };
  
    fetchData();
  }, []);
  

  return (
    <div className="container-fluid bg-light min-vh-100">
      {/* Navigation Bar */}
      <AdminNavbar />

      {/* Header Section */}
      <header className="bg-primary text-white text-center py-5 mb-4 shadow-sm" style={{ minHeight: "200px" }}>
        <h1 className="display-4">Admin Dashboard</h1>
      </header>

      {/* Overview Section */}
      <main className="row g-4">
        <div className="col-lg-3 col-md-6">
          <div className="card text-center shadow-lg border-0 h-100">
            <div className="card-body">
              <h5 className="card-title text-primary">Total Registered Vehicles</h5>
              <p className="card-text display-4 fw-bold">{data.vehicles}</p>
              <small className="text-muted">Updated just now</small>
            </div>
          </div>
        </div>
        <div className="col-lg-3 col-md-6">
          <div className="card text-center shadow-lg border-0 h-100">
            <div className="card-body">
              <h5 className="card-title text-success">Total Registered Fuel Stations</h5>
              <p className="card-text display-4 fw-bold">{data.stations}</p>
              <small className="text-muted">Updated just now</small>
            </div>
          </div>
        </div>
        <div className="col-lg-3 col-md-6">
          <div className="card text-center shadow-lg border-0 h-100">
            <div className="card-body">
              <h5 className="card-title text-warning">Total Fuel Distributed</h5>
              <p className="card-text display-4 fw-bold">{data.fuelDistributed} liters</p>
              <small className="text-muted">Updated just now</small>
            </div>
          </div>
        </div>
        <div className="col-lg-3 col-md-6">
          <div className="card text-center shadow-lg border-0 h-100">
            <div className="card-body">
              <h5 className="card-title text-danger">Active Transactions</h5>
              <p className="card-text display-4 fw-bold">{data.transactions}</p>
              <small className="text-muted">Updated just now</small>
            </div>
          </div>
        </div>
      </main>

      {/* Footer */}
      <AdminFooter />
    </div>
  );
};

export default AdminDashboard;
