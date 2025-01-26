import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import StationNavbar from '../components/StationNavbar';
import Footer from '../components/Footer';
import '../css/Layout.css'
import { getfuelInventory, getstationname } from '../api/FuelStationServiceApi.js';
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";


const StationHomePage = () => {
  const { id } = useParams();
  const [fuels, setFuels] = useState([]);
  const [name, setName] = useState('');
  const [lowFuelAlerts, setLowFuelAlerts] = useState([]);
  const MAX_FUEL_CAPACITY = 5000; // Set the maximum fuel balance to 5,000 liters

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
        toast.error(`Error fetching fuel inventory: ${error.response?.data?.message || error.message || 'Unknown error'}`);
      });
      

    getstationname(id)
      .then((response) => {
        setName(response.data);
      })
      .catch((error) => {
        toast.error(`Error fetching station name: ${error.message || 'Unknown error'}`);
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
      <header className="bg-primary text-white text-center py-4">
        <h1 className="fw-bold">{name.toLocaleUpperCase()}</h1>
      </header>
      <main className="container my-4 homepage">
        <h2 className="fw-bold mb-4">Fuel Inventory</h2>

        {lowFuelAlerts.length > 0 && (
          <div className="toast show align-items-center text-bg-danger mb-4" role="alert">
            <div className="d-flex">
              <div className="toast-body">
                {lowFuelAlerts.map((fuel, index) => (
                  <p key={index}>⚠️ Low Fuel: {fuel.fuelType} is below 250 liters.</p>
                ))}
              </div>
              <button
                type="button"
                className="btn-close me-2 m-auto"
                data-bs-dismiss="toast"
                aria-label="Close"
              ></button>
            </div>
          </div>
        )}

        <div className="row g-3 ">
          {fuels.length > 0 ? (
            fuels.map((fuel, index) => (
              <div className="col-md-4" key={index}>
                <div className="card shadow-sm">
                  <div className="card-header text-white bg-dark fw-bold text-center">
                    {fuel.fuelType}
                  </div>
                  <div className="card-body">
                    <h5 className="card-title text-center">{fuel.quantity} Liters</h5>
                    <div className="progress">
                      <div
                        className={`progress-bar ${fuel.quantity > 250 ? 'bg-success' : 'bg-danger'}`}
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
            <p>Loading fuels...</p>
          )}
        </div>
      </main>
      <footer style={{ position: 'relative', bottom: '0', width: '100%' }}>
        <Footer />
      </footer>
    </>
  );
};

export default StationHomePage;
