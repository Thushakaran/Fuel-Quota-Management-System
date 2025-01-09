import React, { useState, useEffect } from 'react';
import { useParams, Link} from 'react-router-dom';
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";

import "../css/Layout.css";

const OwnerHomePage = () => {
  const {id} = useParams(); 
  const decodedId = decodeURIComponent(id);

  useEffect(() => {
    // Fetch owner and fuel station data from an API or global state
    // Example:
    // axios.get('/api/owner/details').then(response => {
    //   setOwnerDetails(response.data);
    // });

    // Example for fuel station data:
    // axios.get('/api/fuel-station/details').then(response => {
    //   setFuelStationDetails(response.data);
    // });
  }, []);



    return (
      <div>
      {/* temporay Navbar */}
      <Navbar />
      <header className="hero bg-primary text-white text-center py-5">
        <div>
          <h1 className="display-4">Hi , {id}</h1>
        </div>
      </header>
      <main className="container my-5">
        <section className="features text-center">
          
          <div className="row g-4">
            <div className="col-md-4">
              <div className="feature-card p-4 border rounded shadow-sm">
                <Link to="">
                  <i className="bi bi-person-fill text-primary display-4 mb-3"></i>
                </Link>
                  <h3>Personal Detail</h3>
                
              </div>
            </div>

            <div className="col-md-4">
              <div className="feature-card p-4 border rounded shadow-sm">
              <Link to="">
                <i className="bi bi-fuel-pump  text-success display-4 mb-3"></i>
              </Link>
                <h3>Registered Fuel Stations</h3>
              
              </div>
            </div>

            <div className="col-md-4">
              <div className="feature-card p-4 border rounded shadow-sm">
                <Link to="stationreg">
                  <i className="bi bi-pencil text-danger display-4 mb-3"></i>
                </Link>
                  <h3>Register A Fuel Station</h3>
               
              </div>
            </div>

          </div>
        </section>
      </main>
      {/* temporayFooter  */}
      <Footer/>
    </div>
    );
  };
  

export default OwnerHomePage;
