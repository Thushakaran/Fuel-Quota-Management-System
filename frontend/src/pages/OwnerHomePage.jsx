import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import OwnerNavbar from "../components/OwnerNavbar";
import Footer from "../components/Footer";
import "../css/Layout.css";
import { getownername, liststations } from "../api/FuelStationOwnerServiceApi.js";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const OwnerHomePage = () => {
  const { id } = useParams();
  const [ownername, setOwnername] = useState("");
  const [stations, setStations] = useState([]);

  const [loading, setLoading] = useState(true);

  useEffect(() => {
      Promise.all([
          getownername(id).then((response) => {
              setOwnername(response.data.toString().toUpperCase());
          }),
          liststations(id).then((response) => {
              setStations(response.data);
          }),
      ])
          .catch((error) => {
            console.log(error)
              toast.error(`Error: ${error.message || "Unknown error"}`);
          })
          .finally(() => {
              setLoading(false);
          });
  }, [id]);

  if (loading) {
      return <div className="text-center py-5">Loading...</div>;
  }


  return (
    <>
      <OwnerNavbar />
      <ToastContainer position="top-center" autoClose={6000} closeOnClick draggable/>


      <header className="text-white text-center py-5" style={{backgroundColor:"#429cbf"}}>

      <h1 className="fw-bold">Welcome, {ownername || "Owner"}! 👋</h1>
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
            <div className="text-center">
              <p className="text-muted">No stations found. Add a new station to get started!</p>
                <Link to="/add-station" className="btn btn-primary">
                  Add Station
                </Link>
            </div>
          )}
        </div>
      </main>

      {/* Footer */}

      <footer className="bg-light mt-5 mt-auto mb-0" style={{position:'bottom',bottom:'0', width:'100%'}}>

        <Footer />
      </footer>
    </>
  );
};

export default OwnerHomePage;