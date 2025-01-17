import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import OwnerNavbar from "../components/OwnerNavbar";
import Footer from "../components/Footer";
import "../css/Layout.css";
import { getownername, liststations } from "../Services/FuelStationService";

const OwnerHomePage = () => {
  const { id } = useParams();
  const [ownername, setOwnername] = useState(""); // State to track the owner's name
  const [stations,setStations] = useState([]);


  useEffect(() => {
    getownername(id)
      .then((response) => {
        setOwnername((response.data).toUpperCase()); 
      })
      .catch((error) => {
        console.error("Error fetching owner name:", error);
      });

    
    liststations(id)
      .then((response)=>{
        setStations(response.data);
      })
      .catch((error) => {
        console.error(error);
      });

  }, [id]); //ensures the effect runs when `id` changes

  return (
    <>
      <OwnerNavbar />
      <header className="text-black text-left py-1 ps-4 ms-4">
        <h1 className="fw-bold">Hi, {ownername}! 👋</h1>
      </header>

      <main className="container my-2">
        <h2 className="fw-bold mb-3">Your Stations</h2>
        <div className="row g-4">
          {
            stations.map((station) => (
              <div key={station.id}>{station.stationName}</div>
            ))
          }
        </div>
      </main>

      <Footer />
    </>
  );
};

export default OwnerHomePage;
