import React, { useState, useEffect } from "react";
import Footer from "../components/Footer";
import axios from "../api/axiosInstance";
import { useParams } from "react-router-dom";
import OwnerNavbar from "../components/OwnerNavbar";

function VehicleOwnerDashboard() {
  const { id } = useParams();
  const [ownerDetails, setOwnerDetails] = useState(null);
  const [fuelInfo, setFuelInfo] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      setError("Unauthorized. Please log in.");
      return;
    }

    console.log("Vehicle ID:", id); // Debugging output

    const fetchOwnerDetails = axios.get(`/vehicles/dashboard/${id}`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });

    const fetchFuelInfo = axios.get(`/vehicles/dashboard/transactions/${id}`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });

    Promise.all([fetchOwnerDetails, fetchFuelInfo])
      .then(([ownerResponse, fuelResponse]) => {
        setOwnerDetails(ownerResponse.data);
        setFuelInfo(fuelResponse.data || []); // Ensure it's an array
      })
      .catch((err) => {
        setError(`Failed to load data. Error: ${err.response?.data?.message || "Please try again."}`);
        console.error("Error fetching data:", err);
      });
  }, [id]);

  if (error) {
    return (
      <div className="container mt-5 text-center">
        <div className="alert alert-danger">
          <i className="fas fa-exclamation-triangle"></i> {error}
        </div>
      </div>
    );
  }

  if (!ownerDetails || fuelInfo.length === 0) {
    return <p className="text-center mt-5">Loading...</p>;
  }

  // Extract latest fuel transaction
  const latestTransaction = fuelInfo[0] || {}; // Take the first transaction if exists

  return (
    <div>
      <OwnerNavbar/>
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
                  <strong>Full Name:</strong> {ownerDetails?.ownerName || "Not Available"}
                </p>
                <p className="card-text">
                  <strong>IC Number:</strong> {ownerDetails?.ownerIcNumber || "Not Available"}
                </p>
                <p className="card-text">
                  <strong>Phone Number:</strong> {ownerDetails?.phoneNumber || "Not Available"}
                </p>
                <p className="card-text">
                  <strong>Email:</strong> {ownerDetails?.email || "Not Available"}
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
                  <strong>Fuel Quota:</strong> {latestTransaction?.amount || "Not Available"} liters
                </p>
                <p className="card-text">
                  <strong>Pumped Fuel:</strong> {latestTransaction?.pumpedLiters || "Not Available"} liters
                </p>
                <p className="card-text">
                  <strong>Balance Fuel:</strong> {latestTransaction?.remainingQuota || "Not Available"} liters
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
