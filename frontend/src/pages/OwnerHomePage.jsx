import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import OwnerNavbar from "../components/OwnerNavbar";
import Footer from "../components/Footer";
import "../css/Layout.css";
import { getownername, liststations } from "../api/FuelStationOwnerServiceApi.js";

const OwnerHomePage = () => {
  const { id } = useParams();
  const [ownername, setOwnername] = useState("");
  const [stations, setStations] = useState([]);

  useEffect(() => {
    getownername(id)
      .then((response) => {
        setOwnername(response.data.toUpperCase());
      })
      .catch((error) => {
        console.error("Error fetching owner name:", error);
      });

    liststations(id)
      .then((response) => {
        setStations(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, [id]);

  return (
    <>
      <OwnerNavbar />

      {/* Hero Section */}
      <header className="bg-primary text-white text-center py-5">
        <h1 className="fw-bold">Welcome, {ownername}! 👋</h1>
        <p className="fs-5">Here are the fuel stations you manage.</p>
        <div className="mt-3">
          <span className="badge bg-light text-dark fs-6">
            Total Stations: {stations.length}
          </span>
        </div>
      </header>

      {/* Main Content */}
      <main className="container my-4 homepage">
        {/* Station Cards */}
        <h2 className="fw-bold mb-4">Your Stations</h2>
        <div className="row g-4">
          {stations.length > 0 ? (
            stations.map((station) => (
              <div className="col-md-6 col-lg-4" key={station.id}>
                <Link
                  to={`/station/${station.id}`}
                  className="text-decoration-none"
                >
                  <div
                    className="card shadow-sm h-100 border-0"
                    style={{ transition: "transform 0.3s" }}
                    onMouseEnter={(e) => e.currentTarget.classList.add("shadow-lg")}
                    onMouseLeave={(e) => e.currentTarget.classList.remove("shadow-lg")}
                  >
                    <div
                      className="card-header text-white fw-bold text-center"
                      style={{ backgroundColor: "#343a40" }}
                    >
                      <i className="bi bi-fuel-pump me-2"></i>
                      {station.stationName.toUpperCase()}
                    </div>
                    <div className="card-body text-center">
                      <p className="text-muted mb-2">
                        <i className="bi bi-geo-alt"></i> {station.location}
                      </p>
                    </div>
                  </div>
                </Link>
              </div>
            ))
          ) : (
            <p className="text-muted">No stations found.</p>
          )}
        </div>
      </main>

      {/* Footer */}
      <footer className="bg-light py-3 mt-auto" style={{position:'relative',bottom:'0', display:'block', width:'100%'}}>
        <Footer />
      </footer>
    </>
  );
};

export default OwnerHomePage;