import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import StationNavbar from "../components/StationNavbar";
import Footer from "../components/Footer";
import "../css/Layout.css";
import { getfuelInventory, getstationname , getstationStatus} from "../api/FuelStationServiceApi.js";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const StationHomePage = () => {
  const { id } = useParams();
  const [fuels, setFuels] = useState([]);
  const [name, setName] = useState("");
  const [active, setActive] = useState(true); // Track station active status
  const [lowFuelAlerts, setLowFuelAlerts] = useState([]);
  const MAX_FUEL_CAPACITY = 5000;

  const fetchData = () => {
    getfuelInventory(id)
      .then((response) => {
        const fuelData = Object.entries(response.data).map(([fuelType, quantity]) => ({
          fuelType,
          quantity,
        }));
        setFuels(fuelData);

        const lowFuel = fuelData.filter((fuel) => fuel.quantity <= 250);
        setLowFuelAlerts(lowFuel);
      })
      .catch((error) => {
        toast.error(`Error fetching fuel inventory: ${error.response?.data?.message || error.message || "Unknown error"}`);
      });

    getstationname(id)
      .then((response) => {
        setName(response.data);
      })
      .catch((error) => {
        toast.error(`Error fetching station name: ${error.message || "Unknown error"}`);
      });

    getstationStatus(id)
      .then((response) => {
        setActive(response.data);
      })
      .catch((error) => {
        toast.error(`Error fetching station status: ${error.message || "Unknown error"}`);
      });      
  };

  useEffect(() => {
    fetchData();
    const intervalId = setInterval(fetchData, 5000);
    return () => clearInterval(intervalId);
  }, [id]);

  return (
    <>
      <StationNavbar />
      <ToastContainer position="top-center" autoClose={5000} />

      {/* Fixed Alert if the Station is Inactive */}
      {!active && (
        <div className="alert alert-danger text-center " role="alert">
          ⚠️ This station is currently inactive. Please contact the administrator for reactivation.
        </div>
      )}
      <header className="bg-primary text-white text-center py-4" >
        <h1 className="fw-bold">{name.toUpperCase()}</h1>
      </header>

      <main className="container my-4 homepage">
        <h2 className="fw-bold mb-4 text-center">Fuel Inventory</h2>

        {/* Low Fuel Alerts */}
        {lowFuelAlerts.length > 0 && (
          <div className="alert alert-danger d-flex align-items-center mb-4" role="alert">
            <div className="me-auto">
              {lowFuelAlerts.map((fuel, index) => (
                <p key={index} className="mb-1">⚠️ Low Fuel: {fuel.fuelType} is below 250 liters.</p>
              ))}
            </div>
            <button type="button" className="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
          </div>
        )}

        {/* Fuel Inventory */}
        <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
          {fuels.length > 0 ? (
            fuels.map((fuel, index) => (
              <div className="col" key={index}>
                <div className="card shadow-sm h-100">
                  <div className="card-header text-white bg-dark fw-bold text-center">
                    {fuel.fuelType}
                  </div>
                  <div className="card-body">
                    <h5 className="card-title text-center">{fuel.quantity} Liters</h5>
                    <div className="progress">
                      <div
                        className={`progress-bar ${fuel.quantity > 250 ? "bg-success" : "bg-danger"}`}
                        role="progressbar"
                        style={{ width: `${(fuel.quantity / MAX_FUEL_CAPACITY) * 100}%` }}
                        aria-valuenow={fuel.quantity}
                        aria-valuemin="0"
                        aria-valuemax={MAX_FUEL_CAPACITY}
                      ></div>
                    </div>
                  </div>
                </div>
              </div>
            ))
          ) : (
            <div className="text-center w-100">
              <p className="text-muted">Loading fuels...</p>
            </div>
          )}
        </div>
      </main>

      <footer style={{ position: "relative", bottom: "0", width: "100%" }}>
        <Footer />
      </footer>
    </>
  );
};

export default StationHomePage;
