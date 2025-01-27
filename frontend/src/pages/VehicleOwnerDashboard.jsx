
import React, { useState, useEffect } from "react";
import Footer from "../components/Footer";
import axios from "../api/axiosInstance";
import { useParams } from "react-router-dom";
import VehicleOwnerNavbar from "../components/VehicleOwnerNavbar";

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
        setError(
          `Failed to load data. Error: ${
            err.response?.data?.message || "Please try again."
          }`
        );
        console.error("Error fetching data:", err);
      });
  }, [id]);

  const downloadQRCode = () => {
    const qrCodeData = ownerDetails?.qrCode;
    if (qrCodeData) {
      const link = document.createElement("a");
      link.href = `data:image/png;base64,${qrCodeData}`;
      link.download = `${ownerDetails?.vehicleNumber}_QRCode.png`;
      link.click();
    }
  };

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
      <VehicleOwnerNavbar />
      <div className="container mt-5">
        <h2 className="text-center mb-5 text-primary">
          <i className="fas fa-car-side"></i> Vehicle Owner Dashboard
        </h2>
        <div className="row justify-content-center">
          {/* Merged Owner Details and Fuel Information Card */}
          <div className="col-lg-8 col-md-10 d-flex">
            <div className="card shadow-lg border-0 mb-4 w-100">
              <div className="card-header bg-primary text-white text-center">
                <h5 className="card-title mb-0">
                  <i className="fas fa-car-side"></i> Vehicle Information
                </h5>
              </div>
              <div className="card-body d-flex">
                {/* Left Column for Details */}
                <div className="col-8">
                  <div className="mb-4">
                    <p className="card-text">
                      <strong>Full Name:</strong> {ownerDetails?.ownerName || "Not Available"}
                    </p>
                    <p className="card-text">
                      <strong>IC Number:</strong> {ownerDetails?.ownerIcNumber || "Not Available"}
                    </p>
                    <p className="card-text">
                      <strong>Vehicle Number:</strong> {ownerDetails?.vehicleNumber || "Not Available"}
                    </p>
                    <p className="card-text">
                      <strong>Fuel Quota:</strong> {ownerDetails?.fuelQuota || "Not Available"} liters
                    </p>
                    <p className="card-text">
                      <strong>Pumped Fuel:</strong> {latestTransaction?.amount || "Not Available"} liters
                    </p>
                    <p className="card-text">
                      <strong>Balance Fuel:</strong> {ownerDetails?.remainingQuota || "Not Available"} liters
                    </p>
                  </div>
                </div>

                {/* Right Column for QR Code */}
                <div className="col-4 d-flex justify-content-center align-items-center">
                  {ownerDetails?.qrCode ? (
                    <div className="text-center">
                      <img
                        src={`data:image/png;base64,${ownerDetails.qrCode}`}
                        alt="QR Code"
                        className="img-fluid border rounded"
                        style={{ width: "150px", height: "150px" }}
                      />
                      <button
                        className="btn btn-success mt-3"
                        onClick={downloadQRCode}
                      >
                        Download QR Code
                      </button>
                    </div>
                  ) : (
                    <p className="text-danger">QR Code not available</p>
                  )}
                </div>
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
