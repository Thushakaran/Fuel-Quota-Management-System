import React, { useState, useEffect } from "react";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import axios from "../api/axiosInstance";


function VehicleOwnerDashboard({id}) {
  const [ownerDetails, setOwnerDetails] = useState(null);
  const [fuelInfo, setFuelInfo] = useState({});

  useEffect(() => {
    axios
      .get(`/vehicle-owner/details/${id}`)
      .then((response) => {
        setOwnerDetails(response.data);
      })
      .catch((error) => {
        console.error("Error fetching owner details:", error);
      });
  }, [id]);

  if (!ownerDetails) {
    return <p>Loading...</p>;
  }

  return (
    <div>
      <Navbar />
      <div className="container mt-5">
        <h2 className="text-center mb-5 text-primary">
          <i className="fas fa-car-side"></i> Vehicle Owner Dashboard
        </h2>
        <div className="row justify-content-center">
          {/* Owner Details Card */}
          <div className="col-lg-5 col-md-6 d-flex">
            <div className="card shadow-lg border-0 mb-4 w-100">
              <div className="card-header bg-primary text-white text-center">
                <h5 className="card-title mb-0">
                  <i className="fas fa-user-circle"></i> Owner Details
                </h5>
              </div>
              <div className="card-body">
                <p className="card-text">
                  <strong>Full Name:</strong>{" "}
                  {vehicleOwner.fullName || (
                    <span className="text-muted">Loading...</span>
                  )}
                </p>
                <p className="card-text">
                  <strong>IC Number:</strong>{" "}
                  {vehicleOwner.icNumber || (
                    <span className="text-muted">Loading...</span>
                  )}
                </p>
                <p className="card-text">
                  <strong>Phone Number:</strong>{" "}
                  {vehicleOwner.phoneNumber || (
                    <span className="text-muted">Loading...</span>
                  )}
                </p>
                <p className="card-text">
                  <strong>Email:</strong>{" "}
                  {vehicleOwner.email || (
                    <span className="text-muted">Loading...</span>
                  )}
                </p>
              </div>
            </div>
          </div>

          {/* Fuel Information Card */}
          <div className="col-lg-5 col-md-6 d-flex">
            <div className="card shadow-lg border-0 mb-4 w-100">
              <div className="card-header bg-success text-white text-center">
                <h5 className="card-title mb-0">
                  <i className="fas fa-gas-pump"></i> Fuel Information
                </h5>
              </div>
              <div className="card-body">
                <p className="card-text">
                  <strong>Fuel Quota:</strong>{" "}
                  {fuelInfo.quota || (
                    <span className="text-muted">Loading...</span>
                  )}{" "}
                  liters
                </p>
                <p className="card-text">
                  <strong>Pumped Fuel:</strong>{" "}
                  {fuelInfo.pumped || (
                    <span className="text-muted">Loading...</span>
                  )}{" "}
                  liters
                </p>
                <p className="card-text">
                  <strong>Balance Fuel:</strong>{" "}
                  {fuelInfo.balance || (
                    <span className="text-muted">Loading...</span>
                  )}{" "}
                  liters
                </p>
              </div>
            </div>
          </div>
        </div>

        {/* Additional Info Section */}
        <div className="row mt-4 mb-5">
          <div className="col-md-12 text-center">
            <div className="alert alert-info">
              <i className="fas fa-exclamation-circle"></i> Ensure your vehicle
              information is up-to-date for accurate fuel quota allocation.
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default VehicleOwnerDashboard;
